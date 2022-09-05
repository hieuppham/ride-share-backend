package vn.rideshare.common;

public enum ErrorCode {
    NOT_FOUND("0008", "Không tìm thấy bản ghi"),
    INTERNAL_SYSTEM_ERROR("9999", "Lỗi hệ thống");
    private String code;
    private String message;

    private ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
