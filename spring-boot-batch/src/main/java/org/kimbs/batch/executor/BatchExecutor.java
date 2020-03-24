package org.kimbs.batch.executor;

import lombok.RequiredArgsConstructor;
import org.kimbs.batch.event.BatchStartEvent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchExecutor {

    private final JobLauncher jobLauncher;
    private final Job job;

    @EventListener
    public void onApplicationEvent(BatchStartEvent event) throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addString("JOB-ID", String.valueOf(event.getTimestamp()))
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, parameters);
    }
}
