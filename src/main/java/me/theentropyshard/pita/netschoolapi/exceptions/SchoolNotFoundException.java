package me.theentropyshard.pita.netschoolapi.exceptions;

public class SchoolNotFoundException extends Exception{
    public SchoolNotFoundException() {
        super();
    }

    public SchoolNotFoundException(String message) {
        super(message);
    }

    public SchoolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SchoolNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
