package org.kimbs.batch.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class TaskTwo implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("[TASK TWO] START, StepContribution: {}, ChunkContext: {}", stepContribution, chunkContext);

        log.info("[TASK TWO] FINISHED");
        return RepeatStatus.FINISHED;
    }
}
