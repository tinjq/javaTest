package com.tin.pattern.pattern01.factory.factory3.abstr;

import com.tin.pattern.pattern01.factory.Milk;

/**
 * 抽象工厂模式
 */
public abstract class AbstractFactory {

    abstract Milk getMengniu();

    abstract Milk getTelunsu();

    abstract Milk getYili();

}
