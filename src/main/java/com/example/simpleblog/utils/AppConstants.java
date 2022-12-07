package com.example.simpleblog.utils;

public class AppConstants {
    public static final String DEFAULT_PAGE_NO = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIR = "desc";

    public static final String ROLE_USER = "ROLE_USER";

    public static final String TOKEN_TYPE = "Bearer";

    public  static final String BAD_CREDENTIALS_ERROR_MESSAGE = "Invalid email, username or password.";
    public  static final String UNAUTHORISED_UPDATE_MESSAGE = "Unauthorised to update resource.";
    public  static final String UNAUTHORISED_DELETE_MESSAGE = "Unauthorised to delete resource.";

    public static String formatUnauthorisedMessage(String method) {
        return "Unauthorised to " + method + " resource.";
    }
}
