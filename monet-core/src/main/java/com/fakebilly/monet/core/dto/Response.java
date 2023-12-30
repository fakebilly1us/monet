package com.fakebilly.monet.core.dto;

import com.fakebilly.monet.core.emuns.CommonCode;

import java.io.Serializable;

/**
 * Response
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String code;

    private String message;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public Response<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Response<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Response() {
        this.success = false;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static <T> Response<T> create() {
        return new Response<T>();
    }

    public static <T> Response<T> success() {
        return new Response<T>().setSuccess(true);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> success(String code) {
        return (Response<T>) success().setCode(code);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> success(String code, String message) {
        return (Response<T>) success(code).setMessage(message);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> success(String code, String message, T data) {
        return (Response<T>) success(code, message).setData(data);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> success(T data) {
        return (Response<T>) success().setData(data).setCode(CommonCode.SUCCESS.getCode()).setMessage(CommonCode.SUCCESS.getMessage());
    }

    public static <T> Response<T> fail() {
        return new Response<T>().setSuccess(false);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> fail(String code) {
        return (Response<T>) fail().setCode(code);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> fail(String code, String message) {
        return (Response<T>) fail(code).setMessage(message);
    }


}
