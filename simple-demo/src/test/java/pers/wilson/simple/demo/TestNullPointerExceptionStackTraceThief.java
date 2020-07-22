package pers.wilson.simple.demo;

/**
 * @author
 * 日期 2019/12/26 12:05
 * 描述 测试JVM自动优化重复异常日志，可通过设置JVM参数 -XX:-OmitStackTraceInFastThrow 来禁用此项优化
 * @version 1.0
 * @since 1.0
 */
public class TestNullPointerExceptionStackTraceThief {

    public static void main(String[] args) {
        String x = null;
        for (int i = 0; i < 10000; i++) {
            try {
                System.out.println("当前执行次数为：" + i);
                getNPE(x);
            } catch (Exception e) {
                int length = e.getStackTrace().length;
//                没有全部执行完便已经获取不到异常的堆栈信息
                System.out.println("length：" + length);
                e.printStackTrace();
                if (length == 0) {
                    return;
                }
            }
        }
    }

    private static void getNPE(String x) {
        System.out.println(x.length());
    }
}
