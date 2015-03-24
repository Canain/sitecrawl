package com.canain.sitecrawl.authenticator;

import java.util.Map;

/**
 * 2015/03/24
 *
 * @author Shuyang Chen
 */
public class Authenticator {

    public static Map<String, String> authTsquare(String user, String pass) {
        /**
         * https://login.gatech.edu/cas/login?service=https%3A%2F%2Ft-square.gatech.edu%2Fsakai-login-tool%2Fcontainer
         * x-www-form-urlencoded
         * username
         * password
         * lt - need to retrieve
         * execution - need to retrieve
         * _eventId - submit
         * submit - LOGIN
         */
        return null;
    }

}
