package com.pragmatics.scanner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.pragmatics.scanner.model.WebPost;

import java.net.UnknownHostException;

public class MongoDBDataAccess {

    private MongoClient mongoClient;
    private DB db;

    public void init() throws UnknownHostException {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.db = mongoClient.getDB("test");
    }

    public void close() {
        this.mongoClient.close();
    }

    public void savePost(WebPost webPost) {
        DBCollection coll = db.getCollection("posts");
        BasicDBObject obj = convertToDBObject(webPost);
        coll.insert(obj);

    }

    public WebPost getPostById(String id) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCollection coll = db.getCollection("posts");
        DBCursor cursor = coll.find(query);
        WebPost webPost = null;
        try {
            if(cursor.hasNext()) {
                DBObject obj = cursor.next();
                webPost = convertToPost(obj);

            }
        } finally {
            cursor.close();
        }
        return webPost;
    }

    private BasicDBObject convertToDBObject(WebPost p) {
        BasicDBObject object = new BasicDBObject("id", p.getId())
                .append("url", p.getUrl())
                .append("title", p.getTitle())
                .append("content", p.getContent());

        return object;
    }

    private WebPost convertToPost(DBObject obj) {
        WebPost webPost = new WebPost();

        webPost.setId((String) obj.get("id"));
        webPost.setUrl((String) obj.get("url"));
        webPost.setTitle((String) obj.get("title"));
        webPost.setContent((String) obj.get("content"));

        return webPost;
    }
}
