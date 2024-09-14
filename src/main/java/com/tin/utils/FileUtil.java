package com.tin.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * description
 *
 * @since 2024-05-25
 */
public class FileUtil {

    public static String readFileToString(String classPath) throws IOException {
        URL url = FileUtil.class.getClassLoader().getResource(classPath);
        System.out.println(url.getPath());
        File file = new File(url.getPath());
        System.out.println(file.getPath());
        return new String(Files.readAllBytes(Paths.get(file.getPath())));
    }

    public static void main(String[] args) throws IOException {
        String dirStr = "E:\\video\\vue";
        String path = "json/aa.json";
        String jsonStr = readFileToString(path);
        File dir = new File(dirStr);
        File[] files = dir.listFiles();
        List<JSONObject> list = JSONUtil.parseArray(jsonStr).toList(JSONObject.class);
        Arrays.asList(files).forEach(file -> {
            String fileName = file.getName();
            String index = fileName.substring(1, fileName.length() - 4);
            Optional<JSONObject> optinal = list.stream()
                    .filter(obj -> obj.getStr("page").equals(index)).findAny();
            if (optinal.isPresent()) {
//                    System.out.println(dirStr + "\\P" + index + " " + optinal.get().getStr("part") + ".mp4");
                file.renameTo(new File(dirStr + "\\P" + index + " " + optinal.get().getStr("part") + ".mp4"));
            }
        });
//        list.forEach(obj -> {
//            System.out.println(obj.getStr("page"));
//            System.out.println(obj.getStr("part"));
//            System.out.println("-----------------");
//        });
    }
}
