package com.pragmatics.scanner.storage;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class AdStore {
    private MongoClient mongoClient;
    private DB db;

    public void init() throws UnknownHostException {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.db = mongoClient.getDB("test");
    }

    public void close() {
        this.mongoClient.close();
    }
}
