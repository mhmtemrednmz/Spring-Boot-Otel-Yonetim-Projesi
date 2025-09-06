package com.emrednmz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DtoUser extends DtoBase{
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<String> roles = new ArrayList<>();
}
