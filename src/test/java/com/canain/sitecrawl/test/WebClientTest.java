package com.canain.sitecrawl.test;

import com.canain.sitecrawl.authenticator.WebClient;
import com.canain.sitecrawl.parser.SiteType;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 2015/03/25
 *
 * @author Shuyang Chen
 */
public class WebClientTest {

    private static WebClient client;

    @BeforeClass
    public static void setup() throws IOException {
        client = new WebClient();
        String[] authData = Files.toString(new File("testsites/tsquare/auth.secure"), Charsets.UTF_8).split("\n");
        client.authTsquare(authData[0], authData[1]);
    }

    @Ignore
    @Test
    public void testTsquareNav() throws IOException {
        Map<String, String> data = client.getParser("https://t-square.gatech.edu/portal", SiteType.TSQUARE).getData(SiteType.Tsquare.NAV);
        System.out.println(data);
    }

    @Ignore
    @Test
     public void testTsquareSingleSide() throws IOException {
        Map<String, String> data = client.getParser("https://t-square.gatech.edu/portal", SiteType.TSQUARE).getData(SiteType.Tsquare.NAV);
        data = client.getParser((String)data.values().toArray()[1], SiteType.TSQUARE).getData(SiteType.Tsquare.SIDE); //gets first class and not My Workspace
        System.out.println(data);
    }

    @Ignore
    @Test
    public void testTsquareSingleAssignmentsIframe() throws IOException {
        Map<String, String> data = client.getParser("https://t-square.gatech.edu/portal", SiteType.TSQUARE).getData(SiteType.Tsquare.NAV);
        data = client.getParser((String)data.values().toArray()[1], SiteType.TSQUARE).getData(SiteType.Tsquare.SIDE); //gets first class and not My Workspace
        data = client.getParser(data.get("Assignments"), SiteType.TSQUARE).getData(SiteType.Tsquare.ASSIGNMENTS_IFRAME);
        System.out.println(data);
    }

    @Test
    public void testTsquareSingleAssignments() throws IOException {
        Map<String, String> data = client.getParser("https://t-square.gatech.edu/portal", SiteType.TSQUARE).getData(SiteType.Tsquare.NAV);
        data = client.getParser((String)data.values().toArray()[1], SiteType.TSQUARE).getData(SiteType.Tsquare.SIDE); //gets first class and not My Workspace
        data = client.getParser(data.get("Assignments"), SiteType.TSQUARE).getData(SiteType.Tsquare.ASSIGNMENTS_IFRAME);
        data = client.getParser(data.get("iframe"), SiteType.TSQUARE).getData(SiteType.Tsquare.ASSIGNMENTS);
        System.out.println(data);
    }

}
