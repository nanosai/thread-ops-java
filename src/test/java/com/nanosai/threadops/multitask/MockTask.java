package com.nanosai.threadops.multitask;

public class MockTask implements ITask {

    private int slicesInTotal  = 1;
    private int slicesExecuted = 0;

    public MockTask(){}
    public MockTask(int slicesInTotal) {
        this.slicesInTotal = slicesInTotal;
    }

    @Override
    public void makeProgress() {
        this.slicesExecuted++;
    }

    @Override
    public boolean isComplete() {
        return this.slicesExecuted == this.slicesInTotal;
    }

    @Override
    public int getTotalWork() {
        return this.slicesInTotal;
    }

    @Override
    public int getCompletedWork() {
        return this.slicesExecuted;
    }

    @Override
    public int getRemainingWork() {
        return this.slicesInTotal - this.slicesExecuted;
    }

}
