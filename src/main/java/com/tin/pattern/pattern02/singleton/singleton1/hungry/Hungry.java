package com.tin.pattern.pattern02.singleton.singleton1.hungry;

/**
 * Created by Tin on 2018/11/12.
 */
public class Hungry {

    private static final Hungry instance = new Hungry();

    private Hungry() {}

    public static Hungry getInstance() {
        return instance;
    }

}
