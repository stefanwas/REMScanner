package com.pragmatics.scanner

import groovy.util.slurpersupport.GPathResult
import groovyx.net.http.HTTPBuilder
import org.ccil.cowan.tagsoup.Parser;

class ContentReader {

    private static final String ATTRIBUTE_TABLE_PATTERN = /(?s).*<table id="attributeTable"(?s).*<\/table>(?s).*/;

    public static void main(String[] args) {


        def src = new File("D:\\IDEASPACE\\REMScanner\\src\\main\\resources\\sample_post.html").text;

//        println "FILE:" + src;
//        def text = "\nala facebook kota";
        def pattern = /(?s).*<table id="attributeTable"(?s).*<\/table>(?s).*/;

        def matcher = src =~ pattern;
        println "Matches=" + matcher.matches();


        println "------------------------------------------------------------";
        def attributeTablePattern = /<table id="attributeTable"(?s).*?<\/table>/;

        def attributeTable = src.find(attributeTablePattern);

        println attributeTable;


        def addDatePattern = /<td class="first_row".*(\d\d\/\d\d\/\d\d\d\d)(?s).*?<\/td>/;
        def addDate = attributeTable.find(addDatePattern);
        addDate = addDate.replaceAll("<td class=\"first_row\" >", "");
        addDate = addDate.replaceAll("</td>", "");
        addDate = addDate.trim();

        println "ADD DATE=" + addDate;

        def pricePattern = /Cena(?s).*?<td style='font-weight:bold'>(?s).*?<\/td>/;


        def price = attributeTable.find(pricePattern);
        price = price.replace("Cena", "");
        price = price.replace("<td style='font-weight:bold'>","");
        price = price.replaceAll("</td>","").trim();

        println price;

        def addressPattern = /<strong>Adres:<\/strong>.*?<br>/;
        def address = attributeTable.find(addressPattern);
        address = address.replace("<strong>Adres:</strong>", "").replace("<br>", "").trim();

        println address;

        def propertyTypePattern = /Rodzaj nieruchomości(?s).*?<\/td>(?s).*?<\/td>/;
        def propertyType = attributeTable.find(propertyTypePattern);
        propertyType = propertyType.replace("Rodzaj nieruchomości","").replaceAll("</td>","").replace("<td >","").trim();
        println propertyType;

        def sizePattern = /Wielkość \(m2\)(?s).*?<\/td>(?s).*?<\/td>/;
        def size = attributeTable.find(sizePattern);
        size = size.replace("Wielkość (m2)","").replaceAll("</td>","").replace("<td >","").trim();
        println size;

        def noOfRoomsPattern = /Liczba pokoi \(m2\)(?s).*?<\/td>(?s).*?<\/td>/;
        def noOfRooms = attributeTable.find(noOfRoomsPattern);
        noOfRooms = noOfRooms.replace("Liczba pokoi","").replaceAll("</td>","").replace("<td >","").trim();
        println noOfRooms;

    }

    private String findAttributeTable (String html) {

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
