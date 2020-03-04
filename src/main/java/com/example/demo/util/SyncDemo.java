package com.example.demo.util;

public class SyncDemo {
    public void test() {
        synchronized (this){
            System.out.println("start Test");
        }
    }

}
