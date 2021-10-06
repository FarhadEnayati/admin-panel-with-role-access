package com.project.view;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleView extends BaseEntityView<Integer> {
    @NotBlank
    @Length(max = 50)
    private String name;

    @NotNull
    private Short status;
}
