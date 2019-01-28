package com.tin.pattern.pattern01.factory.factory3.abstr;

import com.tin.pattern.pattern01.factory.Mengniu;
import com.tin.pattern.pattern01.factory.Milk;
import com.tin.pattern.pattern01.factory.Telunsu;
import com.tin.pattern.pattern01.factory.Yili;

public class MilkFactory extends AbstractFactory {

    @Override
    Milk getMengniu() {
        return new Mengniu();
    }

    @Override
    Milk getTelunsu() {
        return new Telunsu();
    }

    @Override
    Milk getYili() {
        return new Yili();
    }

}
