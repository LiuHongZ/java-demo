package pers.wilson.threadstop.demo;

public class RunnableImpl implements Runnable {

    private volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {

        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
