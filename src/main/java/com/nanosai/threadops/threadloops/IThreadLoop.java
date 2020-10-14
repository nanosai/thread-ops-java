package com.nanosai.threadops.threadloops;

public interface IThreadLoop<R, T> {

    public IThreadLoop start();

    public IThreadLoop stop();

    public T setInitializer(IThreadLoopInitializer<R, T> initializor);

    public T setTerminator(IThreadLoopTerminator<T> terminator);

    public T setInitializerAndTerminator(IThreadLoopInitializerAndTerminator<R,T> initializerAndTerminator);

}
