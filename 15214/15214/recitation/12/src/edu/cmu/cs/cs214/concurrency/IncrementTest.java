package edu.cmu.cs.cs214.concurrency;


public class IncrementTest implements Runnable {
    static int classData = 0;
    int        instanceData = 0;
    
    @Override
    public void run() {
        int localData = 0;
        
        while (localData < 10000000) {
            localData++;
            instanceData++;
            classData++;
        }
        
        System.out.println("localData:  " + localData + 
                           "\tinstanceData:  " + instanceData + 
                           "\tclassData:  " + classData);
    }
    

    public static void main(String[] args) {
        IncrementTest instance = new IncrementTest();
        
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();

        try {
            Thread.sleep(1000);  // sleeps 1000 milliseconds
        } catch (InterruptedException e) {
            // unlikely, so we'll ignore the exception
        }

        t2.start();
    }

}
