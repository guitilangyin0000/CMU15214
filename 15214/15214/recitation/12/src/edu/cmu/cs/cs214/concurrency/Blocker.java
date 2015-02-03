package edu.cmu.cs.cs214.concurrency;


public class Blocker implements Runnable {
    final Object lock;

    public Blocker(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized(lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                // We'll ignore this here...
            }
        }
        System.out.println("The Blocker is finished.");
    }
}

