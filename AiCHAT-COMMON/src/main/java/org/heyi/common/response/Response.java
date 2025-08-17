package org.heyi.common.response;

import lombok.Data;
import org.heyi.common.enumeration.ResponseEnumeration;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    private Integer code;
    private T data;
    private String message;


    private Response(ResponseEnumeration  responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }


    public static<T> Response<T> builder(ResponseEnumeration responseEnum) {
        Response<T> tResponse = new Response<>(responseEnum);
        return tResponse;
    }

    public static<T> Response<T> builder(ResponseEnumeration responseEnum,T data) {
        Response<T> tResponse = new Response<>(responseEnum);
        tResponse.setData(data);
        return tResponse;
    }
}
