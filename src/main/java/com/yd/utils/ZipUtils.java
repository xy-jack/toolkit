package com.hrsj.mcc.base.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * zip包打包工具类
 *
 * @author  da.yang@hand-china.com
 * @date    2020/11/30
 */
public class ZipUtils {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);


    /**
     * 创建zip包
     *
     * @param filePaths   需要压缩的文件地址列表（绝对路径）
     * @param zipFilePath 需要压缩到哪个zip文件（无需创建这样一个zip，只需要指定一个全路径）
     * @throws Exception
     */
    public static void createZipByFile(List<String> filePaths, String zipFilePath) throws Exception {
        logger.info("=====新流向导出==>>zip文件保存路径：{}", zipFilePath);

        // 将目标文件打包成zip导出
        File zipFile = new File(zipFilePath);

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

        // 创建文件夹
        make(filePaths, zos);

        // 关闭流
        IOUtils.closeQuietly(zos);

        // 删除临时文件
        deleteDir(filePaths);
    }

    /**
     * 根据指定路径删除文件
     * @param filePaths   文件路径集合
     */
    private static void deleteDir(List<String> filePaths) {
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if(file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 生成文件
     *
     * @param filePaths     文件路径
     * @param zos           输出流
     * @throws Exception
     */
    public static void make(List<String> filePaths, ZipOutputStream zos) throws Exception {
        for(String relativePath: filePaths){
            // 通过绝对路径找到file
            File sourceFile = new File(relativePath);
            // 文件输入流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            zos.putNextEntry(new ZipEntry(sourceFile.getName()));
            int count, bufferLen = 1024;
            byte[] b = new byte[bufferLen];
            while ((count = bis.read(b, 0, bufferLen)) != -1) {
                zos.write(b, 0, count);
            }

            IOUtils.closeQuietly(bis);
            zos.flush();
            zos.closeEntry();
        }
    }

    /**
     * main方法测试
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // zip文件存放位置
            String zipFilePath = "f:/text/test.zip";

            String fileName = "test.zip";
            List<String> filePaths = new ArrayList<>();
            filePaths.add(new String("F:\\text\\1.xlsx"));
            filePaths.add(new String("F:\\text\\2.xlsx"));
            filePaths.add(new String("F:\\text\\3.xlsx"));
            filePaths.add(new String("F:\\text\\4.xlsx"));
            filePaths.add(new String("F:\\text\\5.xlsx"));

            ZipUtils.createZipByFile(filePaths, zipFilePath);
            System.out.println("=====================打包完成。。。");
			/*resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			OutputStream out = resp.getOutputStream();
			IOUtils.write(data, out);
			out.flush();
			out.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}