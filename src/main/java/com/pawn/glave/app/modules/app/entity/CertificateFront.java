package com.pawn.glave.app.modules.app.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CertificateFront implements Serializable {
    private static final long serialVersionUID = -1184496822948568435L;
    private Long id;

    private String code;
}
