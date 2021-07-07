package com.example.server.dto.response;

import lombok.Data;

@Data
public class GenericResponseWithBody {
    private Integer status=404;
    private String message="Error";
    private Object data;
}
