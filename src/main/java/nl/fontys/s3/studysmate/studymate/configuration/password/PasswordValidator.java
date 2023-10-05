package nl.fontys.s3.studysmate.studymate.configuration.password;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static boolean isValid(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }
}
