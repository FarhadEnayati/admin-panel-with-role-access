package com.project.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Role extends BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String roleTitle;
    @Column
    private Short status;
    @Column
    private Integer parentId = 0;
    @Column
    private Short roleType;
    @Column
    private Integer code = 0;
}
