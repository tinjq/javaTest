package com.tin.pattern.pattern01.factory.factory1.simple;

import com.tin.pattern.pattern01.factory.Mengniu;
import com.tin.pattern.pattern01.factory.Milk;
import com.tin.pattern.pattern01.factory.Telunsu;
import com.tin.pattern.pattern01.factory.Yili;

/**
 * 简单工厂模式
 */
public class SimpleFactory {

    public Milk getMilk(String name) {
        if ("特仑苏".equals(name)) {
            return new Telunsu();
        } else if ("蒙牛".equals(name)) {
            return new Mengniu();
        } else if ("伊利".equals(name)) {
            return new Yili();
        } else {
            System.out.println("不能生产牛奶：" + name);
            return null;
        }
    }

}
