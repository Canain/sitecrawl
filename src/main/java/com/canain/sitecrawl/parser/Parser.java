package com.canain.sitecrawl.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 2015/03/23
 *
 * @author Shuyang Chen
 */
public class Parser {

    private Document doc;
    private int siteType;

    public Parser(Document doc, int siteType) {
        this.doc = doc;
        this.siteType = siteType;
    }

    public Parser(Document doc) {
        this(doc, SiteType.UNKNOWN);
    }

    public static Parser parse(String url) throws IOException {
        return new Parser(Jsoup.connect(url).get());
    }

    public String getText(String selector) {
        return doc.select(selector).text();
    }

    public int getIntText(String selector) {
        return Integer.parseInt(getText(selector));
    }

    /**
     * Returns data specified from the type
     * Example usage: getData(SiteType.Tsquare.NAV)
     * gets the navigation from tsquare in the form of {{'Class1': 'Class1Url'}, {'Class2': 'Class2Url'}}
     *
     * @param type A type from a SiteType.Site
     * @return the data
     */
    public Map<String, String> getData(int type) {
        Map<String, String> data = new LinkedHashMap<String, String>();
        switch(siteType) {
            case SiteType.TSQUARE:
                switch(type) {
                    case SiteType.Tsquare.NAV:
                        Elements navs = doc.select("#siteLinkList li a");
                        for (Element nav : navs) {
                            data.put(nav.text(), nav.attr("href"));
                        }
                        break;
                    case SiteType.Tsquare.SIDE:
                        Elements sides = doc.select("#toolMenu li a");
                        for (Element side : sides) {
                            data.put(side.text(), side.attr("href"));
                        }
                        break;
                    case SiteType.Tsquare.ASSIGNMENTS_IFRAME:
                        Element iframe = doc.select("iframe[title~=Assignments]").first();
                        data.put("iframe", iframe.attr("src"));
                        break;
                    case SiteType.Tsquare.ASSIGNMENTS:
                        Elements trs = doc.select("form[name=listAssignmentsForm] tr:not(:first-child)");
                        for (int i = 0; i < trs.size(); i++) {
                            Element tr = trs.get(i);
                            Elements tds = tr.select("td:not(:first-child)");
                            for (Element td : tds) {
                                String header = td.attr("headers");
                                if (!header.isEmpty()) {
                                    data.put(i + ":" + header, td.text());
                                }
                            }
                        }
                        break;
                    case SiteType.Tsquare.AUTH:
                        data.put("lt", doc.select("input[name=lt]").attr("value"));
                        data.put("execution", doc.select("input[name=execution]").attr("value"));
                        break;
                    default:
                        break;
                }
                break;
            case SiteType.WEBASSIGN:
                switch(type) {
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return data;
    }

}
