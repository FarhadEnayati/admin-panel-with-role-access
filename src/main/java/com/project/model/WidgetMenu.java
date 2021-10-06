package com.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class WidgetMenu extends BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Short status;
    @Column
    private Short menuType;
    @Column
    private Short menuOrder;
    @Column
    private Integer parentId;
    @Column
    private String widgetIcon;
    @Column
    private String widgetPath;
    @Column
    private String menuNameFa;
    @Column
    private Integer creatorUserId;
    @Column
    private String parentMenuNameFa;

    @Transient
    public List<WidgetMenu> children = new ArrayList<>();

    public void clearChild() {
        children = new ArrayList<>();
    }

    public void addChild(WidgetMenu menu) {
        this.children.add(menu);
    }
}
