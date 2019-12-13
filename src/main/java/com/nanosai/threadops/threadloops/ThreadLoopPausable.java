package com.nanosai.threadops.threadloops;

public class ThreadLoopPausable {


    private Thread           loopThread = null;
    private LoopImpl         loopImpl   = null;

    public ThreadLoopPausable(IRepeatedTaskPausable repeatedTask) {
        this.loopImpl   = new LoopImpl(repeatedTask);
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

        private IRepeatedTaskPausable repeatedTaskPausable = null;

        public LoopImpl(IRepeatedTaskPausable repeatedTaskPausable) {
            this.repeatedTaskPausable = repeatedTaskPausable;
        }

        public void run() {
            while(!isStopping()) {
                long nextExecutionDelay = this.repeatedTaskPausable.exec();
                if(nextExecutionDelay > 0) {
                    long millis = nextExecutionDelay / 1_000_000L;
                    int  nanos  = (int) (nextExecutionDelay % 1_000_000L);

                    //System.out.println("   [sleep time (millis / nanos) = " + millis + " / " + nanos + "]");
                    try {
                        Thread.sleep(millis, nanos);
                    } catch (InterruptedException e) {
                        System.out.println("ThreadLoopPausable failed to pause: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            synchronized(this) {
                isStopped = true;
            }
        }

        public synchronized void    stop() {
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
