package com.example.ITSSJP1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response <T>{
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorDes;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    public Response( int status, T data){
        this.status = status;
        this.data = data;
    }
    public Response( int status, String errorDes){
        this.status = status;
        this.errorDes = errorDes;
    }

}
