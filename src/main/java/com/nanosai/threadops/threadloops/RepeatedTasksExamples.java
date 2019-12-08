package com.nanosai.threadops.threadloops;

public class RepeatedTasksExamples {

    public static void main(String[] args) throws InterruptedException {

        RepeatedTaskExecutorPausable executor = new RepeatedTaskExecutorPausable(
                 () -> { System.out.println("Blabla"); return 1_000_000_000; }
                ,() -> { System.out.println("Second"); return   400_000_000; }
                );


        ThreadLoopPausable threadLoop = new ThreadLoopPausable(() -> {
            return executor.exec();
        });
        threadLoop.start();

        Thread.sleep(5000);

        threadLoop.stop();
    }
}
