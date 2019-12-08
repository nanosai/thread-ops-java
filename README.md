# Thread Ops for Java

Thread Ops for Java is an open source toolkit providing multithreading tools to support the implementation
of samethreaded, asynchronous, non-blocking IO distributed systems.

Thread Ops makes it easier to execute a task in a one-off thread, or to call a "function" repeatedly inside a loop in
its own thread, and several other thread related tasks.


## Tools

 - [Thread Loops](#thread-loops)
 - Sliced Tasks
 - Thread Message Ports



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
        System.out.println("Running");
    });
    threadLoop.start();


    ThreadLoopPausable threadLoop = new ThreadLoopPausable(() -> {
        System.out.println("Running");
        return 500;  //pause 500 milliseconds before next execution
    });
    threadLoop.start();


