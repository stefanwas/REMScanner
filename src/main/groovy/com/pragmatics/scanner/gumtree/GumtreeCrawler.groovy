package com.pragmatics.scanner.gumtree

class GumtreeCrawler {
    public static final String GUMTREE_BASE_URL = "http://www.gumtree.pl/fp-mieszkania-i-domy-sprzedam-i-kupie/krakow/c9073l3200208?A_DwellingType=flat&AdType=2";
    private static final String AD_LINK_PATTERN = /<a href="(.*)"class="adLinkSB" >/;

    private static final int ONE_DAY = 1;

    private String createPageUrl(int pageNo) {
        return GUMTREE_BASE_URL + "&Page=" + pageNo;
    }

    private String getPageContent(String url) {
        //TODO page download + url
        return new URL(pageUrl).getText();
    }

    public List<String> extractAdLinksFromPage(String pageContent) {

        List<String> titles = pageContent.findAll(AD_LINK_PATTERN);

        List<String> links = new ArrayList<>();
        for (String title : titles) {
            String link = title.replace("<a href=\"", "").replace("\"class=\"adLinkSB\" >", "").trim();
            links.add(link);
        }
        return links;
    }

    public List<String> findAllAdLinks(Integer daysBack) {
        if (daysBack == null || daysBack < 1) daysBack = ONE_DAY;

        int currentPage = 1;
        int age = 0;

        Set<String> allLinks = new LinkedHashSet<>();
        while (age < daysBack) {
            String pageUrl = createPageUrl(currentPage++);
            String pageContent = getPageContent(pageUrl);

            int youngest = calculateYoungestAdAge(pageContent);
            if (youngest > daysBack) break;

            List<String> linksOnThePage = extractAdLinksFromPage(pageContent);
            allLinks.addAll(linksOnThePage);

            age = calculateOldestAdAge(pageContent);
        }

        //TODO logging - how many links from last day???
        return new ArrayList<String>(allLinks);
    }

}
