package com.nanosai.threadops.threadloops;

public class ThreadLoopPausable {


    private Thread           loopThread = null;
    private LoopImpl         loopImpl   = null;

    public ThreadLoopPausable(IThreadLoopCycle threadLoopCycle) {
        this.loopImpl   = new LoopImpl(threadLoopCycle);
        this.loopThread = new Thread(this.loopImpl);
    }

    public void start() {
        this.loopThread.start();
    }

    public synchronized void stop() {
        this.loopImpl.stop();
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

        private IThreadLoopCycle loopCycle = null;

        public LoopImpl(IThreadLoopCycle loopCycle) {
            this.loopCycle = loopCycle;
        }

        public void run() {
            while(!isStopping()) {
                long pauseTime = this.loopCycle.run();
                if(pauseTime > 0) {
                    try {
                        Thread.sleep(pauseTime);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println("ThreadLoopPausable failed to pause: " + e.getMessage());

                    }
                }
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
