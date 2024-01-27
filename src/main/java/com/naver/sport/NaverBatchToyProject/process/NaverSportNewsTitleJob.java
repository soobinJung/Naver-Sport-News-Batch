package com.naver.sport.NaverBatchToyProject.process;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@RequiredArgsConstructor
public class NaverSportNewsTitleJob {

    private final NaverSportNewsTitleStep naverSportNewsTitleStep;

    @Bean("NaverSportNewsTitleJob")
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("NaverSportNewsTitleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(naverSportNewsTitleStep.step(jobRepository, transactionManager))
                .build();
    }
}
