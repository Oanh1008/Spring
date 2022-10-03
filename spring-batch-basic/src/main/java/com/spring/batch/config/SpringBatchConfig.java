package com.spring.batch.config;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.spring.batch.entity.Customer;
import com.spring.batch.entity.Person;
import com.spring.batch.mapper.PersonMapper;
import com.spring.batch.processor.CustomerProcessor;
import com.spring.batch.processor.PersonProcessor;
import com.spring.batch.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private CustomerRepository customerRepository;

    private DataSource dataSource;

    @Bean
    public ConversionService customerConversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(defaultConversionService);
        defaultConversionService.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }

        });
        return defaultConversionService;
    }

    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader itemReader = new FlatFileItemReader();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);
        fieldSetMapper.setConversionService(customerConversionService());

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {

        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step").<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importCustomer")
                .listener(new JobResultListener())
                .flow(step1())
                .next(step1DB())
                .end().build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        // 10 thread
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    ///////////////// Get data save it in csv ///////////////

    @Bean
    public JdbcCursorItemReader<Person> readerDB() {
        JdbcCursorItemReader<Person> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql("SELECT person_id,first_name,last_name,email,age FROM person");
        cursorItemReader.setRowMapper(new PersonMapper());
        return cursorItemReader;
    }

    @Bean
    public PersonProcessor processor1() {
        return new PersonProcessor();
    }

    @Bean
    public FlatFileItemWriter<Person> writer1() {
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();
        writer.setResource(new ClassPathResource("person.csv"));

        DelimitedLineAggregator<Person> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<Person> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"personId","firstName","lastName","email","age"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }
    @Bean
    public Step step1DB(){
        return stepBuilderFactory.get("exportDB")
                .<Person,Person>chunk(10)
                .reader(readerDB()).processor(processor1())
                .writer(writer1()).build();
    }

//    @Bean
//    public Job exportPeronJob(){
//        return jobBuilderFactory.get("exportPeronJob").incrementer(new RunIdIncrementer()).flow(step1DB()).end().build();
//    }
}
