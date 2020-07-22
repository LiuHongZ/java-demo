import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author
 * 日期 2019/12/26 11:09
 * 描述 测试阻塞队列
 * @version 1.0
 * @since 1.0
 */
public class TestBlockingQueue {

    private static BlockingQueue<String> filequeue = new LinkedBlockingQueue<>(1000);

    public static void main(String[] args) {
        try {
            filequeue.add("txt");
//            get "txt"
            System.out.println(filequeue.take());
//            Blocking waiting data ...
            System.out.println(filequeue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
