package com.pawn.glave.app.modules.app.utils;

import java.util.List;

/**
 * ListUtil
 **/
public class ListUtil {
    /**
     * 判断是否为空list
     *
     * @param list 需要判断的list
     * @return true: list为空或者大小为0， false: list不为空
     */
    public static boolean isNull(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断是否为非空list
     *
     * @param list 需要判断的list
     * @return true: 非空， false: 为空
     */
    public static boolean isNotNull(List<?> list) {
        return list != null && list.size() > 0;
    }
}
