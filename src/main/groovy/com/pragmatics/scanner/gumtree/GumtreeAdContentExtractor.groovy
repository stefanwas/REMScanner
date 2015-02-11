package com.pragmatics.scanner.gumtree

import org.ccil.cowan.tagsoup.Parser;

class GumtreeAdContentExtractor {

    public static final String TITLE_KEY = "TITLE";
    public static final String DESCRIPTION_KEY = "DESCRIPTION";
    public static final String ADD_DATE_KEY = "ADD_DATE";
    public static final String PRICE_KEY = "PRICE";
    public static final String ADDRESS_KEY = "ADDRESS";
    public static final String PROPERTY_TYPE_KEY = "PROPERTY_TYPE";
    public static final String SIZE_KEY = "SIZE";
    public static final String NO_OF_ROOMS_KEY = "NO_OF_ROOMS";
    public static final String PROVIDER_KEY = "PROVIDER";

    private static final String ATTRIBUTE_TABLE_PATTERN = /(?s).*<table id="attributeTable"(?s).*<\/table>(?s).*/;
    private static final String ADD_DATE_PATTERN = /<td class="first_row".*(\d\d\/\d\d\/\d\d\d\d)(?s).*?<\/td>/;
    private static final String PRICE_PATTERN = /Cena(?s).*?<td style='font-weight:bold'>(?s).*?<\/td>/;
    private static final String ADDRESS_PATTERN = /<strong>Adres:<\/strong>.*?<br>/;
    private static final String PROPERTY_TYPE_PATTERN = /Rodzaj nieruchomości(?s).*?<\/td>(?s).*?<\/td>/;
    private static final String SIZE_PATTERN = /Wielkość \(m2\)(?s).*?<\/td>(?s).*?<\/td>/;
    private static final String NO_OF_ROOMS_PATTERN = /Liczba pokoi(?s).*?<\/td>(?s).*?<\/td>/;
    private static final String PROVIDER_PATTERN = /Na sprzedaż przez(?s).*?<\/td>(?s).*?<\/td>/;

    private XmlSlurper xmlSlurper = new XmlSlurper(new Parser());

    public Map<String, String> extractData(String postHtml) {

        String title = extractTitle(postHtml);
        String description = extractDescription(postHtml);

        String attributeTable = postHtml.find(ATTRIBUTE_TABLE_PATTERN);

        String price = extractPrice(attributeTable);
        String addDate = findAddDate(attributeTable);
        String address = extractAddress(attributeTable);
        String propertyType = extractPropertyType(attributeTable);
        String size = extractSize(attributeTable);
        String noOfRooms = extractNbOfRooms(attributeTable);
        String provider = extractProvider(attributeTable);

        Map<String, String> data = new HashMap<>();

        data.put(TITLE_KEY, title);
        data.put(DESCRIPTION_KEY, description);
        data.put(PRICE_KEY, price);
        data.put(ADD_DATE_KEY, addDate);
        data.put(ADDRESS_KEY, address);
        data.put(PROPERTY_TYPE_KEY, propertyType);
        data.put(SIZE_KEY, size);
        data.put(NO_OF_ROOMS_KEY, noOfRooms);
        data.put(PROVIDER_KEY, provider);

        return data;
    }

    private String extractTitle(String html) {
        def htmlParser = this.xmlSlurper.parseText(html);
        String title = null;
        htmlParser.'**'.find{ it.@id == 'preview-local-title'}.each {
            title = it.text().trim();
        }
        return title;
    }

    private String extractDescription(String html) {
        def htmlParser = this.xmlSlurper.parseText(html);
        String description = null;
        htmlParser.'**'.find{ it.@id == 'preview-local-desc'}.each {
            description = it.text();
        }
        return description;
    }

    private String findAddDate(String html) {
        def addDate = html.find(ADD_DATE_PATTERN);
        return addDate.replace("<td class=\"first_row\" >", "").replace("</td>", "").trim();
    }

    private String extractPrice(String html) {
        String price = html.find(PRICE_PATTERN);
        return price.replace("Cena", "").replace("<td style='font-weight:bold'>","").replaceAll("</td>","").trim();
    }

    private String extractAddress(String html) {
        String address = html.find(ADDRESS_PATTERN);
        return address.replace("<strong>Adres:</strong>", "").replace("<br>", "").trim();
    }

    private String extractPropertyType(String html) {
        String propertyType = html.find(PROPERTY_TYPE_PATTERN);
        return propertyType.replace("Rodzaj nieruchomości","").replaceAll("</td>","").replace("<td >","").trim();
    }

    private String extractSize(String html) {
        String size = html.find(SIZE_PATTERN);
        return size.replace("Wielkość (m2)","").replaceAll("</td>","").replace("<td >","").trim();

    }

    private String extractNbOfRooms(String html) {
        def noOfRooms = html.find(NO_OF_ROOMS_PATTERN);
        return noOfRooms.replace("Liczba pokoi","").replaceAll("</td>","").replace("<td >","").trim();
    }

    private String extractProvider(String html) {
        def provider = html.find(PROVIDER_PATTERN);
        return provider.replace("Na sprzedaż przez","").replaceAll("</td>","").replace("<td >","").trim();
    }

}
