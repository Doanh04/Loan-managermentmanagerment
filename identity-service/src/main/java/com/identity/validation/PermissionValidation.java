package com.identity.validation;

import com.identity.customAntotaion.ValidateEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PermissionValidation implements ConstraintValidator<ValidateEnum, String> {
    private List<String> accepteptedVlues;
    @Override
    public void initialize(ValidateEnum constraintAnnotation) { //lấy dữ liệu từ antotation có enum
        accepteptedVlues = Stream.of(constraintAnnotation.enumCLASS().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //Client không gửi trả về @NptNull hoặc @NotBlank sử lý
        if(value == null){
            return true;
        }
        // kiểm tra khờ trùng với Enum
        return accepteptedVlues.contains(value);
    }
}
