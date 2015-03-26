package com.canain.sitecrawl.test;

import com.canain.sitecrawl.TsquareCrawler;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 2015/03/26
 *
 * @author Shuyang Chen
 */
public class TsquareCrawlerTest {

    @Test
    public void testAllFutureAssignment() throws IOException {
        String[] authData = Files.toString(new File("testsites/tsquare/auth.secure"), Charsets.UTF_8).split("\n");
        TsquareCrawler crawler = new TsquareCrawler(authData[0], authData[1]);
        crawler.auth();

        long startTime = System.currentTimeMillis();
        String out = crawler.getAllFutureAssignments();
        System.out.println("Took " + (System.currentTimeMillis() - startTime) + " ms to get all future assignments");
        System.out.println(out);
    }
}
