package com.naver.sport.NaverBatchToyProject.process;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@RequiredArgsConstructor
public class NaverSportNewsTitleStep {

    private final NaverSportNewsTitleReader reader;
    private final NaverSportNewsTitleWrite write;

    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step",jobRepository)
                .<NaverSportNewsTitleDto, NaverSportNewsTitleDto>chunk(3, transactionManager)
                .reader(reader)
                .writer(write)
                .transactionManager(transactionManager)
                .build();
    }
}