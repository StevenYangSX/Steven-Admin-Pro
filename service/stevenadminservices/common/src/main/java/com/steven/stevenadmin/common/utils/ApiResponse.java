package com.steven.stevenadmin.common.utils;


import lombok.Data;


@Data
public class ApiResponse<T> {

//    @ApiModelProperty(value = "是否成功")
    private Boolean success;
//    @ApiModelProperty(value = "返回码")
    private Integer code;
//    @ApiModelProperty(value = "返回消息")
    private String message;
//    @ApiModelProperty(value = "返回数据")
    private T data ;

    //私有化constructor
    private  ApiResponse(){}

    //    public static <E> E[] appendToArray(E[] array, E item)
    //成功静态方法
    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setCode(ResponseResultCode.SUCCESS);
        apiResponse.setMessage("请求成功");
        return apiResponse;
    }
    //失败静态方法
    public static  <T> ApiResponse<T> error() {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setCode(ResponseResultCode.ERROR);
        apiResponse.setMessage("请求失败");
        return apiResponse;
    }

    //下面的方法这样定义，可以实现链式编程： apiResponse.ok().success().code().data()
    public ApiResponse<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public ApiResponse<T> message(String message) {
        this.setMessage(message);
        return this;
    }
    public ApiResponse<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ApiResponse<T> data(T data) {
        this.setData(data);
        return this;
    }

}
