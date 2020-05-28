package com.ideal.project.common.dto;

import com.ideal.project.common.entity.BaseCode;
import com.ideal.project.common.enumerate.ResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseModel<T> implements Serializable {
    private String code;
    private String message;
    private T data;
    private boolean success;

    public ResponseModel(String code ,boolean success, String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }
    public ResponseModel(String code ,boolean success, String message){
        this.code = code;
        this.message = message;
        this.success = success;
    }

    /**
     * 表示服务器成功处理了请求
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> success(T data){
        return new ResponseModel<T>(ResponseCode.SUCCESS.getCode(),
                true, ResponseCode.SUCCESS.getMessage(),data);
    }

    public static <T> ResponseModel<T> success(){
        return new ResponseModel<T>(ResponseCode.SUCCESS.getCode(),
                true, ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 响应错误
     * @param responseCode
     * @return
     */
    public static ResponseModel error(BaseCode responseCode){
        return new ResponseModel(responseCode.getCode(),false,responseCode.getMessage(),null);
    }

    /**
     * 响应错误，自定义message
     * @param responseCode
     * @return
     */
    public static ResponseModel error(BaseCode responseCode, String message){
        return new ResponseModel(responseCode.getCode(),false,message,null);
    }

}
