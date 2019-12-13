package com.nanosai.threadops.threadloops;

public class RepeatedTaskExecutor implements IRepeatedTask {

    private IRepeatedTask[] repeatedTasks = null;


    public RepeatedTaskExecutor(IRepeatedTask ... repeatedTasks) {
        this.repeatedTasks = repeatedTasks;
    }


    public void exec() {
        for(int i=0; i < repeatedTasks.length; i++) {
            this.repeatedTasks[i].exec();
        }
    }


}
