package com.intuit.mongo;

import com.intuit.types.Location;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class MongoDao {
    private MongoCollection<BasicDBObject> collection;

    MongoDatabase mongoDatabase;

    public MongoDao(MongoClient mongoClient, String database, String collectionName){
        this.mongoDatabase = mongoClient.getDatabase(database);
        collection = mongoDatabase.getCollection(collectionName, BasicDBObject.class);
    }

    public String insertLocation(Location location) {
        String refToken = UUID.randomUUID().toString();
        BasicDBObject basicDBObject = MongoMapper.getLocationDocument(refToken, location);
        collection.insertOne(basicDBObject);
        return refToken;
    }

    public List<Location> getLocations(Double lat, Double longitude, Double rangeInKm) {
        List<Location> locations = new LinkedList<>();

        BasicDBObject geoFilter = new BasicDBObject("$geometry", new Point(new Position(lat, longitude)));
        geoFilter.append("$maxDistance", rangeInKm * 1000);

        BasicDBObject nearFilter = new BasicDBObject("$near", geoFilter);

        FindIterable<BasicDBObject> basicDBObjects = collection.find(new BasicDBObject("loc", nearFilter));
        for(BasicDBObject basicDBObject : basicDBObjects){
            BasicDBList basicDBList = (BasicDBList) basicDBObject.get("loc");
            Double tempLat = (Double) basicDBList.get(0);
            System.out.println(tempLat);
            Double tempLong = (Double)basicDBList.get(1);
            System.out.println(tempLong);
            locations.add(new Location(tempLat, tempLong));
        }
        return locations;
    }
}
