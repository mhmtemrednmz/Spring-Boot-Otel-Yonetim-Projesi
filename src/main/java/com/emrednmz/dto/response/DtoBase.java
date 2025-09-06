package com.emrednmz.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class DtoBase {
    private Long id;
    private Date createTime;
    private Date updateTime;
}
