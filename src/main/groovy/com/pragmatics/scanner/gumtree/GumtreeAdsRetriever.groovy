package com.pragmatics.scanner.gumtree

import com.pragmatics.scanner.model.WebPost
import groovy.util.logging.Slf4j

@Slf4j
class GumtreeAdsRetriever {

    private GumtreeCrawler crawler;
    private GumtreeAdContentExtractor extractor;
    private GumtreeDataConverter converter;

    public static void main(String[] args) {
        GumtreeAdsRetriever retriever = new GumtreeAdsRetriever();
        retriever.init();

        retriever.loadAds();

        log.debug "DONE"
    }

    public void init() {
        this.crawler = new GumtreeCrawler();
        this.extractor = new GumtreeAdContentExtractor();
        this.converter = new GumtreeDataConverter();
        log.debug "Retriever initialized.";
    }

    private String getPageContent(String pageUrl) {
        return new URL(pageUrl).getText();
    }

    // scan and get WebPost list
    public List<WebPost> loadAds() {
        List<WebPost> webPosts = new ArrayList<>();

        Map<String, List<WebPost>> titleToAd = new HashMap<>();

        log.debug "Leading ad links...";
        List<String> adLinks = this.crawler.findAllAdLinks(1, 5000);
        log.debug "Leaded " + adLinks.size() + " different links.";

        log.debug "Loading ads..."
        for (String adUrl : adLinks) {
            String adContent = getPageContent(adUrl);
            Map<String, String> adData = extractor.extractData(adContent);
            WebPost ad = this.converter.convert(adData);
            log.debug "Loaded ad: [" + ad.title + "]";

            List<WebPost> existingAds = titleToAd.get(ad.getTitle());
            if (existingAds != null) {
                log.debug "Found similar ad. Checking details..."
                if (alreadyExists(ad, existingAds)) {
                    log.debug "This ad has been already already downloaded and will be dropped."
                    continue;
                }
            }

            if (existingAds == null) {
                existingAds = new ArrayList<WebPost>();
            }

            existingAds.add(ad);
            log.debug "Ad added. Waiting 2000ms...";
            sleep(2000);

        }

        webPosts.addAll(titleToAd.values());
        log.debug "Total number of loaded ads:" + webPosts.size();

        return webPosts;
    }

    private boolean alreadyExists(WebPost ad, WebPost existingAds) {
        for (WebPost existingAd : existingAds) {
            if (haveSamePrice(ad, existingAd) && haveSameSize(ad, existingAd) && haveSameRooms(ad, existingAd))
                return true;
        }
        return false;
    }

    private boolean haveSamePrice(WebPost ad, WebPost exAd) {
        return ad.getPrice() == null && exAd.getPrice() == null || ad.getPrice().equals(exAd.getPrice());
    }

    private boolean haveSameSize(WebPost ad, WebPost exAd) {
        return ad.getSize() == null && exAd.getSize() == null || ad.getSize().equals(exAd.getSize());
    }

    private boolean haveSameRooms(WebPost ad, WebPost exAd) {
        return ad.getNoOfRooms() == null && exAd.getNoOfRooms() == null || ad.getNoOfRooms().equals(exAd.getNoOfRooms());
    }
}
