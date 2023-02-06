package com.zero.record.demo;

public class Plane {
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("startDate" + start);

        System.out.println("running...");

        long time = System.currentTimeMillis() - start;
        System.out.println("spend time: " + time);
    }

    public void fly() {
        //性能检测代码
        long start = System.currentTimeMillis();
        System.out.println("startDate" + start);

        System.out.println("fly...");

        //性能检测代码
        long time = System.currentTimeMillis() - start;
        System.out.println("spend time: " + time);
    }
}
