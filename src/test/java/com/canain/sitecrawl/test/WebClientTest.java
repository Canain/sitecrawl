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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void testTsquareSingleAssignments() throws IOException, ParseException {
        long startTime = System.currentTimeMillis();

        Map<String, String> data = client.getParser("https://t-square.gatech.edu/portal", SiteType.TSQUARE).getData(SiteType.Tsquare.NAV);
        data = client.getParser((String)data.values().toArray()[1], SiteType.TSQUARE).getData(SiteType.Tsquare.SIDE); //gets first class and not My Workspace
        data = client.getParser(data.get("Assignments"), SiteType.TSQUARE).getData(SiteType.Tsquare.ASSIGNMENTS_IFRAME);
        data = client.getParser(data.get("iframe"), SiteType.TSQUARE, 10000).getData(SiteType.Tsquare.ASSIGNMENTS);

        System.out.println("Took " + (System.currentTimeMillis() - startTime) + " ms to retrieve assignments from Tsquare");

        int maxValue = -1;
        int value;
        for (String key : data.keySet()) {
            value = Integer.parseInt(key.split(":")[0]);
            if (value > maxValue) {
                maxValue = value;
            }
        }

        System.out.println("Title|Status|Open Date|Due Date");
        System.out.println(":--|:--|:--|:--");

        // Feb 9, 2015 3:00 pm
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy h:mm a");
        String dueDate;
        long currentTime = (new Date()).getTime();
        for (int i = 0; i <= maxValue; i++) {
            dueDate = data.get(i + ":dueDate");
            if (sdf.parse(dueDate).getTime() > currentTime) {
                System.out.print(data.get(i + ":title") + "|");
                System.out.print(data.get(i + ":status") + "|");
                System.out.print(data.get(i + ":openDate") + "|");
                System.out.println(dueDate);
            }
        }

//        System.out.println(data);
    }

}
