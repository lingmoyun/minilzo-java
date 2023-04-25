#include "com_lingmoyun_minilzo_MiniLZO.h"


/*
 * Class:     com_lingmoyun_minilzo_MiniLZO
 * Method:    init
 * Signature: ()V
 * Author:    guoweifeng(winfordguo@gmail.com)
 */
JNIEXPORT void JNICALL Java_com_lingmoyun_minilzo_MiniLZO_init(JNIEnv *env, jclass thiz) {
    //printf("\nLZO real-time data compression library (v%s, %s).\n", lzo_version_string(), lzo_version_date());
    //printf("Copyright (C) 1996-2017 Markus Franz Xaver Johannes Oberhumer\nAll Rights Reserved.\n\n");


    /*
     * Step 1: initialize the LZO library
     */
    if (lzo_init() != LZO_E_OK) {
        printf("internal error - lzo_init() failed !!!\n");
        printf("(this usually indicates a compiler bug - try recompiling\nwithout optimizations, and enable '-DLZO_DEBUG' for diagnostics)\n");
    }
}

/*
 * Class:     com_lingmoyun_minilzo_MiniLZO
 * Method:    compress
 * Signature: ([B)B
 */
JNIEXPORT jbyteArray JNICALL
Java_com_lingmoyun_minilzo_MiniLZO_compress(JNIEnv *env, jclass cla, jbyteArray src) {
    int r;
    jbyte *in;
    jint in_len;
    jbyteArray out;
    jlong out_len;
    unsigned char __LZO_MMODEL *buf;

    in = (*env)->GetByteArrayElements(env, src, NULL);
    in_len = (*env)->GetArrayLength(env, src);

    buf = (char *) malloc((size_t) in_len + in_len / 16 + 64 + 3);
    lzo_align_t *wrk = (lzo_align_t *)malloc(sizeof(wrkmem));

    //r = lzo1x_1_compress(in, in_len, buf, &out_len, wrkmem); // 共享工作区，不适用于多线程
    r = lzo1x_1_compress(in, in_len, buf, &out_len, wrk);
    if (r == LZO_E_OK) {
        out = (*env)->NewByteArray(env, (jsize) out_len);
        //将buf中的值复制到jbyteArray中去，数组copy
        (*env)->SetByteArrayRegion(env, out, 0, (jsize) out_len, buf);
    } else
        out = NULL;

    free(wrk);
    free(buf);

    return out;
}

/*
 * Class:     com_lingmoyun_minilzo_MiniLZO
 * Method:    decompress
 * Signature: ([B)B
 */
JNIEXPORT jbyteArray JNICALL
Java_com_lingmoyun_minilzo_MiniLZO_decompress(JNIEnv *env, jclass cla, jbyteArray src) {
    int r;
    jbyte *in;
    jint in_len;
    jbyteArray out;
    jlong out_len;
    unsigned char __LZO_MMODEL *buf;

    in = (*env)->GetByteArrayElements(env, src, NULL);
    in_len = (*env)->GetArrayLength(env, src);

    buf = (char *) malloc((size_t) in_len * 30);

    r = lzo1x_decompress(in, in_len, buf, &out_len, NULL);
    if (r == LZO_E_OK) {
        out = (*env)->NewByteArray(env, (jsize) out_len);
        //将buf中的值复制到jbyteArray中去，数组copy
        (*env)->SetByteArrayRegion(env, out, 0, (jsize) out_len, buf);
    } else
        out = NULL;

    free(buf);

    return out;
}