package com.pragmatics.scanner;

import com.pragmatics.scanner.model.WebPost;

public class PostHandler {

    private MongoDBDataAccess mongoDBDataAccess = new MongoDBDataAccess();

    public static void main(String[] args) throws Exception {
        PostHandler handler = new PostHandler();
        handler.init();
        handler.test();
        handler.close();
    }

    public void  init() throws Exception {
        this.mongoDBDataAccess.init();
    }

    public void close() {
        this.mongoDBDataAccess.close();
    }

    public void test() {
        WebPost p1 = new WebPost();

        p1.setId("P1");
        p1.setTitle("Przytulna kawalerka");
        p1.setDescription("Ala ma kota ale ma kota 123123");
        p1.setUrl("https://www.youtube.com/watch?v=kDfw12hQJnY");

        this.mongoDBDataAccess.savePost(p1);

        WebPost p2 = this.mongoDBDataAccess.getPostById("P1");

        System.out.println(p2);

    }
}
