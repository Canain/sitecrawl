package com.canain.sitecrawl.test;

import com.canain.sitecrawl.parser.Parser;
import com.canain.sitecrawl.parser.SiteType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 2015/03/23
 *
 * @author Shuyang Chen
 */
public class OfflineParserTest {

    private Parser parser;

    @Before
    public void setup() {

    }

    @Test
    public void testOfflineTsquareNav() throws IOException {
        parser = new Parser(Jsoup.parse(new File("testsites/tsquare/workspace.html"), "UTF-8"), SiteType.TSQUARE);

        Map<String, String> data = new LinkedHashMap<String, String>();
        data.put("CS-2110", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1");
        data.put("CS-2340-A,GR", "https://t-square.gatech.edu/portal/site/gtc-878c-f073-5abc-a837-fc1051446535");
        data.put("PHYS-2211-K,M,N", "https://t-square.gatech.edu/portal/site/gtc-3d62-f0de-52de-8aec-e11a87b512fe");
        data.put("PHYS-2211-N03", "https://t-square.gatech.edu/portal/site/gtc-71a5-b1d4-562b-a9b7-e8688ac1bb4a");
        data.put("CS-2050-A1,A2,B1,B2,B3,A3", "https://t-square.gatech.edu/portal/site/gtc-7fb4-a11e-5400-afa0-73ee2b759a03");

        assertEquals(data, parser.getData(SiteType.Tsquare.NAV));
    }

    @Test
    public void testOfflineTsquareSide() throws IOException {
        parser = new Parser(Jsoup.parse(new File("testsites/tsquare/class.html"), "UTF-8"), SiteType.TSQUARE);

        Map<String, String> data = new LinkedHashMap<String, String>();
        data.put("Home", "");
        data.put("Announcements", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/7b74d026-c476-40fd-af90-7ab84d16c2a6");
        data.put("Assignments", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/0e5e45b7-6500-4cb5-a92d-cc672256a093");
        data.put("Email Archive", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/6c44caae-7f04-4854-a7ad-6e29060d341b");
        data.put("Gradebook", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/ff8cf1c3-c96d-44fa-a50a-a5f4e9b7ee3c");
        data.put("Piazza", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/44a5fd92-4c42-4edd-875a-cd4bfde929e7");
        data.put("Resources", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/536efe7c-7917-49bd-ade5-837e6361064a");
        data.put("Roster", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/e3421255-66a4-4989-8b86-88d1198d5b01");
        data.put("Section Info", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/0392c8d5-85ab-4578-a208-816140f937eb");
        data.put("Sign-up", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/3bdee9de-c22b-4b43-b340-b5839a38b02b");
        data.put("Site Info", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/eaecb64a-97e0-4b39-bbb9-39b68dfa8849");
        data.put("Tests & Quizzes", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/ff9420a6-70d6-4745-9531-02c9e09fe2f3");
        data.put("Wiki", "https://t-square.gatech.edu/portal/site/gtc-8603-513f-59e2-86ed-64fd10a878e1/page/aba74900-dd51-49c1-afe7-bf1f5fac7b4f");
        data.put("Help", "https://t-square.gatech.edu/portal/help/main");

        assertEquals(data, parser.getData(SiteType.Tsquare.SIDE));
    }

    @Test
    public void testOfflineTsquareAssignmentsIframe() throws IOException {
        parser = new Parser(Jsoup.parse(new File("testsites/tsquare/assignments.html"), "UTF-8"), SiteType.TSQUARE);

        Map<String, String> data = new LinkedHashMap<String, String>();
        data.put("iframe", "https://t-square.gatech.edu/portal/tool/77bf4947-7b37-4ab9-8046-3df006928a95?panel=Main");

        assertEquals(data, parser.getData(SiteType.Tsquare.ASSIGNMENTS_IFRAME));
    }

    @Test
    public void testOfflineTsquareAssignments() throws IOException {
        parser = new Parser(Jsoup.parse(new File("testsites/tsquare/assignmentsiframe.html"), "UTF-8"), SiteType.TSQUARE);

        Map<String, String> data = new LinkedHashMap<String, String>();
        data.put("0:title", "Homework 08");
        data.put("0:status", "Not Started");
        data.put("0:openDate", "Mar 7, 2015 8:00 pm");
        data.put("0:dueDate", "Mar 13, 2015 12:00 pm");

        data.put("1:title", "Homework 07");
        data.put("1:status", "Not Started");
        data.put("1:openDate", "Feb 28, 2015 9:30 pm");
        data.put("1:dueDate", "Mar 6, 2015 12:05 pm");

        data.put("2:title", "Homework 06");
        data.put("2:status", "Not Started");
        data.put("2:openDate", "Feb 14, 2015 2:50 pm");
        data.put("2:dueDate", "Feb 20, 2015 12:05 pm");

        data.put("3:title", "Homework 05");
        data.put("3:status", "Not Started");
        data.put("3:openDate", "Feb 6, 2015 5:00 pm");
        data.put("3:dueDate", "Feb 13, 2015 12:05 pm");

        data.put("4:title", "Homework 04");
        data.put("4:status", "Not Started");
        data.put("4:openDate", "Feb 2, 2015 3:00 pm");
        data.put("4:dueDate", "Feb 6, 2015 5:00 pm");

        data.put("5:title", "Homework 03");
        data.put("5:status", "Not Started");
        data.put("5:openDate", "Jan 23, 2015 6:00 pm");
        data.put("5:dueDate", "Jan 30, 2015 12:05 pm");

        data.put("6:title", "Homework 02");
        data.put("6:status", "Not Started");
        data.put("6:openDate", "Jan 16, 2015 12:00 pm");
        data.put("6:dueDate", "Jan 23, 2015 12:00 pm");

        data.put("7:title", "Homework 01");
        data.put("7:status", "Not Started");
        data.put("7:openDate", "Jan 9, 2015 7:00 am");
        data.put("7:dueDate", "Jan 16, 2015 12:00 pm");

        assertEquals(data, parser.getData(SiteType.Tsquare.ASSIGNMENTS));
    }

    @Test
    public void testTsquareAuth() throws IOException {
        parser = new Parser(Jsoup.parse(new File("testsites/tsquare/auth.html"), "UTF-8"), SiteType.TSQUARE);

        Map<String, String> data = new LinkedHashMap<String, String>();

        data.put("lt", "LT-1198121-gBgHrHNEgl00yiXfeS9dr0wOx4NuCD");
        data.put("execution", "e2s1");

        assertEquals(data, parser.getData(SiteType.Tsquare.AUTH));
    }
}
