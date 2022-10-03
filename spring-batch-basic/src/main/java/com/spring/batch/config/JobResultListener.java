package com.spring.batch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JobResultListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Tao dang chuẩn bị làm việc đây");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Tao làm xong rồi nghe");
    }
}
