package com.emrednmz.dto.request;

import com.emrednmz.enums.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private List<ERole> roles = new ArrayList<>();
}
