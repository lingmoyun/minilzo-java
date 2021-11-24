package com.lingmoyun.example;

import com.lingmoyun.minilzo.MiniLZO;

/**
 * 示例
 *
 * @author guoweifeng
 */
public class MiniLZOExample {

    public static void main(String[] args) {
        String str = "Hello miniLZO!";
        byte[] origin = str.getBytes();
        byte[] compressed = MiniLZO.compress(origin);
        byte[] decompressed = MiniLZO.decompress(compressed);
        String str1 = new String(decompressed);
        System.out.println("str = " + str); // str = Hello miniLZO!
        System.out.println("origin.length = " + origin.length); // origin.length = 14
        System.out.println("compressed.length = " + compressed.length); // compressed.length = 18
        System.out.println("decompressed.length = " + decompressed.length); // decompressed.length = 14
        System.out.println("str1 = " + str1); // str1 = Hello miniLZO!
        System.out.println("str.equals(str1) = " + str.equals(str1)); // str.equals(str1) = true
    }

}
