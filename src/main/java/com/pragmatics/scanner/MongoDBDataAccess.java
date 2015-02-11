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

    public void savePost(WebPost post) {
        DBCollection coll = db.getCollection("posts");
        BasicDBObject obj = convertToDBObject(post);
        coll.insert(obj);

    }

    public WebPost getPostById(String id) {
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCollection coll = db.getCollection("posts");
        DBCursor cursor = coll.find(query);
        WebPost post = null;
        try {
            if(cursor.hasNext()) {
                DBObject obj = cursor.next();
                post = convertToPost(obj);

            }
        } finally {
            cursor.close();
        }
        return post;
    }

    private BasicDBObject convertToDBObject(WebPost p) {
        BasicDBObject object = new BasicDBObject("_id", p.getId())
                .append("url", p.getUrl())
                .append("title", p.getTitle())
                .append("content", p.getDescription());

        return object;
    }

    private WebPost convertToPost(DBObject obj) {
        WebPost post = new WebPost();

        post.setId((String) obj.get("_id"));
        post.setUrl((String) obj.get("url"));
        post.setTitle((String) obj.get("title"));
        post.setDescription((String) obj.get("content"));

        return post;
    }
}
