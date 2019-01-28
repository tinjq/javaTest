package com.tin.java.core.io;

import java.io.*;

public class IOTest {

    private static final String fileIn = "D:/a.txt";
    private static final String fileOut = "D:/b.txt";

    public static void main(String[] args) {
//		fileStreamTest();
//        bufferedReaderTest();
        systemInTest();
    }

    public static void fileStreamTest() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileIn);
            fos = new FileOutputStream(fileOut);
            int i = 0;
            while ((i = fis.read()) != -1) {
                fos.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(fis, fos);
        }
    }

    public static void bufferedReaderTest() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(br, bw);
        }
    }

    public static void systemInTest() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeStream(Closeable... closeables) {
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
