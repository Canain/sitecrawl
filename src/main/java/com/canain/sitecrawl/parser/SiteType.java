package com.canain.sitecrawl.parser;

/**
 * 2015/03/23
 *
 * @author Shuyang Chen
 */
public class SiteType {

    public static final int UNKNOWN = 0;
    public static final int TSQUARE = 1;
    public static final int WEBASSIGN = 2;

    public static class Tsquare {
        public static final int NAV = 0;
        public static final int SIDE = 1;
        public static final int ASSIGNMENTS_IFRAME = 2;
        public static final int ASSIGNMENTS = 3;
    }
}
