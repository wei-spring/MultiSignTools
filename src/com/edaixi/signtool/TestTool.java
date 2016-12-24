package com.edaixi.signtool;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.*;

/**
 * Created by wei_spring on 2016/12/22.
 */
public class TestTool {

    public static void main(String[] args) throws Exception {
        //addChannelFile(new File("e袋洗_V5.3.0_20161221105449_GodChannel.apk"));
        //System.out.println("-------------------------------------------");
        //addChannelFileBak(new File("app-debug.apk"), new File("META-INF/cztchannel_WanDouJia"));
        //addZip();
        //System.out.println("-------------------------------------------");
        //addChannelFile(new File("app-debug.apk"));
        //SimpleDateFormat df = new SimpleDateFormat("当前时间: yyyy年MM月dd日 HH:mm:ss");
        //System.out.println(df.format(new Date()));
        System.out.println(System.getProperty("os.name"));
    }

    public static void addChannelFile(File zipFile) {
        try {

//            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
//            //实例化一个名称为ab.txt的ZipEntry对象
//            ZipEntry entry = new ZipEntry("META-INF/cztchannel_WanDouJia");
//            //设置注释
//            zos.setComment("zip测试for单个文件");
//            //把生成的ZipEntry对象加入到压缩文件中，而之后往压缩文件中写入的内容都会放在这个ZipEntry对象里面
//            zos.putNextEntry(entry);
//            InputStream is = new FileInputStream("edaixi.txt");
//            int len = 0;
//            while ((len = is.read()) != -1)
//                zos.write(len);
//            is.close();
//            zos.close();


            ZipFile zFile = new ZipFile(zipFile);
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry = null;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                System.out.println(fileName);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addZip() throws Exception {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        Path path = Paths.get("e袋洗_V5.3.0_20161221105449_GodChannel.apk");
        URI uri = URI.create("jar:" + path.toUri());
        try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
            Path nf = fs.getPath("META-INF/cztchannel_WanDouJia");
            try (Writer writer = Files.newBufferedWriter(nf, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                writer.write("testzip");
            }
        }
    }

    public static void addChannelFileBak(File zipFile,
                                         File files) throws IOException {
        // get a temp file
        File tempFile = File.createTempFile(zipFile.getName(), null);
        // delete it, otherwise you cannot rename your existing zip to it.
        tempFile.delete();

        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            throw new RuntimeException("could not rename the file " + zipFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
        }
        byte[] buf = new byte[1024];

        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        ZipEntry entry = zin.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            boolean notInFiles = true;
            if (files.getName().equals(name)) {
                notInFiles = false;
                break;
            }
            if (notInFiles) {
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(name));
                // Transfer bytes from the ZIP file to the output file
                int len;
                while ((len = zin.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            entry = zin.getNextEntry();
        }
        // Close the streams
        zin.close();
        // Compress the files
        InputStream in = new FileInputStream(files);
        // Add ZIP entry to output stream.
        out.putNextEntry(new ZipEntry(files.getName()));
        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        // Complete the entry
        out.closeEntry();
        in.close();
        // Complete the ZIP file
        out.close();
        tempFile.delete();
    }
}
