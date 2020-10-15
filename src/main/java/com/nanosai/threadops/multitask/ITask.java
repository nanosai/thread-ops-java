package com.nanosai.threadops.multitask;

public interface ITask {

    public void makeProgress();

    public boolean isComplete();

    public int getTotalWork();

    public int getCompletedWork();

    public int getRemainingWork();
}
