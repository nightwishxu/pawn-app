package com.pawn.glave.app.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.Query;
import com.pawn.glave.app.modules.sys.dao.SysDeptDao;
import com.pawn.glave.app.modules.sys.entity.SysDeptEntity;
import com.pawn.glave.app.modules.sys.service.SysDeptService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SysDeptServiceImpl  extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Resource
    private SysDeptDao sysDeptDao;

    @Override
    public List<SysDeptEntity> deptList() {
        return sysDeptDao.deptList();
    }

    @Override
    public Map<String, Object> getParentInfo(String id) {
        return sysDeptDao.getParentInfo(id);
    }

    @Override
    public Map<String, Object> getDeptInfo(String id) {
        return sysDeptDao.getDeptInfo(id);
    }

    @Override
    public void saveOrUpdate(Map<String,Object> dept) {
        String id = MapUtils.getString(dept,"id","");
        if(StringUtils.isBlank(id)){
            sysDeptDao.save(dept);
        }else{
            sysDeptDao.update(dept);
        }
    }

    @Override
    public void delete(int id) {
        String children = sysDeptDao.getChildren(id);
        List<String> list= Arrays.asList(children.split(","));
        List<String> arrList = new ArrayList<String>(list);
        arrList.remove("$");
        String[] strings = new String[arrList.size()];
        sysDeptDao.delete(arrList.toArray(strings));
        sysDeptDao.deleteUserByDeptId(arrList.toArray(strings));
    }

    @Override
    public Map<String,Object> getUserDept(Long user_id) {
        return sysDeptDao.getUserDept(user_id);
    }

    @Override
    public void saveUserDept(Long user_id, String dept_id,String isLeader) {
        sysDeptDao.saveUserDept(user_id,dept_id,isLeader);
    }

    @Override
    public void deleteUserDept(Long user_id, String dept_id) {
        sysDeptDao.deleteUserDept(user_id,dept_id);
    }

    @Override
    public PageUtils findUserByDept(Map<String,Object> param) {
        IPage iPage = new Query().getPage(param);
        String dept_id = MapUtils.getString(param,"dept_id");
        IPage<Map<String,Object>> users = sysDeptDao.findUserByDept(iPage,dept_id);
        return new PageUtils(users);
    }


}
