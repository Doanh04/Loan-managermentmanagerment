package com.identity.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
//    Lỗi cấu hình
    PERMISSION_INVALID(1001, "Permission Invalid", HttpStatus.NOT_FOUND),
    PERMISION_NAME_INVALID(1002, "Name Perission Invalid", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_BLANK(1003, "Permission Not Blank", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String mesage, HttpStatusCode statusCode) {
        this.code = code;
        this.mesage = mesage;
        this.statusCode = statusCode;
    }

    int code;
    String mesage;
    HttpStatusCode statusCode;
}
