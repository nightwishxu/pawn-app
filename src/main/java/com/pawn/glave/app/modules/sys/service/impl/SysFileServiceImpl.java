package com.pawn.glave.app.modules.sys.service.impl;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.common.utils.StringUtils;
import com.pawn.glave.app.modules.app.utils.BaseUtils;
import com.pawn.glave.app.modules.sys.dao.SysFileDao;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {
    @Value("${web.upload-path}")
    private String fileUploadPath;

    @Value("${baidu.accessKey}")
    private   String ACCESS_KEY_ID;
    @Value("${baidu.secretKey}")
    private   String SECRET_ACCESS_KEY;
    @Value("${baidu.endPoint}")
    private   String ENDPOINT ;

    @Value("${baidu.bucketName}")
    private  String BUCKET_NAME ;

    @Value("${bos.url}")
    private String bosUrl;



    private static BosClientConfiguration config = new BosClientConfiguration();

    private static BosClient client =null;

    @Override
    public R upload(MultipartFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String oldName = file.getOriginalFilename();
        String prefix = oldName.substring(oldName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + prefix;
//        try {
//            String path = fileUploadPath + "files/" + date + "/";
//            File f = new File(path);
//            if (!f.exists()) {
//                f.mkdirs();
//            }
//            Files.write(Paths.get(path + fileName), file.getBytes());
//            SysFileEntity sysFileEntity = SysFileEntity
//                    .builder()
//                    .fileName(fileName)
//                    .fileOldName(oldName)
//                    .fileType(prefix)
//                    .fileUploadTime(new Date())
//                    .fileUrl("/files/" + date + "/" + fileName).build();
//            this.save(sysFileEntity);
//            return R.ok("上传成功").put("file_id", sysFileEntity.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error("上传失败。");
//        }
        try {
            putObject(file.getBytes(),"pawnFiles/"+fileName);
            SysFileEntity sysFileEntity = SysFileEntity
                    .builder()
                    .fileName(fileName)
                    .fileOldName(oldName)
                    .fileType(prefix)
                    .type(1)
                    .fileUploadTime(new Date())
                    .fileUrl(bosUrl+ "pawnFiles/"+fileName).build();
            this.save(sysFileEntity);
            return R.ok("上传成功").put("file_id", sysFileEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败。");
        }
    }


    public void putObject(byte[] byte1, String fileName){
//		// 获取指定文件
//		File file = new File("/path/to/file.zip");
//		// 获取数据流
//		InputStream inputStream = new FileInputStream("/path/to/test.zip");
        if (client ==null){
            config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
            config.setEndpoint(ENDPOINT);
            client = new BosClient(config);
        }
//		// 以文件形式上传Object
//		PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file);
//		// 以数据流形式上传Object
//		PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, objectKey, inputStream);
        // 以二进制串上传Object
        long start = System.currentTimeMillis();
        PutObjectResponse putObjectResponseFromByte = client.putObject(BUCKET_NAME, fileName, byte1);
        long end = System.currentTimeMillis();
//        logger.info("uploadFile time:{}s,fileName:{}",(end-start)/1000.0,fileName);
        // 以字符串上传Object
//		PutObjectResponse putObjectResponseFromString = client.putObject(bucketName, objectKey, string1);
//
//		// 打印ETag
//		System.out.println(putObjectFromFileResponse.getETag());
    }

    @Override
    public Long uploadUrl(String urlPath) {
        if (StringUtils.isEmpty(urlPath)) {
            return null;
        }
        MultipartFile file = BaseUtils.downloadFile(urlPath);
        if (file == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String oldName = file.getOriginalFilename();
        String prefix = oldName.substring(oldName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + prefix;
        try {
            String path = fileUploadPath + "files/" + date + "/";
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            Files.write(Paths.get(path + fileName), file.getBytes());
            SysFileEntity sysFileEntity = SysFileEntity
                    .builder()
                    .fileName(fileName)
                    .fileOldName(oldName)
                    .fileType(prefix)
                    .fileUploadTime(new Date())
                    .fileUrl("/files/" + date + "/" + fileName).build();
            this.save(sysFileEntity);
            return sysFileEntity.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadUrls(String urlPaths) {
        if (StringUtils.isEmpty(urlPaths)){
            return null;
        }
        String[] split = urlPaths.split(",");
        for (String s : split) {

        }

        return null;
    }

    @Override
    public String saveUrls(String urlPaths) {
        if (StringUtils.isEmpty(urlPaths)){
            return null;
        }

        String[] split = urlPaths.split(",");
        List<String> result = Lists.newArrayList();
        for (String s : split) {
            if (StringUtils.isNotEmpty(s)){
                SysFileEntity sysFileEntity = SysFileEntity
                        .builder()
                        .fileName("")
                        .fileOldName("")
                        .fileType("")
                        .type(1)
                        .fileUploadTime(new Date())
                        .fileUrl(s).build();
                this.save(sysFileEntity);
                result.add(String.valueOf(sysFileEntity.getId()));
            }
        }
        if (CollectionUtils.isNotEmpty(result)){
            return String.join(",",result);
        }else {
            return null;
        }
    }
}
