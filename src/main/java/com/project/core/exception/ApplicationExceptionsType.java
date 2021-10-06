package com.project.core.exception;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public enum ApplicationExceptionsType {

    /**
     * خطا
     */
    ERROR((byte) 1),

    /**
     * اطلاع
     */
    INFO((byte) 2),

    /**
     * هشدار
     */
    WARNING((byte) 3),

    /**
     * موفقیت
     */
    SUCCESS((byte) 4),

    /**
     * قفل کاربر
     */
    LOCK((byte) 5);

    public static ApplicationExceptionsType getApplicationExceptionsTypes(Byte id) {
        for (ApplicationExceptionsType obj : ApplicationExceptionsType.values()) {
            if (obj.getValue().equals(id)) {
                return obj;
            }
        }
        return null;
    }

    private byte value;

    private ApplicationExceptionsType(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

}
