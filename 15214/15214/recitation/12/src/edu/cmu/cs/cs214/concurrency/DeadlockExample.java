package edu.cmu.cs.cs214.concurrency;


public class DeadlockExample implements Runnable {
    Counter c1, c2;
    
    public DeadlockExample(Counter c1, Counter c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void run() {
        while (true) {
            synchronized(c1) {
                synchronized(c2) { 
                    c1.increment();
                    c2.increment();
                    System.out.println(c1.getValue() + "\t" + c2.getValue());
                }
            }
        }
    }
    

    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        DeadlockExample locker1 = new DeadlockExample(counter1, counter2);
        DeadlockExample locker2 = new DeadlockExample(counter2, counter1);
        
        Thread t1 = new Thread(locker1);
        Thread t2 = new Thread(locker2);

        t1.start();
        t2.start();
    }

}
