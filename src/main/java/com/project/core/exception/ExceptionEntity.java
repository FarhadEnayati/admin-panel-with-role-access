package com.project.core.exception;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionEntity {

    private String customMsg;
    private Byte type;
    private Integer code;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
