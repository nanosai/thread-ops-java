package com.nanosai.threadops.multitask;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiTaskExecutorTest {



    @Test
    public void test() {
        MultiTaskExecutor executor = new MultiTaskExecutor();

        MockTask task1 = new MockTask(1);
        MockTask task2 = new MockTask(2);
        MockTask task3 = new MockTask(3);
        MockTask task4 = new MockTask(4);
        MockTask task5 = new MockTask(5);

        executor.addTasks(task1, task2, task3, task4, task5);
        assertEquals(5, executor.getActiveTasks().size());
        assertEquals(0, executor.getCompletedTasks().size());

        executor.makeProgress(1);
        assertAboutTask(task1, true , 1, 1, 0);
        assertAboutTask(task2, false, 2, 0, 2);
        assertAboutTask(task3, false, 3, 0, 3);
        assertAboutTask(task4, false, 4, 0, 4);
        assertAboutTask(task5, false, 5, 0, 5);
        assertEquals(4, executor.getActiveTasks().size());
        assertEquals(1, executor.getCompletedTasks().size());

        executor.makeProgress(1);
        assertAboutTask(task2, false, 2, 1, 1);
        assertAboutTask(task3, false, 3, 0, 3);
        assertAboutTask(task4, false, 4, 0, 4);
        assertAboutTask(task5, false, 5, 0, 5);
        assertEquals(4, executor.getActiveTasks().size());
        assertEquals(1, executor.getCompletedTasks().size());

        executor.makeProgress(2);
        assertAboutTask(task2, false, 2, 1, 1);
        assertAboutTask(task3, false, 3, 1, 2);
        assertAboutTask(task4, false, 4, 1, 3);
        assertAboutTask(task5, false, 5, 0, 5);
        assertEquals(4, executor.getActiveTasks().size());
        assertEquals(1, executor.getCompletedTasks().size());

        executor.makeProgress(2);
        assertAboutTask(task2, true , 2, 2, 0);
        assertAboutTask(task3, false, 3, 1, 2);
        assertAboutTask(task4, false, 4, 1, 3);
        assertAboutTask(task5, false, 5, 1, 4);
        assertEquals(3, executor.getActiveTasks().size());
        assertEquals(2, executor.getCompletedTasks().size());

        executor.makeProgress(2);
        assertAboutTask(task3, false, 3, 2, 1);
        assertAboutTask(task4, false, 4, 2, 2);
        assertAboutTask(task5, false, 5, 1, 4);
        assertEquals(3, executor.getActiveTasks().size());
        assertEquals(2, executor.getCompletedTasks().size());

        executor.makeProgress(2);
        assertAboutTask(task3, true , 3, 3, 0);
        assertAboutTask(task4, false, 4, 2, 2);
        assertAboutTask(task5, false, 5, 2, 3);
        assertEquals(2, executor.getActiveTasks().size());
        assertEquals(3, executor.getCompletedTasks().size());

        executor.makeProgress(3);
        assertAboutTask(task4, true , 4, 4, 0);
        assertAboutTask(task5, false, 5, 3, 2);
        assertEquals(1, executor.getActiveTasks().size());
        assertEquals(4, executor.getCompletedTasks().size());

        executor.makeProgress(1);
        assertAboutTask(task5, false, 5, 4, 1);
        assertEquals(1, executor.getActiveTasks().size());
        assertEquals(4, executor.getCompletedTasks().size());

        executor.makeProgress(2);
        assertAboutTask(task5, true , 5, 5, 0);
        assertEquals(0, executor.getActiveTasks().size());
        assertEquals(5, executor.getCompletedTasks().size());

        assertEquals(0, executor.getNextTaskIndex());

        executor.makeProgress(3);
        assertEquals(0, executor.getNextTaskIndex());

    }

    public void assertAboutTask(ITask task, boolean isComplete, int totalWork, int completedWork, int remainingWork) {
        assertEquals(isComplete, task.isComplete());
        assertEquals(totalWork    , task.getTotalWork());
        assertEquals(completedWork, task.getCompletedWork());
        assertEquals(remainingWork, task.getRemainingWork());
    }

}
