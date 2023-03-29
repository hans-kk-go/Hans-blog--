package com.hans.commen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    public static ResponseResult ok(){
        return new ResponseResult(true, 200, null, null);
    }
    public static ResponseResult ok(Object data){
        return new ResponseResult(true, 200, null, data);
    }
    public static ResponseResult ok(String message){
        return new ResponseResult(true, 200, message, null);
    }
    public static ResponseResult ok(String message,Object data){
        return new ResponseResult(true, 200, message, data);
    }
    public static ResponseResult fail(Integer code,String message){
        return new ResponseResult(false, code, message, null);
    }

}

