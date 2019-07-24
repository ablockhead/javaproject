package com.admin.hanfusiadmin.utils;

import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadFileUtil {

    public static String uploadFile(MultipartFile file, String types){

        if (file.isEmpty()){
            throw new HanfusiException(HanfusiEnums.FILE_ERROR);
        }

        StringBuffer newFileName = new StringBuffer();
        try {
            String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            newFileName.append(types);
            newFileName.append("/");
            newFileName.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            newFileName.append(((int)(Math.random() * 9000) + 1000));
            newFileName.append(prefix);
            File newFile = new File("/home/ftpuser/www/images/" + newFileName);
//            File newFile = new File("F:/" + newFileName);

            if (!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }
            newFile.createNewFile();
            FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
        } catch (Exception e){
            throw new HanfusiException(HanfusiEnums.UPLOAD_ERROR);
        }

        return "https://luoshuchuanmei.com/images/" + newFileName.toString();
//        return "F:/" + newFileName.toString();
    }

    public static String screenshot(String filePath){

        String savePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".png";
        List<String> cutpic = new ArrayList<>();
        cutpic.add("/usr/local/ffmpeg-3.4/ffmpeg");
//        cutpic.add("D:/ffmpeg/bin/ffmpeg.exe");
        cutpic.add("-i");
        cutpic.add(filePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("1"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add(savePath); // 添加截取的图片的保存路径

        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savePath;
    }
}
