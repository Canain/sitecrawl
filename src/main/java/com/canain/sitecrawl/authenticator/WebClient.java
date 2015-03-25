package com.canain.sitecrawl.authenticator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * 2015/03/24
 *
 * @author Shuyang Chen
 */
public class WebClient {

    private static final String TSQUARE_AUTH_URL = "https://login.gatech.edu/cas/login?service=https%3A%2F%2Ft-square.gatech.edu%2Fsakai-login-tool%2Fcontainer";

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
    private static Map<String, String> authTsquare(String user, String pass) {

        return null;
    }

}
