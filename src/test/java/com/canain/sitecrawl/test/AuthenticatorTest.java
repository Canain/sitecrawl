package com.canain.sitecrawl.test;

import com.canain.sitecrawl.authenticator.WebClient;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 2015/03/25
 *
 * @author Shuyang Chen
 */
public class AuthenticatorTest {

    @Test
    public void testAuthTsquare() throws IOException {
        WebClient client = new WebClient();
        String[] authData = Files.toString(new File("testsites/tsquare/auth.secure"), Charsets.UTF_8).split("\n");
        client.authTsquare(authData[0], authData[1]);
    }

}
