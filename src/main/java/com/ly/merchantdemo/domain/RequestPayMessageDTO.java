package com.ly.merchantdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RequestPayMessageDTO implements Serializable {
    private static final long serialVersionUID = 4802859787150098739L;
    private String mchId;
    private String tradeUrl;
}
