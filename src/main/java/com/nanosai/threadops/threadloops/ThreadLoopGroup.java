package com.nanosai.threadops.threadloops;

import java.util.ArrayList;
import java.util.List;

public class ThreadLoopGroup {

    private List<ThreadLoop> threadLoops = new ArrayList<>();

    IThreadLoopGroupInitializer initializer = null;
    IThreadLoopGroupTerminator  terminator  = null;


    public ThreadLoopGroup addThreadLoop(ThreadLoop threadLoop){
        this.threadLoops.add(threadLoop);
        return this;
    }


    public ThreadLoopGroup setInitializer(IThreadLoopGroupInitializer initializer){
        this.initializer = initializer;
        return this;
    }


    public ThreadLoopGroup setTerminator(IThreadLoopGroupTerminator terminator) {
        this.terminator = terminator;
        return this;
    }


    public ThreadLoopGroup start() {
        if(this.initializer != null) {
            this.initializer.init(this);
        } else {
            for(int i=0; i<this.threadLoops.size(); i++) {
                ThreadLoop threadLoop = this.threadLoops.get(i);
                threadLoop.start();
            }
        }

        return this;
    }


    public ThreadLoopGroup stop() {
        if(this.terminator != null) {
            this.terminator.terminate(this);
        } else {
            for(int i=0; i<this.threadLoops.size(); i++) {
                ThreadLoop threadLoop = this.threadLoops.get(i);
                threadLoop.stop();
            }
        }
        return this;
    }
}
