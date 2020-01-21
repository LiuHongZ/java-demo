package pers.wilson.threadstop.demo;

public class ThreadImpl extends Thread {

    @Override
    public void run() {
        while (true) {
            if(Thread.interrupted()) {
                return;
            }
        }
    }
}
