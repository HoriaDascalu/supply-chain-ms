package com.supply.chain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailsDto {

    private String message;
    private String errorCode;
    private Integer statusValue;

}
