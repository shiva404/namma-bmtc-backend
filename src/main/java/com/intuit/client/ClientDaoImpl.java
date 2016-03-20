package com.intuit.client;

import com.intuit.provider.JacksonJsonProvider;
import com.intuit.types.Data;
import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Configuration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 */
public class ClientDaoImpl implements ClientDao {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClientDaoImpl.class);

    Client client;

    public ClientDaoImpl() {
        Configuration configuration = new ClientConfig(JacksonJsonProvider.class);
        client = ClientBuilder.newClient(configuration);
    }

    @Override
    public Data getSomeData() throws Exception {
        final Future<Data> entityFuture = client.target("http://localhost:8389/test/v1/test/sample")
                .request().async().get(new InvocationCallback<Data>() {
                    @Override
                    public void completed(Data data) {
                        LOGGER.info("Response entity '" + data + "' received.");
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        LOGGER.error("Failed : ", throwable);
                    }
                });
        try {
            return entityFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Failed to execute");
        }

    }
}
