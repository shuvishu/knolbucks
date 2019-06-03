package com.knoldus.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    private int errorCode;

    private String errorMessage;

    private String time;

    private String id;
}
