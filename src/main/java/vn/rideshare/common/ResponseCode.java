package vn.rideshare.common;

public enum ResponseCode {
    SUCCESS("0000", "Thành công"),
    NOT_FOUND("0008", "Không tìm thấy bản ghi"),
    ONE_RIDE_ACTIVE("8888", "Đã có hành trình đang hoạt động"),
    INTERNAL_SYSTEM_ERROR("9999", "Lỗi hệ thống");
    private String code;
    private String message;

    private ResponseCode(String code, String message){
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
