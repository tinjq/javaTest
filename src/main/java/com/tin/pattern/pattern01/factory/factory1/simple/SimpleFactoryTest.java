package com.tin.pattern.pattern01.factory.factory1.simple;

import com.tin.pattern.pattern01.factory.Telunsu;

/**
 * 小作坊式的工厂模型
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {

        // 这个new的过程实际上是比较复杂的
        // 有人民币之后就不需要自己new了
        System.out.println(new Telunsu().getName());


        // 小作坊式的生产模式
        // 用户本身不再关心生产的过程，只需要关心结果

        SimpleFactory factory = new SimpleFactory();
        System.out.println(factory.getMilk("特仑苏").getName());

    }

}
