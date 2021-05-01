package com.pawn.glave.app.modules.app.service;

import com.pawn.glave.app.modules.app.entity.AppraisalAgain;
import com.pawn.glave.app.modules.app.param.AgainAppraisalSaveParam;

public interface AgainAppraisalService {
    /**
     *
     * @param param
     */
    void save(AgainAppraisalSaveParam param);

    /**
     * 根据再次申请ID 获取再次申请的详细信息
     * @param againId
     * @return
     */
    AppraisalAgain infoByAgainId(String againId);
}
