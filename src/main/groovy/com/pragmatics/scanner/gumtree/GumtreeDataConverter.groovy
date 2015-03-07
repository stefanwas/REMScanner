package com.pragmatics.scanner.gumtree

import com.pragmatics.scanner.model.AdSource
import com.pragmatics.scanner.model.WebPost

import java.text.NumberFormat


class GumtreeDataConverter {

    private static NumberFormat FORMATTER = NumberFormat.getInstance(Locale.FRANCE);

    public WebPost convert(Map<String, String> data) {
        WebPost post = new WebPost();

        post.setSource(AdSource.GUMTREE);
        post.setTitle(data.get(GumtreeAdContentExtractor.TITLE_KEY));
        post.setDescription(data.get(GumtreeAdContentExtractor.DESCRIPTION_KEY));
        post.setAddress(data.get(GumtreeAdContentExtractor.ADDRESS_KEY));

        String priceStr = data.get(GumtreeAdContentExtractor.PRICE_KEY);
        Double price = FORMATTER.parse(priceStr.replace("Zł", "").replaceAll(" ", "").trim()).doubleValue();
        post.setPrice(price);

        String sizeStr = data.get(GumtreeAdContentExtractor.SIZE_KEY);
        Double size = Double.parseDouble(sizeStr.trim());
        post.setSize(size);

        String noOfRoomsStr = data.get(GumtreeAdContentExtractor.NO_OF_ROOMS_KEY).split(" ")[0];
        Integer noOfRooms = null;
        if (noOfRoomsStr != null) {
            if ("Kawalerka".equalsIgnoreCase(noOfRoomsStr) || "Garsoniera".equalsIgnoreCase(noOfRoomsStr)) {
                noOfRooms = 1;
            } else {
                noOfRooms = Integer.parseInt(noOfRoomsStr);
            }
        }
        post.setNoOfRooms(noOfRooms);

        return post;
    }

}
