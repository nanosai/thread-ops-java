package com.nanosai.threadops.threadloops;

public class ThreadLoop {


    private Thread   loopThread = null;
    private LoopImpl loopImpl   = null;

    public ThreadLoop(IRepeatedTask repeatedTask) {
        this.loopImpl   = new LoopImpl(repeatedTask);
        this.loopThread = new Thread(this.loopImpl);
    }

    public synchronized ThreadLoop start() {
        this.loopThread.start();
        return this;
    }

    public synchronized ThreadLoop stop() {
        this.loopImpl.stop();
        return this;
    }

    public synchronized boolean isStopping() {
        return this.loopImpl.isStopping();
    }

    public synchronized boolean isStopped() {
        return this.loopImpl.isStopped();
    }

    public void interrupt() {
        this.loopThread.interrupt();
    }

    public void join() throws InterruptedException {
        this.loopThread.join();
    }

    public String getThreadName() {
        return this.loopThread.getName();
    }

    public long getThreadId() {
        return this.loopThread.getId();
    }

    public Thread getLoopThread() {
        return this.loopThread;
    }




    public static class LoopImpl implements Runnable {
        private boolean shouldStop = false;
        private boolean isStopped  = false;

        private IRepeatedTask loopCycle = null;

        public LoopImpl(IRepeatedTask loopCycle) {
            this.loopCycle = loopCycle;
        }

        public void run() {
            while(!isStopping()) {
                this.loopCycle.exec();
            }
            synchronized(this) {
                isStopped = true;
            }
        }

        public synchronized void stop() {
            this.shouldStop = true;
        }

        public synchronized boolean isStopping() {
            return this.shouldStop;
        }

        public synchronized boolean isStopped() {
            return this.isStopped;
        }
    }

}
