package org.kimbs.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.batch.task.TaskOne;
import org.kimbs.batch.task.TaskTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
    public Job demoJob() {
        return jobBuilderFactory.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(demoStep0())
                .next(demoStep1())
                .next(demoStep2())
                .build();
    }

    @Bean
    public Step demoStep0() {
        return stepBuilderFactory.get("demoStep0")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("[TASK ZERO] START, StepContribution: {}, ChunkContext: {}", stepContribution, chunkContext);

                    log.info("[TASK ZERO] FINISHED");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step demoStep1() {
        return stepBuilderFactory.get("demoStep1")
                .tasklet(new TaskOne())
                .build();
    }

    @Bean
    public Step demoStep2() {
        return stepBuilderFactory.get("demoStep2")
                .tasklet(new TaskTwo())
                .build();
    }
}
