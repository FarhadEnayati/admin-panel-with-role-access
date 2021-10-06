package com.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class User extends BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String userName;
    @Column
    private String loginName;
    @Column
    private String password;
    @Column
    private Short status;
    @Column
    private Date expiredDate;
    @Column
    private Date passwordExpiredDate;
    @Column
    private Integer creatorUserId;
    @Column
    private Date activeDate;
    @Column
    private Date lastLoginDate;
    @Column
    private String email;
    @Column
    private Long mobile;
    @Column
    private String editDesc = "";

}
