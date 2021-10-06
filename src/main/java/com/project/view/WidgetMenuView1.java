package com.project.view;

import lombok.Data;

import java.util.List;

@Data
public class WidgetMenuView1 extends BaseEntityView<Integer> {
    private String path;
    private String name;
    private Integer parent;
    private Integer order;
    private String icon;
    private String type;
    private Short status;
    public List<WidgetMenuView1> children;
}
