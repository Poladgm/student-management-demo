package com.management.student.app.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@ApiModel(description = "Response DTO projection")
public class AuthToken {
    @ApiModelProperty(name = "token", notes = "If user is authorized ,the generated token")
    private final String token;

    public AuthToken(String token) {
        this.token = token;
    }
}
