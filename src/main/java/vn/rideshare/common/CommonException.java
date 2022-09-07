package vn.rideshare.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException {
    private String code;
    private String message;

    public CommonException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(ErrorCode e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public CommonException(Exception e){
        this.code = ErrorCode.INTERNAL_SYSTEM_ERROR.getCode();
        this.message = e.getMessage();
    }
}
