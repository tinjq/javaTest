package com.tin.pattern.pattern01.factory.factory3.abstr;

public class AbstractFactoryTest {

    public static void main(String[] args) {
        MilkFactory factory = new MilkFactory();
        System.out.println(factory.getMengniu().getName());
    }

}
