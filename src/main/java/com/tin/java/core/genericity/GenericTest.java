package com.tin.java.core.genericity;

/**
 * Created by Administrator on 2019/2/25.
 */
public class GenericTest<T> implements GenericInterface<T> {

    private T t;

    @Override
    public void set(T t) {
        this.t = t;
    }

    @Override
    public T get() {
        return t;
    }

    public static void showKeyValue(GenericTest<?> gt) {
        System.out.println(gt.get());
    }

    public static void main(String[] args) {
        GenericTest<String> gs = new GenericTest<String>();
        gs.set("aaa");
        System.out.println(gs.get());

        GenericTest<Integer> gu = new GenericTest<Integer>();
        gu.set(111);
        System.out.println(gu.get());

        GenericTest<Number> gNumber = new GenericTest<Number>();
        gNumber.set(456);
        showKeyValue(gNumber);

        showKeyValue(gu);
    }

}
