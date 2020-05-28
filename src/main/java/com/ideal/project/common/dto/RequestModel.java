package com.ideal.project.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RequestModel implements Serializable {
    private String cipher;
    private Long timeStamp;
}
