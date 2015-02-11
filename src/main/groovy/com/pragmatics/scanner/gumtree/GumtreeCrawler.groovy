package com.pragmatics.scanner.gumtree

import org.ccil.cowan.tagsoup.Parser

class GumtreeCrawler {
    public static final String GUMTREE_BASE_URL = "http://www.gumtree.pl/fp-mieszkania-i-domy-sprzedam-i-kupie/krakow/c9073l3200208?A_DwellingType=flat&AdType=2";

    private XmlSlurper xmlSlurper = new XmlSlurper(new Parser());

    private String createPageUrl(int pageNo) {
        return GUMTREE_BASE_URL + "&Page=" + pageNo;
    }

    private List<String> extractAdsFromPage(String pageUrl) {
        String multiAdPageContent = new URL(pageUrl).getText();

        def htmlParser = this.xmlSlurper.parseText(multiAdPageContent);

        htmlParser.'**'.find{ it.@class == 'adLinkSB'}.each {
            println adtitle = it.href.text();
        }
        return title;
    }

}
