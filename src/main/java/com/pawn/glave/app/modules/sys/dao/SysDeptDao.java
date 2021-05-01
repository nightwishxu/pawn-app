package com.pawn.glave.app.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pawn.glave.app.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    List<SysDeptEntity> deptList();

    @Select("select t2.* from sys_dept t1 left join sys_dept t2 on t1.parent_id=t2.id where t1.id= #{id}")
    Map<String,Object> getParentInfo(@Param("id") String id);

    @Select("select t1.*,t2.name pname from sys_dept t1 left join sys_dept t2 on t1.parent_id=t2.id where t1.id= #{id}")
    Map<String,Object> getDeptInfo(@Param("id") String id);

    void save(Map<String, Object> dept);

    void update(Map<String, Object> dept);

    String getChildren(@Param("id") int id);

    int delete(@Param("ids") String[] ids);

    Map<String,Object> getUserDept(@Param("user_id") Long user_id);

    void saveUserDept(@Param("user_id") Long user_id, @Param("dept_id") String dept_id, @Param("isLeader") String isLeader);

    void deleteUserDept(@Param("user_id") Long user_id, @Param("dept_id") String dept_id);

    void deleteUserByDeptId(@Param("ids") String[] ids);

    IPage<Map<String,Object>> findUserByDept(@Param("iPage") IPage iPage, @Param("dept_id") String dept_id);
}
