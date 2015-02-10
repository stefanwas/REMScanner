package com.pragmatics.scanner.gumtree

import com.pragmatics.scanner.model.WebPost
import groovy.util.slurpersupport.GPathResult

import java.security.Provider

class GumtreeExtractor {

    public static final String GUMTREE_TITLE_KEY = "TITLE";
    public static final String GUMTREE_DESCRIPTION_KEY = "DESCRIPTION";
    public static final String GUMTREE_ADD_DATE_KEY = "ADD_DATE";

    private static final String ATTRIBUTE_TABLE_PATTERN = /(?s).*<table id="attributeTable"(?s).*<\/table>(?s).*/;
    private static final String ADD_DATE_PATTERN = /<td class="first_row".*(\d\d\/\d\d\/\d\d\d\d)(?s).*?<\/td>/;
    private static final String PRICE_PATTERN = /Cena(?s).*?<td style='font-weight:bold'>(?s).*?<\/td>/;
    private static final String ADDRESS_PATTERN = /<strong>Adres:<\/strong>.*?<br>/;
    private static final String PROPERTY_TYPE_PATTERN = /Rodzaj nieruchomości(?s).*?<\/td>(?s).*?<\/td>/;
    private static final String SIZE_PATTERN = /Wielkość \(m2\)(?s).*?<\/td>(?s).*?<\/td>/;
    private static final String NO_OF_ROOMS_PATTERN = /Liczba pokoi \(m2\)(?s).*?<\/td>(?s).*?<\/td>/;
    private static final String PROVIDER_PATTERN = /Na sprzedaż przez (?s).*?<\/td>(?s).*?<\/td>/;


    public Map<String, String> extractData(String postHtmlContent) {
        Map<String, String> data = new HashMap<>();




        String attributeTable = src.find(ATTRIBUTE_TABLE_PATTERN);

        println attributeTable;



        String addDate = findAddDate(attributeTable);
        println "ADD DATE" + addDate;


        return data;
    }


    private String findAddDate(String html) {
        def addDate = attributeTable.find(ADD_DATE_PATTERN);

        addDate = addDate.replace("<td class=\"first_row\" >", "");
        addDate = addDate.replace("</td>", "");
        addDate = addDate.trim();

        return  addDate;
    }

    private String extractPrice(String html) {
        String price = attributeTable.find(PRICE_PATTERN);
        return price.replace("Cena", "").replace("<td style='font-weight:bold'>","").replaceAll("</td>","").trim();
    }

    private String extractAddress(String html) {
        String address = attributeTable.find(ADDRESS_PATTERN);
        return address.replace("<strong>Adres:</strong>", "").replace("<br>", "").trim();
    }

    private String extractPropertyType(String html) {
        String propertyType = attributeTable.find(PROPERTY_TYPE_PATTERN);
        return propertyType.replace("Rodzaj nieruchomości","").replaceAll("</td>","").replace("<td >","").trim();
    }

    private String extractSize(String html) {
        String size = attributeTable.find(SIZE_PATTERN);
        return size.replace("Wielkość (m2)","").replaceAll("</td>","").replace("<td >","").trim();

    }

    private String extractNbOfRooms(String html) {
        def noOfRooms = attributeTable.find(NO_OF_ROOMS_PATTERN);
        return noOfRooms.replace("Liczba pokoi","").replaceAll("</td>","").replace("<td >","").trim();
    }

    private String extractProvider(String html) {
        def provider = attributeTable.find(PROVIDER_PATTERN);
        return provider.replace("Liczba pokoi","").replaceAll("</td>","").replace("<td >","").trim();
    }

    public WebPost extractData(GPathResult parsResult) {

        WebPost post = new WebPost();

        // title
        htmlParser.'**'.find{ it.@id == 'preview-local-title'}.each {
            post.setTitle(it.text());
            println "Title" + it.text();
        }

        // price
        htmlParser.'**'.find{ it.@id == 'preview-local-title'}.each {
            post.setTitle(it.text());
            println "Title" + it.text();
        }

        // size
        // content
        // seller
        // rooms

        return post
    }

}
