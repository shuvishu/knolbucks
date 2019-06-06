package com.knoldus.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Error {

    int errorCode;

    String errorMessage;

    String time;

    String id;
}
