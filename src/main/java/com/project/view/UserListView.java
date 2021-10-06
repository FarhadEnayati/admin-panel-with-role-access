package com.project.view;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.utility.converter.CustomDateSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class UserListView extends BaseEntityView<Integer> {
    private String userName;
    private String loginName;
    private Short status;
    private Long mobile;
    private String email;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date expiredDate;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date passwordExpiredDate;
}
