package com.project.model.rest;

import com.project.model.BaseEntity;
import lombok.Data;

@Data
public class UserInfo extends BaseEntity<Integer> {
    private Integer id;
    private String password;
}
