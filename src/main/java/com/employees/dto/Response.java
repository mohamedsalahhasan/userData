package com.employees.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include. NON_NULL)
public class Response<T> {


    private String errorMessage;
    private Integer currentPage;
    private Integer pageSize;
    private Integer numberOfPages;
    private long exTime;
    private T result;
}
