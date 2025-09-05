package com.atuluttam.user.dto;

import com.atuluttam.user.model.Address;
import lombok.Data;

@Data
public class UserRequest  {
    private String id;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private Address address;
}
