package dev.mskory.util;

public class StopWatch {
    long startTime;

    public void start (){
        startTime = System.currentTimeMillis();
    }

    public void reset () {
        start();
    }

    public double getSeconds(){
        return (System.currentTimeMillis() - startTime) / 1000D;
    }
    public long getMillis(){
        return System.currentTimeMillis() - startTime;
    }
}
