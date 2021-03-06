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
    public Document authTsquare(String user, String pass) throws IOException {
        Connection.Response response = Jsoup.connect(TSQUARE_AUTH_URL).execute();
        cookies.putAll(response.cookies());

        response = Jsoup.connect(TSQUARE_AUTH_URL)
                .cookies(cookies)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .data("username", user)
                .data("password", pass)
                .data(new Parser(response.parse(), SiteType.TSQUARE).getData(SiteType.Tsquare.AUTH))
                .data("_eventId", "submit")
                .data("submit", "LOGIN")
                .method(Connection.Method.POST)
                .execute();

        cookies.putAll(response.cookies());

        return response.parse();
    }

    public Document getDocument(String url, int timeout) throws IOException {
        return Jsoup.connect(url).cookies(cookies).timeout(timeout).execute().parse();
    }

    public Document getDocument(String url) throws IOException {
        return getDocument(url, 3000);
    }

    public Parser getParser(String url, int siteType) throws IOException {
        return new Parser(getDocument(url), siteType);
    }

    public Parser getParser(String url, int siteType, int timeout) throws IOException {
        return new Parser(getDocument(url, timeout), siteType);
    }

}
