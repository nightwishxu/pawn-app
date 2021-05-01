package com.pawn.glave.app.modules.app;

public class AgainConstant {
    public enum AgainEnum {
        WAIT_IDENTIFIED("0","待鉴定"),
        DIST_EXPERT("1","已分配专家"),
        SUCCESS_APPRAISAL("2","鉴定完成"),
        UN_APPRAISAL("3","无法鉴定"),
        AGAIN_APPRAISAL("4","再次提交鉴定申请"),
        FINISH_AGAIN_APPRAISAL("5","专家完成再次鉴定审核");

        private String value;
        private String desc;

        AgainEnum(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

    }
}
