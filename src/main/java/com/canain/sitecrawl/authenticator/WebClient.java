package com.canain.sitecrawl.authenticator;

import com.canain.sitecrawl.parser.Parser;
import com.canain.sitecrawl.parser.SiteType;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 2015/03/24
 *
 * @author Shuyang Chen
 */
public class WebClient {

    private static final String TSQUARE_AUTH_URL = "https://login.gatech.edu/cas/login?service=https%3A%2F%2Ft-square.gatech.edu%2Fsakai-login-tool%2Fcontainer";

    private Map<String, String> cookies;

    public WebClient() {
        cookies = new LinkedHashMap<String, String>();
    }

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
    private static Map<String, String> authTsquareCookies(String user, String pass) throws IOException {

        Connection.Response response = Jsoup.connect(TSQUARE_AUTH_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .data("username", user)
                .data("password", pass)
                .data(Parser.parse(TSQUARE_AUTH_URL).getData(SiteType.Tsquare.AUTH))
                .data("_eventId", "submit")
                .data("submit", "LOGIN")
                .method(Connection.Method.POST)
                .execute();

        System.out.println(response.parse().toString());

        return response.cookies();
    }

    public void authTsquare(String user, String pass) throws IOException {
        cookies.putAll(authTsquareCookies(user, pass));
    }

}
