package com.nanosai.threadops.threadloops;

public class ThreadLoop implements IThreadLoop<IRepeatedTask, ThreadLoop> {

    private Thread   loopThread = null;
    private LoopImpl loopImpl   = null;

    private IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer = null;
    private IThreadLoopTerminator<ThreadLoop>  terminator  = null;


    public ThreadLoop(IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer){
        this.initializer = initializer;
    }

    public ThreadLoop(IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer, IThreadLoopTerminator<ThreadLoop> terminator) {
        this.initializer = initializer;
        this.terminator  = terminator;
    }

    public ThreadLoop(IThreadLoopInitializerAndTerminator<IRepeatedTask, ThreadLoop> initializerAndTerminator){
        this.initializer = initializerAndTerminator;
        this.terminator  = initializerAndTerminator;
    }

    @Override
    public ThreadLoop setInitializer(IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer) {
        this.loopImpl.setInitializer(initializer);
        return this;
    }

    @Override
    public ThreadLoop setTerminator(IThreadLoopTerminator<ThreadLoop> terminator) {
        this.loopImpl.setTerminator(terminator);
        return this;
    }

    @Override
    public ThreadLoop setInitializerAndTerminator(IThreadLoopInitializerAndTerminator<IRepeatedTask, ThreadLoop> initializerAndTerminator){
        this.initializer = initializerAndTerminator;
        this.terminator  = initializerAndTerminator;
        return this;
    }

    public synchronized IThreadLoop start() {
        this.loopImpl   = new LoopImpl(this, this.initializer);
        this.loopThread = new Thread(this.loopImpl);
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

        private IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer = null;
        private IThreadLoopTerminator<ThreadLoop>  terminator  = null;

        private IRepeatedTask repeatedTask = null;
        private ThreadLoop    threadLoop   = null;

        public LoopImpl(ThreadLoop threadLoop, IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer) {
            this.threadLoop   = threadLoop;
            this.initializer  = initializer;
        }

        public LoopImpl(ThreadLoop threadLoop, IThreadLoopInitializer<IRepeatedTask, ThreadLoop> initializer, IThreadLoopTerminator<ThreadLoop> terminator) {
            this.threadLoop  = threadLoop;
            this.initializer = initializer;
            this.terminator  = terminator;
        }

        public void run() {
            this.repeatedTask = this.initializer.init(this.threadLoop);

            while(!isStopping()) {
                this.repeatedTask.exec();
            }
            if(this.terminator != null) {
                this.terminator.terminate(this.threadLoop);
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

        public synchronized void setInitializer(IThreadLoopInitializer initializer) {
            this.initializer = initializer;
        }

        public synchronized void setTerminator(IThreadLoopTerminator terminator) {
            this.terminator = terminator;
        }
    }


}
