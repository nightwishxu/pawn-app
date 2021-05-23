package com.pawn.glave.app.modules.certificate.entity;

import lombok.Data;

/**
 * @author WBC
 * @date 2021/5/23 19:43
 * @title ZipEntity
 */

@Data
public class ZipEntity {

    String name;
    String url;

    public ZipEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
