package pers.wilson.spi.demo.service.impl;

import pers.wilson.spi.demo.service.IShout;

public class Dog implements IShout {

    @Override
    public void shout() {
        System.out.println("wang wang");
    }
}
