package com.atuluttam.user.model;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Address {
    private Long id;
    private String street;
    private String city;
    private String country;
    private String pincode;
}
