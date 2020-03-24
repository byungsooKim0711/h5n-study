package org.kimbs.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job jobTest1() {
        return jobBuilderFactory.get("jobTest1")
                .start(stepTest1())
                .build();
    }

    @Bean
    public Step stepTest1() {
        return stepBuilderFactory.get("stepTest1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[STEP TEST1] StepContribution: {}, ChunkContext: {}", stepContribution, chunkContext);
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
