package vn.rideshare.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.rideshare.common.ResponseCode;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBody {
    private String code;
    private String message;
    private Object data;

    public ResponseBody(ResponseCode code, Object data){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public ResponseBody(ResponseCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
