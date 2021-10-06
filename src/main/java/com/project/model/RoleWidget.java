package com.project.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class RoleWidget extends BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @ManyToOne
    private WidgetMenu widgetMenu;
    @ManyToOne
    private Role role;
    @Column
    private Short status;
    @Column
    private Integer creatorUserId;
}
