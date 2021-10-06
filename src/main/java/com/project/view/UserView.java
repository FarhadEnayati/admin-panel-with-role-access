package com.project.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.utility.converter.CustomDateDeserializer;
import com.project.utility.converter.CustomDateSerializer;
import com.project.utility.validator.Username;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserView extends BaseEntityView<Integer> {
    @NotEmpty
    @Length(max = 50)
    private String userName;
    @NotEmpty
    @Length(min = 5, max = 16)
    @Username
    private String loginName;
    @NotNull
    private Short status;
    private Long mobile;
    @Length(max = 45)
    @Email
    private String email;
    @NotNull
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date expiredDate;

}
