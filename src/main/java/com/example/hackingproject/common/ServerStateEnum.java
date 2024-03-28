package com.example.hackingproject.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServerStateEnum {
    SUCCESS("0000",null),
    NOTICE_DELETE_DUPLICATE("0100","이미 삭제된 글입니다."),
    FILE_DELETE_DUPLICATE("0101","이미 삭제된 파일입니다."),
    FAIL("500",null)
    ;

    private final String code;
    private final String desc;

    ServerStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }
    public String desc() {
        return desc;
    }
}
