package com.project.core.encryption;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String hashed = null;
        try {
            hashed = UserManagmentEncrypter.encrypt(rawPassword.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashed = "";
        try {
            hashed = UserManagmentEncrypter.encrypt(rawPassword.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashed.equals(encodedPassword);
    }
}
