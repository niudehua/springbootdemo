package cn.niudehua.springbootdemo;

/**
 * 类名称：RunTime
 * ***********************
 * <p>
 * 类描述：
 *
 * @author deng on 2021/3/4 21:08
 */
public class RunTime {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.maxMemory());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.freeMemory());
        String x = "";
        for (int i = 0; i < 30000; i++) {
            x += i;
        }
        System.out.println(runtime.maxMemory());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.freeMemory());
        runtime.gc();
        System.out.println(runtime.maxMemory());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.freeMemory());
    }
}