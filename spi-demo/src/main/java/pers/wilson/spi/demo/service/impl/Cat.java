package pers.wilson.spi.demo.service.impl;

import pers.wilson.spi.demo.service.IShout;

public class Cat implements IShout {

    @Override
    public void shout() {
        System.out.println("miao miao");
    }
}
