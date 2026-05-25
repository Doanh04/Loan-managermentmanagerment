package com.identity.exception;

import com.identity.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //Hàm xử lý lỗi chủ động
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMesage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    // Hứng lỗi khi @Valid tìm ra trường sai (Chặn ở tầng Controller)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getBindingResult().getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.PERMISSION_INVALID; // Mặc định
        try {
            errorCode = ErrorCode.valueOf(enumKey); // Tra ngược chuỗi "PERMISSION_INVALID" thành Enum ErrorCode
        } catch (IllegalArgumentException e) {
            // Giữ nguyên mặc định nếu gõ sai Key message
        }

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
        apiResponse.setMessage("Dữ liệu truyền vào không khớp với cấu hình hệ thống: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
