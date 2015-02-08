package com.pragmatics.scanner

import com.pragmatics.scanner.model.WebPost
import groovy.util.slurpersupport.GPathResult

class GumtreeExtractor {

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
