package com.pawn.glave.app.common.utils;

/**
 * 常量
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    public static final String USER_KEY = "userId";

    /**
     * 菜单类型
     **/
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum AppraisalCategoryEnum {
        COLORED_GEMSTONE(1, "彩色宝石"),
        LUXURY_JEWELRY(2, "奢侈品珠宝"),
        WRIST_WATCH(3, "手表"),
        DIAMONDS(4, "钻石"),
        NOBLE_METAL(5, "贵金属"),
        JADEITE(6, "翡翠玉石"),
        PORCELAIN(7, "瓷器"),
        NEPHRITE(8, "和田玉"),
        PAINTING(9, "绘画"),
        CALLIGRAPHY(10, "书法"),
        MISCELLANEOUS(11, "文玩杂项"),
        OTHER(12, "其它");

        private long id;

        private String desc;

        AppraisalCategoryEnum(long id, String desc) {
            this.id = id;
            this.desc = desc;
        }

        public long getId() {
            return id;
        }
    }
}
