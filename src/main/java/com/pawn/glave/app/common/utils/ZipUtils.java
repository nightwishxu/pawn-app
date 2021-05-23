package com.pawn.glave.app.common.utils;

import com.pawn.glave.app.modules.certificate.entity.ZipEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author WBC
 * @date 2021/5/23 19:12
 * @title ZipUtils
 */
public class ZipUtils {
    /**
     * 把文件打成压缩包并输出到客户端浏览器中
     */
    public static void downloadZipFiles(List<String> srcFiles, String zipFileName) throws IOException {
        System.out.println("压缩文件...");
        String fileName = new String(zipFileName.getBytes(), "UTF-8"); // 必须转码，否则会丢失中文文件名。
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileName));
        try {
            byte[] zipFile = zipFile(srcFiles, "", zos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {  // 必须释放以及关闭，否则可能会出现压缩的文件出现损坏。
            zos.flush();
            zos.close();
        }
        System.out.println("压缩完成");
    }

    private static byte[] zipFile(List<String> subs, String baseName, ZipOutputStream zos) throws IOException {

        try {
            byte[] buffer = null;
            for (int i = 0; i < subs.size(); i++) {
                File f = new File(subs.get(i));
                zos.putNextEntry(new ZipEntry(baseName + f.getName()));
                FileInputStream fis = new FileInputStream(f);
                buffer = new byte[1024];
                int r = 0;
                while ((r = fis.read(buffer)) != -1) zos.write(buffer, 0, r);
                fis.close();
            }
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {     // 必须释放和关闭，否则可能会出现压缩的文件出现损坏。
            zos.flush();
            zos.close();
        }
    }
}
