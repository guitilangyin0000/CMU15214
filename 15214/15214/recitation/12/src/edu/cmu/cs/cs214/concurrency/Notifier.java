package edu.cmu.cs.cs214.concurrency;


public class Notifier implements Runnable {
    final Object lock;

    public Notifier(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized(lock) {
            lock.notifyAll();
        }
        System.out.println("The Notifier is finished.");
    }
}
