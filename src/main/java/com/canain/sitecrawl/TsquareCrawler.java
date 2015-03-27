package com.canain.sitecrawl;

import com.canain.sitecrawl.authenticator.WebClient;
import com.canain.sitecrawl.parser.SiteType;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 2015/03/26
 *
 * @author Shuyang Chen
 */
public class TsquareCrawler {

    private WebClient client;
    private String user;
    private String pass;

    private Map<String, String> navs;
    private Map<String, Map<String, String>> sides;
    private Map<String, Map<String, String>> assignments;

    private boolean auth;

    public TsquareCrawler(String user, String pass) {
        this.user = user;
        this.pass = pass;
        client = new WebClient();
        auth = false;

        sides = new HashMap<String, Map<String, String>>();
        assignments = new HashMap<String, Map<String, String>>();
    }

    /**
     * Logins into Tsquare
     * @return true if auth success, false if failed
     */
    public boolean auth() {
        try {
            client.authTsquare(user, pass);
            auth = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<String, String> getNavs() {
        if (!auth) {
            return null;
        }

        if (navs == null) {
            try {
                navs = client.getParser("https://t-square.gatech.edu/portal", SiteType.TSQUARE).getData(SiteType.Tsquare.NAV);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return navs;
    }

    public Map<String, String> getSides(String nav) {
        if (!auth) {
            return null;
        }

        Map<String, String> navSides = sides.get(nav);
        if (navSides == null) {
            try {
                Map<String, String> navs = getNavs();
                if (navs == null) {
                    return null;
                }
                navSides = client.getParser(navs.get(nav), SiteType.TSQUARE).getData(SiteType.Tsquare.SIDE);
                sides.put(nav, navSides);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return navSides;
    }

    public Map<String, String> getAssignments(String nav) {
        if (!auth) {
            return null;
        }

        Map<String, String> navAssignments = assignments.get(nav);
        if (navAssignments == null) {
            Map<String, String> navSides = getSides(nav);
            if (navSides == null) {
                return null;
            }

            String assignmentsUrl = navSides.get("Assignments");

            if (assignmentsUrl == null) {
                return null;
            }

            try {
                String url = client.getParser(assignmentsUrl, SiteType.TSQUARE).getData(SiteType.Tsquare.ASSIGNMENTS_IFRAME).get("iframe");

                navAssignments = client.getParser(url, SiteType.TSQUARE, 15000).getData(SiteType.Tsquare.ASSIGNMENTS);
                assignments.put(nav, navAssignments);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return navAssignments;
    }

    /**
     * Gets all future assignments for all classes in reddit table format
     * @return all future assignments for all classes in reddit table format
     */
    public String getAllFutureAssignments() {
        StringBuilder sb = new StringBuilder();

        sb.append("Class|Title|Status|Open Date|Due Date\n");
        sb.append(":--|:--|:--|:--|:--\n");

        Map<String, String> navs = getNavs();

        // Feb 9, 2015 3:00 pm
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy h:mm a");

        for (Map.Entry<String, String> entry : navs.entrySet()) {
            String clas = entry.getKey();
            Map<String, String> navAssignments = getAssignments(clas);

            if (navAssignments == null) {
                continue;
            }

            int maxValue = -1;
            int value;
            for (String key : navAssignments.keySet()) {
                value = Integer.parseInt(key.split(":")[0]);
                if (value > maxValue) {
                    maxValue = value;
                }
            }

            String dueDate;
            long currentTime = (new Date()).getTime();
            for (int i = 0; i <= maxValue; i++) {
                dueDate = navAssignments.get(i + ":dueDate");
                try {
                    if (sdf.parse(dueDate).getTime() > currentTime) {
                        sb.append(clas).append("|");
                        sb.append(navAssignments.get(i + ":title")).append("|");
                        sb.append(navAssignments.get(i + ":status")).append("|");
                        sb.append(navAssignments.get(i + ":openDate")).append("|");
                        sb.append(dueDate).append("\n");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

}
