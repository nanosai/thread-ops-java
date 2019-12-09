package com.nanosai.threadops.threadloops;

public class RepeatedTaskExecutorPausable implements IRepeatedTask {

    private IRepeatedTask[] proactors                   = null;
    private long[]          proactorsExecutionTimeNanos = null;


    public RepeatedTaskExecutorPausable(IRepeatedTask... proactors) {
        this.proactors = proactors;
        this.proactorsExecutionTimeNanos = new long[this.proactors.length];
    }


    public long exec() {
        long timeNanos = System.nanoTime();
        for(int i=0; i < proactorsExecutionTimeNanos.length; i++) {
            if(timeNanos > this.proactorsExecutionTimeNanos[i]){
                long nextExecutionDelay = this.proactors[i].exec();
                timeNanos = System.nanoTime();
                this.proactorsExecutionTimeNanos[i] = timeNanos + nextExecutionDelay;
            }
        }

        //calculate delay until next iteration
        long earliestExecutionTimeNanos = Long.MAX_VALUE;
        for(int i=0; i < proactorsExecutionTimeNanos.length; i++) {
            if(proactorsExecutionTimeNanos[i] < earliestExecutionTimeNanos){
                earliestExecutionTimeNanos = proactorsExecutionTimeNanos[i];
            }
        }
        long nextExecutionDelay = earliestExecutionTimeNanos - System.nanoTime();
        if(nextExecutionDelay < 0) {
            nextExecutionDelay = 0;
        }
        return nextExecutionDelay;
    }


}
