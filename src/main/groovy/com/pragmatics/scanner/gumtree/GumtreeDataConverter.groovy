package com.pragmatics.scanner.gumtree

import com.pragmatics.scanner.model.AdSource
import com.pragmatics.scanner.model.WebPost


class GumtreeDataConverter {

    public WebPost convert(Map<String, String> data) {
        WebPost post = new WebPost();

        post.setSource(AdSource.GUMTREE);
        post.setTitle(data.get(GumtreeAdContentExtractor.TITLE_KEY));
        post.setDescription(data.get(GumtreeAdContentExtractor.DESCRIPTION_KEY));
        post.setAddress(data.get(GumtreeAdContentExtractor.ADDRESS_KEY));

        String priceStr = data.get(GumtreeAdContentExtractor.PRICE_KEY);
        Double price = Double.parseDouble(priceStr.replace("Zł").trim());
        post.setPrice(price);

        String sizeStr = data.get(GumtreeAdContentExtractor.SIZE_KEY);
        Double size = Double.parseDouble(sizeStr.trim());
        post.setSize(size);

        String noOfRoomsStr = data.get(GumtreeAdContentExtractor.NO_OF_ROOMS_KEY).split(" ")[0];
        Integer noOfRooms = Integer.parseInt(noOfRoomsStr);
        post.setNoOfRooms(noOfRooms);

        return post;
    }
}
