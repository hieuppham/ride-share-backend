package vn.rideshare.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.rideshare.client.dto.ResponseBody;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResponseBody> handleCommonException(CommonException e){
        return ResponseEntity.ok().body(new ResponseBody(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody> handleException(Exception e) {
        return ResponseEntity.ok().body(new ResponseBody(e));
    }
}
