package com.task.tracker.common;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

    @Value("${timeapp-admin-group}")
    private static String timeAppAdminGroup;

    public static final long JWT_EXPIRATION = 86400000;
    //86400000 - set it to 1 days by default to expire jwt token
    //604800000 - set it to 7 days to expire jwt token
    //60000 - set it to 1 min for testing jwt expiration, unauthorized access

    public static final String TIMEAPP_ADMIN_GROUP = timeAppAdminGroup;
    public static final String TIMEAPP_ADMIN_ROLE = "ROLE_ADMIN";
    public static final String TIMEAPP_USER_ROLE = "ROLE_USER";

    public static final String JWT_TOKEN_EXPIRED_ERROR_MSG = "JWT token has expired";

}
