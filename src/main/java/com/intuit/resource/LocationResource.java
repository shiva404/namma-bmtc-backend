package com.intuit.resource;

import com.intuit.mongo.MongoDao;
import com.intuit.types.Data;
import com.intuit.types.Location;
import com.intuit.types.LocationPage;
import com.intuit.types.ReferenceToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 */
@Consumes("application/json")
@Produces("application/json")
@Path("/location")
@Singleton
@Component
public class LocationResource {

    @Autowired
    private MongoDao mongoDao;

    public LocationResource(){
    }

    @POST
    public void saveLocation(@Suspended final AsyncResponse asyncResponse, Location location){
        //todo: validate
        new Thread(() -> {
            String refToken = mongoDao.insertLocation(location);
            asyncResponse.resume(Response.ok().entity(new ReferenceToken(refToken)).build());
        }).start();
    }

    @GET
    public void getLocationsNearBy(@Suspended final AsyncResponse asyncResponse,
                                   @QueryParam("lat") Double lat,
                                   @QueryParam("long") Double longitude,
                                   @QueryParam("range") @DefaultValue("3.0") Double rangeInKm){
        new Thread(()->{
            List<Location> locations = mongoDao.getLocations(lat, longitude, rangeInKm);
            locations.forEach(System.out::println);
            LocationPage locationPage = new LocationPage();
            locationPage.getLocations().addAll(locations);
            locationPage.setOffset(0);
            locationPage.setSize(locations.size());
            asyncResponse.resume(Response.ok().entity(locationPage).build());
        }).start();
    }
}
