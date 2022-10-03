package com.spring.batch.shedule;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EnableScheduling
@AllArgsConstructor
@Log4j2
public class ExportCSV {

//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job job;
//
//    @Scheduled(cron = "*/10 * * * * *")
//    public void myScheduler() {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        log.info("===========================================");
//        try {
//            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
//            System.out.println("Job's Status:::" + jobExecution.getStatus());
//        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
//                 | JobParametersInvalidException e) {
//            e.printStackTrace();
//        }
//    }
}
