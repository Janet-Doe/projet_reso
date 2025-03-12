package server;

/**
 * Timer which interrupt a Thread when limit is passed.
 */
public class Timer extends Thread {

    private long timeStart;
    private boolean stop = false;
    private final Thread thread;
    private final int limit;

    /**
     * Timer which interrupt the Thread given when limit is passed.
     * @param thread Thread
     * @param limit int, time in seconds
     */
    public Timer(Thread thread, int limit) {
        this.thread = thread;
        this.limit = limit;
    }

    public int getTime() {
        long currentTime = System.currentTimeMillis();
        return (int) (currentTime - timeStart)/1000;

    }

    public void reset() {
        this.timeStart = System.currentTimeMillis();
    }

    public void close() {
        this.stop = true;
    }

    @Override
    public void run() {
        this.timeStart = System.currentTimeMillis();
        while (!stop) {
            if (getTime() > limit) {
                System.out.println("thread interruption : "+thread.getName());
                this.thread.interrupt();
                this.close();
            }
        }
    }
}
