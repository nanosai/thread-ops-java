package com.nanosai.threadops.threadloops;

public class RepeatedTaskExecutorPausable implements IRepeatedTaskPausable {

    private IRepeatedTaskPausable[] repeatedTasks = null;
    private long[] repeatedTasksNextExecutionTimes = null;


    public RepeatedTaskExecutorPausable(IRepeatedTaskPausable ... repeatedTasks) {
        this.repeatedTasks = repeatedTasks;
        this.repeatedTasksNextExecutionTimes = new long[this.repeatedTasks.length];
    }


    public long exec() {
        long timeNanos = System.nanoTime();
        for(int i = 0; i < repeatedTasksNextExecutionTimes.length; i++) {
            if(timeNanos > this.repeatedTasksNextExecutionTimes[i]){
                long nextExecutionDelay = this.repeatedTasks[i].exec();
                timeNanos = System.nanoTime();
                this.repeatedTasksNextExecutionTimes[i] = timeNanos + nextExecutionDelay;
            }
        }

        //calculate delay until next iteration
        long earliestExecutionTimeNanos = Long.MAX_VALUE;
        for(int i = 0; i < repeatedTasksNextExecutionTimes.length; i++) {
            if(repeatedTasksNextExecutionTimes[i] < earliestExecutionTimeNanos){
                earliestExecutionTimeNanos = repeatedTasksNextExecutionTimes[i];
            }
        }
        long nextExecutionDelay = earliestExecutionTimeNanos - System.nanoTime();
        if(nextExecutionDelay < 0) {
            nextExecutionDelay = 0;
        }
        return nextExecutionDelay;
    }


}
