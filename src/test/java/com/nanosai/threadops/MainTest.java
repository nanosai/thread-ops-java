package com.nanosai.threadops;

import com.nanosai.threadops.threadloops.ThreadLoop;
import com.nanosai.threadops.threadloops.ThreadLoopPausable;

public class MainTest {

    public static void main(String[] args) throws InterruptedException {

        //testThreadLoop();

        testThreadLoopPausable();

    }

    private static void testThreadLoopPausable() throws InterruptedException {
        ThreadLoopPausable thread = new ThreadLoopPausable((threadLoop) -> () -> {
            System.out.println("Running");
            return 500;
        });

        thread.start();

        Thread.sleep(3000);

        thread.stop();
        thread.join();
    }

    private static void testThreadLoop() throws InterruptedException {
        ThreadLoop thread = new ThreadLoop((threadLoop) -> () -> {
            System.out.println("Running");
        });
        thread.start();


        Thread.sleep(3000);

        thread.stop();
        thread.join();
    }
}
