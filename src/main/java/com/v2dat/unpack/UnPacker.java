package com.v2dat.unpack;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * @author by liujiawei
 * @date 2024/8/8 14:24
 */
public interface UnPacker {

    /**
     * 数据处理
     *
     * @param dataPath 原始数据位置
     * @param outPath  解析后数据写入目录
     * @param tags     需要过滤的文件tag
     * @param clean    写入前是否需要清理历史文件
     */
    void unpack(String dataPath, String outPath, List<String> tags, boolean clean) throws IOException;

    /**
     * 解析类型
     *
     * @return geoip geosite
     */
    String getFilePrefix();

    /**
     * 清理指定目录下的所有子目录和子文件
     *
     * @param path 指定目录
     */
    default void cleanPath(String path) throws IOException {
        Path directory = Paths.get(path);
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                // 保留顶级目录本身
                if (!dir.equals(directory)) {
                    Files.delete(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 获取写入文件的绝对路径
     */
    default String getFileName(String path, String tag) {
        return String.format("%s/%s_%s.txt", path, this.getFilePrefix(), tag);
    }
}
