package com.pawn.glave.app.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.sys.dao.SysFileDao;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import com.pawn.glave.app.modules.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {
    @Value("${web.upload-path}")
    private String fileUploadPath;

    @Override
    public R upload(MultipartFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String oldName = file.getOriginalFilename();
        String prefix=oldName.substring(oldName.lastIndexOf(".")+1);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+"."+prefix;
        try {
            String path = fileUploadPath+"files/" + date + "/";
            File f = new File(path);
            if(!f.exists()){
                f.mkdirs();
            }
            Files.write(Paths.get(path+fileName), file.getBytes());
            SysFileEntity sysFileEntity = SysFileEntity
                    .builder()
                    .fileName(fileName)
                    .fileOldName(oldName)
                    .fileType(prefix)
                    .fileUploadTime(new Date())
                    .fileUrl("/files/" +date+"/"+fileName).build();
            this.save(sysFileEntity);
            return R.ok("上传成功").put("file_id",sysFileEntity.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败。");
        }
    }
}
