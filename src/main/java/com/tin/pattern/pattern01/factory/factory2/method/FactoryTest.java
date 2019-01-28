package com.tin.pattern.pattern01.factory.factory2.method;

import com.tin.pattern.pattern01.factory.factory2.method.TelunsuFactory;

public class FactoryTest {

    public static void main(String[] args) {
        Factory factory = new TelunsuFactory();
        System.out.println(factory.getMilk().getName());
    }

}
