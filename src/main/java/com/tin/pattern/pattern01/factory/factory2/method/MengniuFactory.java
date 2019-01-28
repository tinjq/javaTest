package com.tin.pattern.pattern01.factory.factory2.method;

import com.tin.pattern.pattern01.factory.Mengniu;
import com.tin.pattern.pattern01.factory.Milk;

public class MengniuFactory implements Factory {

    @Override
    public Milk getMilk() {
        return new Mengniu();
    }

}
