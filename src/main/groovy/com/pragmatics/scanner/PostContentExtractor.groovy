package com.pragmatics.scanner

import org.ccil.cowan.tagsoup.Parser;

class PostContentExtractor {

    private XmlSlurper xmlSlurper;

    public PostContentExtractor() {
        this.xmlSlurper = new XmlSlurper(new Parser());
    }

    public static void main(String[] args) {

        PostContentExtractor extractor = new PostContentExtractor();
        String url = "http://www.gumtree.pl/cp-mieszkania-i-domy-sprzedam-i-kupie/krakow/mieszkanie-krakow-stare-miasto-619182348";
        String html = new URL(url).getText();
        extractor.extractData(html);
    }

    public Map<String, String> extractData(String html) {

        def result = new HashMap();
        def htmlParser = this.xmlSlurper.parseText(html);

//        htmlParser.'**'.find{ it.@id == 'preview-local-title'}.each {
//            result.put("title", it.text())
//            println it
//        }

        // <table id="attributeTable" ... </tbody> </table>
        htmlParser.'**'.find{ it.@id == 'attributeTable'}.each {
            result.put("title", it)
            println it
        }

        def text = html;
        def pattern =  /.*HtmlPageTail.*/;

        def matcher = text =~ pattern;

        println "Mateches=" + matcher.matches();

        def table = htmlParser.'**'.find{ it.@id == 'attributeTable'};
        println "----- table -----";
        println table.text();
        println "----- table -----";

        println "size:" + table.tbody.tr.size();
        table.tbody.tr.each {
            result.put("TR", it)
            println it
        }


        return result;
    }

}
