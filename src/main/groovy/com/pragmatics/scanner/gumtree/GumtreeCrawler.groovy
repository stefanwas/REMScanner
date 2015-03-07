package com.pragmatics.scanner.gumtree

import groovy.util.logging.Slf4j

@Slf4j
class GumtreeCrawler {
    public static final String GUMTREE_BASE_URL = "http://www.gumtree.pl/fp-mieszkania-i-domy-sprzedam-i-kupie/krakow/c9073l3200208?A_DwellingType=flat&AdType=2";
    private static final String AD_LINK_PATTERN = /<a href="(.*)"class="adLinkSB" >/;


    private String createPageUrl(int pageNo) {
        return GUMTREE_BASE_URL + "&Page=" + pageNo;
    }

    private String getPageContent(String pageUrl) {
        return new URL(pageUrl).getText();
    }

    private List<String> extractAdLinksFromPage(String pageContent) {

        List<String> titles = pageContent.findAll(AD_LINK_PATTERN);

        List<String> links = new ArrayList<>();
        for (String title : titles) {
            String link = title.replace("<a href=\"", "").replace("\"class=\"adLinkSB\" >", "").trim();
            links.add(link);
        }
        return links;
    }

    public List<String> findAllAdLinks(Integer noOfLastPages, Long waitTime) {
        if (noOfLastPages == null || noOfLastPages < 1) noOfLastPages = 200;

        int currentPage = 1;

        Set<String> allLinks = new LinkedHashSet<>();
        while (currentPage <= noOfLastPages) {
            String pageUrl = createPageUrl(currentPage);
            String pageContent = getPageContent(pageUrl);
            log.debug "Loaded ad list page: " + currentPage;

            List<String> linksOnThePage = extractAdLinksFromPage(pageContent);
            log.debug "Extracted " + linksOnThePage.size() + " links."

            allLinks.addAll(linksOnThePage);
            if (waitTime != null) {
                log.debug "Waiting " + waitTime + " ms..."
                sleep(waitTime);
            }

            currentPage++;
        }

        return new ArrayList<String>(allLinks);
    }

}
