package com.identity.exception;

import com.identity.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";


    //Hàm xử lý lỗi chủ động
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMesage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // Lưới bảo hiểm cuối cùng: Hứng lỗi ép kiểu Enum sai nếu vô tình lọt xuống tầng trong
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handlingIllegalArgumentException(IllegalArgumentException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(400);
        apiResponse.setMessage("Permission or Role Invalid: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

//    exception lỗi password
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation =
                    exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMesage(), attributes)
                        : errorCode.getMesage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
    private String mapAttribute(String message, Map<String, Object> attributes) {
        // Duyệt qua từng Key-Value có trong túi thuộc tính của Annotation
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String key = entry.getKey();   // Ví dụ: "min", "max", "value", "regexp"
            String value = String.valueOf(entry.getValue()); // Ví dụ: 6, 20, 18

            // Cứ thấy chỗ nào trong câu message có dạng {key} thì thay bằng value
            message = message.replace("{" + key + "}", value);
        }
        return message;
    }
}
