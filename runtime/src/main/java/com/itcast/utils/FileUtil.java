package com.itcast.utils;

import java.io.*;

public class FileUtil {
    /**
     * 创建文件
     */
   /* public static File createFile(String filePath, String fileName) throws IOException {
        if (filePath != null && filePath.length()>0){
            File file = new File(filePath,fileName);
            if (!file.exists()){
                file.createNewFile();
            }
        }
        return null;
    }*/
    public static String readFile(String filePath) throws IOException {
        int len = 2048;
        byte[] bytes = new byte[len];
        String str = "";
        if (filePath!=null && filePath.length()>0){
            InputStream in = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(in);
            while ((bis.read(bytes,0,len))!=-1){
                str = new String(bytes,"GBK");
            }
        }
        return str;
    }
    public static void writeFile(String filePath,String contents) throws IOException {
        OutputStream os = new FileOutputStream(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] bytes = contents.getBytes("GBK");
        bos.write(bytes);
        bos.close();
    }
    public static void main(String[] args) throws IOException {
//        File file1 = createFile("D:\\","xiaowang.txt");
        /*String contents = readFile("D:\\xiaowang.txt");
        System.out.print(contents);*/
        writeFile("E:\\token.txt","呵呵");
    }
}
