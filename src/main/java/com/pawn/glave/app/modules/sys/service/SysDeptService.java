package com.pawn.glave.app.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

public interface SysDeptService extends IService<SysDeptEntity> {

    List<SysDeptEntity> deptList();

    Map<String,Object> getParentInfo(String id);

    Map<String,Object> getDeptInfo(String id);

    void saveOrUpdate(Map<String, Object> dept);

    void delete(int id);

    Map<String,Object> getUserDept(Long user_id);

    void saveUserDept(Long user_id, String dept_id, String isLeader);

    void deleteUserDept(Long user_id, String dept_id);

    PageUtils findUserByDept(Map<String, Object> param);
}
