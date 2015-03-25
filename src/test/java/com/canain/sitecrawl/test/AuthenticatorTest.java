package com.canain.sitecrawl.test;

import com.canain.sitecrawl.authenticator.WebClient;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * 2015/03/25
 *
 * @author Shuyang Chen
 */
public class AuthenticatorTest {

    private WebClient client;

    @Before
    public void setup() {
        client = new WebClient();
    }

    @Test
    public void testAuthTsquare() throws IOException {
        String[] authData = Files.toString(new File("testsites/tsquare/auth.secure"), Charsets.UTF_8).split("\n"); //uses a test auth file, which is excluded from git
        assertNull(client.authTsquare(authData[0], authData[1]).select("#msg.errors").first());
        Document doc = client.getDocument("https://t-square.gatech.edu/portal");
        assertNotNull(doc.select("#siteLinkList").first());
    }

    @Test
    public void testAuthFailTsquare() throws IOException {
        Element errors = client.authTsquare("wronguser", "wrongpass").select("#msg.errors").first();
        assertNotNull(errors);
        assertTrue(errors.text().contains("Incorrect login"));
    }

}
