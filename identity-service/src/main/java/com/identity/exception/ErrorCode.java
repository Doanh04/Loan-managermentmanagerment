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
    PERMISSION_NOT_BLANK(1003, "Permission Not Blank", HttpStatus.BAD_REQUEST),
    PERMISSION_IS_EXITED(1004,"Permission is exited", HttpStatus.CONFLICT),
    ROLE_INVALID(1005, "Role Invalid", HttpStatus.NOT_FOUND),
    ROLE_NAME_INVALID(1006, "Role Name Invalid", HttpStatus.NOT_FOUND),
    ROLE_NOT_BLANK(1007, "Role is not blank", HttpStatus.BAD_REQUEST),
    ROLE_IS_EXITED(1008, "Role is exited",HttpStatus.CONFLICT)
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
