package com.admin.hanfusiadmin.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastDFSClient {


    private static final String CONFIG = "client.conf";
    /**
     * 上传文件
     * @param is
     * @param
     * @return
     */
    public static String UploadFile(InputStream is){
        String filepath2 = null;
        try {
            ClientGlobal.init(CONFIG);
            //3、创建一个TrackerClient对象。
            TrackerClient trackerClient = new TrackerClient();

            // 4、创建一个TrackerServer对象。
            TrackerServer trackerServer = trackerClient.getConnection();

            // 5、声明一个StorageServer对象，null。
            StorageServer storageServer = null;

            // 6、获得StorageClient对象。
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            byte[] v =null;

            v = new byte[is.available()];
            is.read(v,0,v.length);

            String[] strings = storageClient.upload_file(v,"jpg" , null);
            filepath2 = strings[0]+"/"+strings[1];
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (MyException e)
        {
            e.printStackTrace();
        }
        return filepath2;
    }

}
