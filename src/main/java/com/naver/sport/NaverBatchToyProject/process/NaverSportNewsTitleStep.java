package com.naver.sport.NaverBatchToyProject.process;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NaverSportNewsTitleStep {

    private final NaverSportNewsTitleReader reader;
    private final NaverSportNewsTitleWrite write;

    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager ) {

        try{
            TaskletStep step = new StepBuilder("step", jobRepository)
                    .<NaverSportNewsTitleDto, NaverSportNewsTitleDto>chunk(10, transactionManager)
                    .reader(reader)
                    .writer(write)
                    .transactionManager(transactionManager)
                    .build();
            return step;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}