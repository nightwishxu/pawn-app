package com.pawn.glave.app.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.sys.entity.SysFileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SysFileService extends IService<SysFileEntity> {
    R upload(MultipartFile file);
}
