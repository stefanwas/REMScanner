package com.pragmatics.scanner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.pragmatics.scanner.model.Post;

import java.net.UnknownHostException;

public class MongoDBDataAccess {

    private MongoClient mongoClient;
    private DB db;

    public void init() throws UnknownHostException {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.db = mongoClient.getDB("mydb");
    }

    public void close() {
        this.mongoClient.close();
    }

    public void savePost(Post post) {
        DBCollection coll = db.getCollection("posts");
        BasicDBObject obj = convertToDBObject(post);
        coll.insert(obj);

    }

    public Post getPostById(String id) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCollection coll = db.getCollection("posts");
        DBCursor cursor = coll.find(query);
        Post post = null;
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

    private BasicDBObject convertToDBObject(Post p) {
        BasicDBObject object = new BasicDBObject("id", p.getId())
                .append("url", p.getUrl())
                .append("title", p.getTitle())
                .append("content", p.getContent());

        return object;
    }

    private Post convertToPost(DBObject obj) {
        Post post = new Post();

        post.setId((String) obj.get("id"));
        post.setUrl((String) obj.get("url"));
        post.setTitle((String) obj.get("title"));
        post.setContent((String) obj.get("content"));

        return post;
    }
}
