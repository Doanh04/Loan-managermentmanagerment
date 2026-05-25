package com.identity.customAntotaion;

import com.identity.validation.PermissionValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Hoạt động trên fiel của DTO
@Retention(RetentionPolicy.RUNTIME) // Hoạt động trong khi đang chạy
@Constraint(validatedBy = PermissionValidation.class) // chỉ định class xử lý logic
public @interface ValidateEnumPermission {
    Class<? extends Enum<?>> enumCLASS(); // thuộc tính chỉ định enum muốn so sánh chỉ nhận class ENUM
    String message() default "Permission Invalid"; //Message lỗi mặc định

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
