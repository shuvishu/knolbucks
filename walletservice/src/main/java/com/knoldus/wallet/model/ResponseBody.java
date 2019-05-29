package com.knoldus.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBody<T> {

    private int status;

    private String errors;

    private T data;
}
