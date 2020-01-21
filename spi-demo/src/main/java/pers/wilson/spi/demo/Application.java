package pers.wilson.spi.demo;

import pers.wilson.spi.demo.service.IShout;

import java.util.ServiceLoader;

public class Application {

    public static void main(String[] args) {
        ServiceLoader<IShout> shouts = ServiceLoader.load(IShout.class);
        for (IShout s : shouts) {
            s.shout();
        }
    }
}
