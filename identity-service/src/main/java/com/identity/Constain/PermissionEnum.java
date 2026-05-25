package com.identity.Constain;

public enum PermissionEnum {
    CUSTOMER_CREATE, // v
    CUSTOMER_WRITE,
    CUSTOMER_READ,
// TẠo hồ sơ vay vốn, nhập số tiền, kì hạn, tài sản thế chấp
    LOAN_CREATE,
    LOAN_WRITE,
    LOAN_SUBMIT,//    Gửi hồ sơ lên cấp trên để chờ duyệt
    CREDIT_CHECK, // chạy luồng chấm điểm tín dụng
    LOAN_READ_ALL, //xem toàn bộ danh sách hồ sơ vay
    LOAN_APPROVE, // phê duyệt khoản vay
    LOAN_REJECT, // từ chối hoặc yêu cầu nhân viên tín dụng bổ sung thêm hồ sơ
    REPORT_VIEW,// xem báo cáo doanh số cho vay, tỷ lệ nợ xấu
    DISBURSEMENT_EXECUTE, // Thực hiện giải ngân
    REPAYMENT_COLLECT,// Nhập liệu và xử ý thu nợ khi khách hàng đến thanh toán định kỳ
    FINANCIAL_REPORT, //xuất báo cáo dòng tiền, sổ nhật ký chung, báo cáo và thu hồi nợ
    USER_CREATE,
    USER_WRITE,
    USER_READ,
    SYSTEM_CONFIG,
    AUDIT_LOG_VIEW
}
