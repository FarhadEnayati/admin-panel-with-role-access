package com.project.view;

import lombok.Data;

@Data
public class LoggingUserView extends BaseEntityView<Integer> {
    private String userName;
    private String loginName;
    private Short status;
    private String email;
    private Long mobile;
    private String role;
}
