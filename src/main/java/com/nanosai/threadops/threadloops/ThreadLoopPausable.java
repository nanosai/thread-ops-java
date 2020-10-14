package com.nanosai.threadops.threadloops;

public class ThreadLoopPausable implements IThreadLoop<IRepeatedTaskPausable, ThreadLoopPausable> {


    private Thread           loopThread = null;
    private LoopImpl         loopImpl   = null;

    private IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer = null;
    private IThreadLoopTerminator<ThreadLoopPausable>                         terminator  = null;


    public ThreadLoopPausable(IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer){
        this.initializer = initializer;
    }

    public ThreadLoopPausable(IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer,
                              IThreadLoopTerminator<ThreadLoopPausable> terminator){
        this.initializer = initializer;
        this.terminator  = terminator;
    }

    public ThreadLoopPausable(IThreadLoopInitializerAndTerminator initializerAndTerminator){
        this.initializer = initializerAndTerminator;
        this.terminator  = initializerAndTerminator;
    }

    @Override
    public ThreadLoopPausable setInitializer(IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer) {
        this.loopImpl.setInitializer(initializer);
        return this;
    }

    @Override
    public ThreadLoopPausable setTerminator(IThreadLoopTerminator<ThreadLoopPausable> terminator) {
        this.loopImpl.setTerminator(terminator);
        return this;
    }

    @Override
    public ThreadLoopPausable setInitializerAndTerminator(IThreadLoopInitializerAndTerminator<IRepeatedTaskPausable, ThreadLoopPausable> initializerAndTerminator) {
        this.initializer = initializerAndTerminator;
        this.terminator  = initializerAndTerminator;
        return this;
    }

    public synchronized IThreadLoop start() {
        this.loopThread.start();
        return this;
    }

    public synchronized IThreadLoop stop() {
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

        private IRepeatedTaskPausable repeatedTaskPausable = null;

        private IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer = null;
        private IThreadLoopTerminator<ThreadLoopPausable> terminator  = null;

        private ThreadLoopPausable threadLoop = null;

        public LoopImpl(ThreadLoopPausable threadLoop, IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable>  initializer) {
            this.threadLoop  = threadLoop;
            this.initializer = initializer;
        }

        public LoopImpl(ThreadLoopPausable threadLoop, IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer , IThreadLoopTerminator<ThreadLoopPausable> terminator) {
            this.threadLoop  = threadLoop;
            this.initializer = initializer;
            this.terminator  = terminator;
        }

        public LoopImpl(ThreadLoopPausable threadLoop, IThreadLoopInitializerAndTerminator<IRepeatedTaskPausable, ThreadLoopPausable> initializerAndTerminator) {
            this.threadLoop  = threadLoop;
            this.initializer = initializerAndTerminator;
            this.terminator  = initializerAndTerminator;
        }


        public void run() {
            this.repeatedTaskPausable = this.initializer.init(this.threadLoop);
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
            if(this.terminator != null) {
                this.terminator.terminate(this.threadLoop);
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

        public synchronized void setInitializer(IThreadLoopInitializer<IRepeatedTaskPausable, ThreadLoopPausable> initializer) {
            this.initializer = initializer;
        }

        public synchronized void setTerminator(IThreadLoopTerminator<ThreadLoopPausable> terminator) {
            this.terminator = terminator;
        }


    }

}
