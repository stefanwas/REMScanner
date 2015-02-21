package com.pragmatics.scanner.gumtree

class GumtreeCrawler {
    public static final String GUMTREE_BASE_URL = "http://www.gumtree.pl/fp-mieszkania-i-domy-sprzedam-i-kupie/krakow/c9073l3200208?A_DwellingType=flat&AdType=2";
    private static final String AD_LINK_PATTERN = /<a href="(.*)"class="adLinkSB" >/;

    private String createPageUrl(int pageNo) {
        return GUMTREE_BASE_URL + "&Page=" + pageNo;
    }


    private List<String> extractAdLinksFromPage(String pageUrl) {
        String multiAdPageContent = new URL(pageUrl).getText();
        List<String> titles = multiAdPageContent.findAll(AD_LINK_PATTERN);

        List<String> links = new ArrayList<>();
        for (String title : titles) {
            String link = title.replace("<a href=\"", "").replace("\"class=\"adLinkSB\" >", "").trim();
            links.add(link);
        }
        return links;
    }

//    public static void main(String[] args) {
//        GumtreeCrawler crawler = new GumtreeCrawler();
//
//        String pageUrl = crawler.createPageUrl(2);
//
//        List<String> links = crawler.extractAdLinksFromPage(pageUrl);
//
//    }

}
