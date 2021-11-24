package com.lingmoyun.minilzo;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * LibLoader
 *
 * @author guoweifeng
 * @date 2021/4/22 16:51
 */
class LibLoader {
    public static final String LIBS_DIR = "/libs/";

    public static void loadLib(String libname) {
        String libFileName = libFileName(libname);
        String tmpLibsDir = System.getProperty("java.io.tmpdir") + "/libs/";
        File tmpLibsFolder = new File(tmpLibsDir);
        tmpLibsFolder.mkdirs();
        File libFile = new File(tmpLibsFolder, libFileName);
        try {
            if (!libFile.exists() || !md5DigestAsHex(new FileInputStream(libFile)).equalsIgnoreCase(md5DigestAsHex(Objects.requireNonNull(LibLoader.class.getResourceAsStream(LIBS_DIR + libFileName))))) {
                try {
                    InputStream in = LibLoader.class.getResourceAsStream(LIBS_DIR + libFileName);
                    OutputStream out = new FileOutputStream(libFile);
                    streamCopy(in, out);
                    out.close();
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to load required lib", e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.load(libFile.getAbsolutePath());
    }

    private static String libFileName(String libname) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return libname + ".dll";
        } else if (os.toLowerCase().startsWith("linux")) {
            return "lib" + libname + ".so";
        } else {
            System.err.println("not support system " + os);
            System.exit(1);
        }
        return null;
    }

    private static int streamCopy(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[4096];

        int bytesRead;
        for (boolean var4 = true; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
            out.write(buffer, 0, bytesRead);
        }

        out.flush();
        return byteCount;
    }

    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String md5DigestAsHex(InputStream inputStream) throws IOException {
        String algorithm = "MD5";

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", var2);
        }

        byte[] buffer = new byte[4096];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }

        byte[] digest = messageDigest.digest();

        char[] hexDigest = new char[32];

        for (int i = 0; i < hexDigest.length; i += 2) {
            byte b = digest[i / 2];
            hexDigest[i] = HEX_CHARS[b >>> 4 & 15];
            hexDigest[i + 1] = HEX_CHARS[b & 15];
        }

        return new String(hexDigest);
    }

}
