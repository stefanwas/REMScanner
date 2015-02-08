package com.pragmatics.scanner

class Scanner {

    public static void main(String[] args) {
        String url = "http://www.gumtree.pl/cp-mieszkania-i-domy-sprzedam-i-kupie/krakow/okazja-ekskluzywny-apartament-59m2-ul-borowego-wola-justowska-619169410";
        def txt = getHTMLContent(url);
        println(txt);
    }


    public static String getHTMLContent(String url) {
        String rawContent = new URL(url).getText();
        return rawContent;
    }
}
