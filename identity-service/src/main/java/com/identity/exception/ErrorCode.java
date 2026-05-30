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
    UKNOWN_ERROR(9999, "uknown error", HttpStatus.BAD_REQUEST),
    PERMISSION_INVALID(1001, "Permission Invalid", HttpStatus.NOT_FOUND),
    PERMISION_NAME_INVALID(1002, "Name Perission Invalid", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_BLANK(1003, "Permission Not Blank", HttpStatus.BAD_REQUEST),
    PERMISSION_IS_EXITED(1004,"Permission is exited", HttpStatus.CONFLICT),
    ROLE_INVALID(1005, "Role Invalid", HttpStatus.NOT_FOUND),
    ROLE_NAME_INVALID(1006, "Role Name Invalid", HttpStatus.NOT_FOUND),
    ROLE_NOT_BLANK(1007, "Role is not blank", HttpStatus.BAD_REQUEST),
    ROLE_IS_EXITED(1008, "Role is exited",HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(1009,"role not found", HttpStatus.CONFLICT),
    PERMISSION_NOT_FOUND(1010,"Permission not found", HttpStatus.CONFLICT),
    STATUS_NOT_FOUND(1011, "Status not found", HttpStatus.CONFLICT),
    INVALID_KEY(1012, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1013, "Invalid password", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1014, "invalid email", HttpStatus.BAD_REQUEST),
    EMAIL_IS_REQUIRED(1015, "email is required", HttpStatus.BAD_REQUEST),
    USERNAME_IS_EXITED(1016, "User name is exited", HttpStatus.BAD_REQUEST),
    EMAIL_VERIFIED_EXITED(1017, "Email is exited", HttpStatus.BAD_REQUEST)
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
