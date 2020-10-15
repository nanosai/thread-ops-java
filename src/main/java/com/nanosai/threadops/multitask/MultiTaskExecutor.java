package com.nanosai.threadops.multitask;

import java.util.ArrayList;
import java.util.List;

public class MultiTaskExecutor {

    private int nextTaskIndex = 0;

    private List<ITask> activeTasks    = new ArrayList<>();
    private List<ITask> completedTasks = new ArrayList<>();

    public List<ITask> getActiveTasks() {
        return activeTasks;
    }
    public List<ITask> getCompletedTasks() {
        return completedTasks;
    }

    public int getNextTaskIndex() {
        return this.nextTaskIndex;
    }

    public void setNextTaskIndex(int nextTaskIndex) {
        this.nextTaskIndex = nextTaskIndex;
    }

    public void resetNextTasKIndex() {
        this.nextTaskIndex = 0;
    }

    public void addTask(ITask task) {
        this.activeTasks.add(task);
    }

    public void addTasks(List<ITask> tasks) {
        this.activeTasks.addAll(tasks);
    }

    public void addTasks(ITask ... tasks){
        for(int i=0; i<tasks.length; i++) {
            this.activeTasks.add(tasks[i]);
        }
    }

    public void removeActiveTasks() {
        this.activeTasks.clear();
    }

    public List<ITask> takeActiveTasks(List<ITask> dest) {
        dest.addAll(this.activeTasks);
        this.activeTasks.clear();
        return dest;
    }

    public void removeCompletedTasks() {
        this.completedTasks.clear();
    }

    public List<ITask> takeCompletedTasks(List<ITask> dest) {
        dest.addAll(this.completedTasks);
        this.completedTasks.clear();
        return dest;
    }

    public void makeProgress() {
        makeProgress(this.activeTasks.size());
    }

    public void makeProgress(int noOfTasksToCall) {
        if(this.activeTasks.size() == 0){
            return;
        }
        for(int i=0; i<noOfTasksToCall; i++) {
            ITask task = this.activeTasks.get(this.nextTaskIndex);
            task.makeProgress();

            if(task.isComplete()) {
                this.activeTasks.remove(this.nextTaskIndex);
                this.completedTasks.add(task);
            } else {
                this.nextTaskIndex++;
            }
            if(this.activeTasks.size() == 0){
                this.nextTaskIndex = 0;
                return;
            }
            this.nextTaskIndex %= this.activeTasks.size();
        }

   }

}
