package pers.wilson.threadstop.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Predicate;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ThreadStopApplication {

    public static Logger logger = Logger.getLogger("Application");
    public static LogManager logManager = LogManager.getLogManager();

    static {
        try (InputStream in = ThreadStopApplication.class.getResourceAsStream("/logging.properties")) {
            logManager.readConfiguration(in);
        } catch (IOException e) {
            logger.warning("读取配置文件失败");
        }
        logManager.addLogger(logger);
    }

    /**
     * 创建多线程
     *
     * @param args a[0]: 创建的线程数
     *             a[1]: 执行的时间 单位（秒）
     */
    public static void main(String[] args) {

        Map<String, Integer> stringIntegerMap = validatorParam(args);
        Integer corePoolSize = stringIntegerMap.get("corePoolSize");
        Integer runningSecond = stringIntegerMap.get("runningSecond");

        logger.info("执行开始...");
        logger.info("线程数：" + corePoolSize + "，运行时长：" + runningSecond + "秒");

        long shutdownTime = System.currentTimeMillis() + runningSecond * 1000;

        threadImpl(corePoolSize, shutdownTime);
//        runnableImpl(corePoolSize, shutdownTime);
        logger.info("执行结束");
    }

    /**
     * 使用Thread实现
     *
     * @param corePoolSize 创建的线程数
     * @param shutdownTime 结束时间
     */
    private static void threadImpl(Integer corePoolSize, long shutdownTime) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        for (int i = 0; i < corePoolSize; i++) {
            executor.execute(new ThreadImpl());
        }
        while (true) {
            if (System.currentTimeMillis() >= shutdownTime) {
                executor.shutdownNow();
                return;
            }
        }
    }

    /**
     * 使用Runnable接口实现
     *
     * @param corePoolSize 创建的线程数
     * @param shutdownTime 结束时间
     */
    private static void runnableImpl(Integer corePoolSize, long shutdownTime) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        List<RunnableImpl> threads = new ArrayList<>();
        for (int i = 0; i < corePoolSize; i++) {
            RunnableImpl runnable = new RunnableImpl();
            threads.add(runnable);
            executor.execute(runnable);
        }
        while (true) {
            if (System.currentTimeMillis() >= shutdownTime) {
                threads.forEach(i -> i.setFlag(false));
                executor.shutdownNow();
                return;
            }
        }
    }

    /**
     * 参数校验
     *
     * @param args main args
     * @return
     */
    private static Map<String, Integer> validatorParam(String[] args) {
        if (args.length == 0) {
            logger.warning("缺少必要参数");
            throw new RuntimeException("缺少必要参数");
        }
        int corePoolSize;
        int runningSecond;
        try {
            corePoolSize = Integer.parseInt(args[0]);
            runningSecond = Integer.parseInt(args[1]);
        } catch (Exception e) {
            logger.warning("参数转换异常");
            throw new RuntimeException("参数转换异常");
        }
        Predicate<Integer> predicate = (s) -> s <= 0;
        if (predicate.test(corePoolSize)) {
            logger.warning("线程数不合法");
            throw new RuntimeException("线程数不合法");
        }
        if (predicate.test(runningSecond)) {
            logger.warning("运行时长不合法");
            throw new RuntimeException("运行时长不合法");
        }

        return new HashMap<String, Integer>() {{
            put("corePoolSize", corePoolSize);
            put("runningSecond", runningSecond);
        }};
    }
}
