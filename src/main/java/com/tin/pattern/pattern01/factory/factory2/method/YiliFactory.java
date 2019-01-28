package com.tin.pattern.pattern01.factory.factory2.method;

import com.tin.pattern.pattern01.factory.Milk;
import com.tin.pattern.pattern01.factory.Yili;

public class YiliFactory implements Factory {

    @Override
    public Milk getMilk() {
        return new Yili();
    }

}
