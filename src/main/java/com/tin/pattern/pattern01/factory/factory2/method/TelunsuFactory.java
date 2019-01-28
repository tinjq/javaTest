package com.tin.pattern.pattern01.factory.factory2.method;

import com.tin.pattern.pattern01.factory.Milk;
import com.tin.pattern.pattern01.factory.Telunsu;

public class TelunsuFactory implements Factory {

    @Override
    public Milk getMilk() {
        return new Telunsu();
    }

}
