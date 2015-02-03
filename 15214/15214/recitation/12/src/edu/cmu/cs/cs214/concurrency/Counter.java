package edu.cmu.cs.cs214.concurrency;


public class Counter {
    private int value = 0;
    
    public void increment() { value++; }
    public int getValue() { return value; }
}
