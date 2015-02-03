package edu.cmu.cs.cs214.concurrency;


public class NotifyExample {
    public static void main(String[] args) {
        Object lock = new Object();
        Blocker blocker = new Blocker(lock);
        Notifier notifier = new Notifier(lock);

        Thread t1 = new Thread(blocker);
        Thread t2 = new Thread(notifier);

        t1.start();
        t2.start();
    }
}
