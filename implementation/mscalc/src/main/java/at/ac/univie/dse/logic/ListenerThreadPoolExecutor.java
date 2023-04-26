package at.ac.univie.dse.logic;
import java.util.concurrent.LinkedBlockingQueue;

public class ListenerThreadPoolExecutor {
    private WorkerListener workerListener;
    private final int threadsCount;
    private final ExecutionThread[] executors;
    private final LinkedBlockingQueue<Runnable> queue;

    public ListenerThreadPoolExecutor(int threadsCount, WorkerListener workerListener) {
        this.threadsCount = threadsCount;
        this.workerListener = workerListener;
        this.executors = new ExecutionThread[threadsCount];
        this.queue = new LinkedBlockingQueue<>();
        this.workerListener = workerListener;

        for (int i = 0; i < threadsCount; i++) {
            executors[i] = new ExecutionThread();
            executors[i].start();
        }
    }

    public void execute(Runnable command) {
        synchronized (queue) {
            queue.add(command);
            queue.notify();
            workerListener.didStartExecuting();
        }
    }

    public void shutdown() {
        for (int i = 0; i < threadsCount; i++) {
            executors[i] = null;
        }
    }

    private class ExecutionThread extends Thread {
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {

                        try {
                            workerListener.didBecomeIdle();
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = queue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
