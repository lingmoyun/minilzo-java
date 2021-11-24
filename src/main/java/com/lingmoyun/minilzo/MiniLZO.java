package com.lingmoyun.minilzo;

/**
 * miniLZO
 * C语言实现，通过jni封装
 *
 * @author guoweifeng
 */
public class MiniLZO {

    static {
        //System.loadLibrary("minilzo_java");
        LibLoader.loadLib("minilzo_java");
        init();
    }

    private static native void init();

    public static native byte[] compress(byte[] src);

    public static native byte[] decompress(byte[] src);

}
