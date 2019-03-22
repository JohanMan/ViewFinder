package com.johan.view.finder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 2018/9/9.
 */

public class FileUtil {

    /**
     * 查找 Layout 文件
     * @param layout
     * @return
     */
    public static File findLayout(String layout) {
        // 能获取到工程目录 但是不能直接使用 否则调用projectFile.listFiles()时返回null
        File projectFile = new File("");
        File parent = new File(projectFile.getAbsolutePath());
        List<File> fileList = new ArrayList<>();
        searchFile(parent, layout, fileList);
        if (fileList.size() == 0) return null;
        return fileList.get(0);
    }

    /**
     * 查找文件
     * @param parent
     * @param layout
     * @param fileList
     */
    public static void searchFile(File parent, String layout, List<File> fileList) {
        if (parent.isFile()) return;
        if (fileList.size() > 0) return;
        for (File file : parent.listFiles()) {
            if (file.isFile()) {
                if (file.getName().equals(layout)) {
                    fileList.add(file);
                    return;
                }
            } else {
                searchFile(file, layout, fileList);
            }
        }
    }

}
