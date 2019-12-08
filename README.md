# Thread Ops for Java

Thread Ops for Java is an open source toolkit providing multithreading tools to support the implementation
of samethreaded, asynchronous, non-blocking IO distributed systems.

Thread Ops makes it easier to execute a task in a one-off thread, or to call a "function" repeatedly inside a loop in
its own thread, and several other thread related tasks.


## Tools

 - [Thread Loops](#thread-loops)
 - [Repeated Tasks](#repeated-tasks)
 - [One Time Tasks](#one-time-tasks)
 - [One Time Sliced Tasks](#one-time-sliced-tasks)
 - [Thread Message Ports](#thread-message-ports)



<a name="thread-loops"></a>
## Thread Loops

A Thread Loop is a thread that keeps executing the same "loop" of operations over and over again.
For instance, a thread loop could consist of these actions:

 - Check if a non-blocking NIO server has any input data to read from any of its inbound connections
 - Check if there is any outbound data that is waiting to be written back to a connection.

These operations have to be executed over and over again as long as the server is running.

Thread Ops contains 2 types of thread loops. One which can be paused, and one which cannot.
Here are two quick examples:


    ThreadLoop threadLoop = new ThreadLoop(() -> {
        System.out.println("Repeated Task");
    });
    threadLoop.start();


    ThreadLoopPausable threadLoop = new ThreadLoopPausable(() -> {
        System.out.println("Repeated Task");
        return 500;  //pause 500 milliseconds before next execution
    });
    threadLoop.start();



<a name="repeated-tasks"></a>
## Repeated Tasks
Repeated tasks are tasks that are repeated over and over again for as long as the application is running.
This will typically be tasks like check if there is any new inbound data to read from a non-blocking socket (NIO),
or any data to write, or any other kind of task your application needs to repeat on a regular basis.

Repeated tasks are typically executed inside a thread loop. A ThreadLoop can execute a single IRepeatedTask
repeatedly in its loop. However, Thread Ops comes with IRepeatedTask implementations which can themselves execute
multiple IRepeatedTasks, so you can execute more than one IRepeatedTask inside the same ThreadLoop.

Repeated tasks can be executed by an executor that can pause the task for an amount of nano-seconds decided by
the repeated task.


<a name="one-time-tasks"></a>
## One Time Tasks
A one time task is a task that is executed just one time - typically as a response to e.g. an incoming message
from a client (over the network) etc.

There is currently no one time task execution mechanism, but you can always just execute a task immediately yourself.



<a name="one-time-sliced-tasks"></a>
## One Time Sliced Tasks

A sliced task is a longer-running one time task. It runs so long that it would unfairly monopolize the CPU, and thus
block other short-lived tasks from completing quickly. To avoid this, longer-running tasks can be designed as sliced tasks
which can be executed one slice at a time, just like a thread is given a slice of time to execute.

Thread Ops does not yet have a sliced task executor, but it's planned and designed.



<a name="thread-message-ports"></a>
## Thread Message Ports

A Thread message port is a port through which different threads can exchange messages, similarly to a network connection.

Thread Ops does not yet have a thread message port, but we have planned to implement one.
