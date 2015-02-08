package com.pragmatics.scanner

import groovy.util.slurpersupport.GPathResult
import groovyx.net.http.HTTPBuilder
import org.ccil.cowan.tagsoup.Parser;

class ContentReader {

    public static void main(String[] args) {

        def src = new File("D:\\DEVELOPMENT\\INTELLISPACE\\REMScanner\\src\\main\\resources\\sample_post.html").text;

//        println "FILE:" + src;
//        def text = "\nala facebook kota";
        def pattern = /(?s).*<table id="attributeTable"(?s).*<\/table>(?s).*/;

        def matcher = src =~ pattern;
        println "Matches=" + matcher.matches();


        println "------------------------------------------------------------";
        def attributeTablePattern = /<table id="attributeTable"(?s).*?<\/table>/;

        def attributeTable = src.find(attributeTablePattern);

        def addDatePattern = //

        println a;


    }

    private HTTPBuilder;
    private XmlSlurper xmlSlurper;

    public ContentReader() {
        this.xmlSlurper = new XmlSlurper(new Parser());
    }


    private String getPageContentBasic(String url) {
        String content = new URL(url).getText();
        return content;
    }

    private String getPageContent(String url) {

    }

    public GPathResult getContent() {

    }

//    def result = new HashMap();
//    def htmlParser = this.xmlSlurper.parseText(html);
//
//    htmlParser.'**'.findAll{ it.@id == 'preview-local-title'}.each {
//        result.put("title", it)
//        println it
//    }

    public static String getPageContentTest(String url) {

        String rawContent = new URL(url).getText();

//        def http = new HTTPBuilder("http://www.gumtree.pl");
//        def html = http.get(pathq, TEXT) {


        return "";
    }
}
