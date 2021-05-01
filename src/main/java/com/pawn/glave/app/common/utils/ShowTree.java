package com.pawn.glave.app.common.utils;


import com.pawn.glave.app.modules.sys.entity.SysDeptEntity;

import java.util.ArrayList;
import java.util.List;

public class ShowTree {
	public static SysDeptEntity getDeptTree(SysDeptEntity mapTree, List<SysDeptEntity> allData){
		List<SysDeptEntity> listTree = new ArrayList<>();
		for(SysDeptEntity childTree : allData){
			if(childTree.getParentId().equals(mapTree.getId())){
				SysDeptEntity t = getDeptTree(childTree, allData);

				listTree.add(t);
				mapTree.setChildren(listTree);
			}
		}
		return mapTree;
	}
}
