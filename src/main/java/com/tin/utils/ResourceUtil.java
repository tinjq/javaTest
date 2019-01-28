package com.tin.utils;

import java.io.Closeable;
import java.io.IOException;

public class ResourceUtil {

    /**
     * 关闭资源
     * @param closeables
     */
    public static void closeResource(Closeable... closeables) {
        for (Closeable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                c = null;
            }
        }
    }

}
