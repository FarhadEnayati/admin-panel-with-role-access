package com.project.view;

import com.project.utility.validator.URL;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class WidgetMenuView2 extends BaseEntityView<Integer> {
    @NotEmpty
    @Length(max = 100)
    private String name;
    private Integer parent;
    private String parentName;
    @NotNull
    private Integer order;
    @Length(max = 100)
    @URL
    private String path;
    @Length(max = 50)
    private String icon;
    @NotEmpty
    private String type;
    @NotNull
    private Short status;
}
