package edu.cmu.cs.cs214.concurrency;


public class SynchronizedIncrementTest implements Runnable {
    static int classData = 0;
    int        instanceData = 0;

    static final Object classLock = new Object();
    final Object        instanceLock = new Object();
    
    @Override
    public void run() {
        int localData = 0;
        
        while (localData < 10000000) {
            localData++;
            //synchronized(instanceLock) { classData++; }
            synchronized(instanceLock)    { classData++;    }
   
        }
        
        System.out.println("localData:  " + localData + 
                           "\tinstanceData:  " + instanceData + 
                           "\tclassData:  " + classData);
    }
    

    public static void main(String[] args) {
        SynchronizedIncrementTest instance1 = new SynchronizedIncrementTest();
        SynchronizedIncrementTest instance2 = new SynchronizedIncrementTest();
        
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);

        t1.start();

//        try {
//            Thread.sleep(1000);  // sleeps 1000 milliseconds
//        } catch (InterruptedException e) {
//            // unlikely, so we'll ignore it
//        }

        t2.start();
    }

}
