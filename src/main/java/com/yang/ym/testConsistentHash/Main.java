package com.yang.ym.testConsistentHash;

import com.yang.ym.testDepend.Load;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author qcy
 * @create 2021/10/19 21:52:40
 */
public class Main {

    //真实节点
    private static final String[] ipArray = new String[]{"192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4"};
    //哈希环(哈希值->真实节点ip)
    private static final TreeMap<Long, String> circle = new TreeMap<>();

    //指定倍数初始化哈希环
    private static void initCircle(int mul) {
        //映射真实节点
        for (String ip : ipArray) {
            circle.put(hash(ip), ip);
            //按照倍数映射虚拟节点
            for (int i = 1; i <= mul; i++) {
                String virtual = ip + "#" + i;
                circle.put(hash(virtual), ip);
            }
        }
    }

    //获取指定文件存储的机器ip
    private static String getIpByFileName(String fileName) {
        long hash = hash(fileName);
        Long higherKey = circle.higherKey(hash);
        if (higherKey == null) {
            //返回哈希环中的第一个ip
            return circle.get(circle.firstKey());
        }
        //返回比文件名称的哈希值大的最小ip
        return circle.get(higherKey);
    }

    //统计落在每个节点上的文件总数(ip->文件总数)
    private static Map<String, Long> count(long fileCount) {
        //(ip,文件总数)
        Map<String, Long> map = new HashMap<>();

        for (long i = 1; i <= fileCount; i++) {
            String ip = getIpByFileName("file#" + i);
            Long ipCount = map.get(ip);
            map.put(ip, (ipCount == null ? 0 : ipCount) + 1);
        }

        return map;
    }

    //打印各个ip存储的文件数占总数的百分比
    private static void print(int fileCount) {
        Map<String, Long> map = count(fileCount);

        for (String ip : ipArray) {
            Long ipCountL = map.get(ip);
            long ipCount = ipCountL == null ? 0 : ipCountL;

            double result = ipCount * 100 / (double) fileCount;
            //保留一位小数
            String format = String.format("%.1f", result);
            System.out.println(ip + ":" + format + "%");
        }
    }

    // 32位的 Fowler-Noll-Vo 哈希算法
    // https://en.wikipedia.org/wiki/Fowler–Noll–Vo_hash_function
    private static Long hash(String key) {
        final int p = 16777619;
        long hash = 2166136261L;
        for (int idx = 0, num = key.length(); idx < num; ++idx) {
            hash = (hash ^ key.charAt(idx)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        //初始化哈希环
        initCircle(1000000);
        //文件总数1000000个
        print(1000000);
    }

}
//    public static void main(String[] args) {
//        //缓存失效计数
//        int count = 0;
//
//        for (int i = 0; i < 10000; i++) {
//            //文件名称
//            String fileName = "file#" + i;
//            int hashCode = fileName.hashCode();
//
//            //原来的存储位置
//            int oldSite = hashCode % 3;
//            //增加一台机器后，现在的存储位置
//            int newSite = hashCode % 5;
//
//            if (oldSite != newSite) {
//                count++;
//            }
//        }
//
//        System.out.println(count);
//    }

//    public static void main(String[] args) {
//        //缓存失效计数
//        int count = 0;
//
//        for (int i = 0; i < 10000; i++) {
//            //文件名称
//            String fileName = "file#" + i;
//            long hashCode = FNVHash(fileName);
//
//            //原来的存储位置
//            long oldSite = hashCode % 3;
//            //增加一台机器后，现在的存储位置
//            long newSite = hashCode % 2;
//
//            if (oldSite != newSite) {
//                count++;
//            }
//        }
//
//        System.out.println(count);
//    }

