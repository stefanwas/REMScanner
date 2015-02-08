package com.pragmatics.scanner

import groovy.util.slurpersupport.GPathResult
import org.ccil.cowan.tagsoup.Parser

class WebPageReader {

    String url;
    private XmlSlurper xmlSlurper;

    public WebPageReader(String url) {
        this.url = url;
        this.xmlSlurper =  new XmlSlurper(new Parser());
    }

    public GPathResult getWebPageContent(String path) {
        String html = getHtml(path);
        return xmlSlurper.parseText(html);
    }

    private String getHtml(String path) {
        return new URL(url + path).getText();
    }

    private String getHtmlAdvanced(String path) {
        //TODO
        return null;
    }
}
