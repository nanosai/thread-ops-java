package com.nanosai.threadops.threadloops;

public interface IThreadLoopInitializer<R,T> {

    public R init(T threadLoop);

}
