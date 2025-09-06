package com.emrednmz.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exception<E>  {
    private String path;
    private Date timestamp;
    private String hostname;
    private E message;
}
