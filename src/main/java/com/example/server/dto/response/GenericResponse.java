package com.example.server.dto.response;

import lombok.Data;

@Data
public class GenericResponse {
    private Integer status=404;
    private String message="Error";

    public GenericResponse() {
    }

    public GenericResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
