package com.steven.stevenadmin.common.exceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomExceptionHandler extends RuntimeException {
    private Integer code;
    private String message;
}
