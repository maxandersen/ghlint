package org.xam.gh;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;


class LabelFetcher {

    static String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");

    public static void main(String[] args) throws IOException {

        List labels = paginate("https://api.github.com/repos/quarkusio/quarkus/labels");

        System.out.println(labels.size());

    }

    static List paginate(String url) {
        Client client = ClientBuilder.newClient();
        //client.register(EntityLoggingFilter.class);
        //client.register(HTTPLoggingFilter.class);

        WebTarget target = client.target(url);

        Response r = target.request().header("Authorization", "token " + GITHUB_TOKEN).get();

        if(r.getStatus()!=200) {
            throw new IllegalStateException(r.getStatusInfo().toString());
        }

        List data = r.readEntity(List.class);

        Link link = r.getLink("next");
        while (link != null) {
            target = client.target(link.getUri());
            r = target.request().header("Authorization", "token " + GITHUB_TOKEN).get();
            data.addAll(r.readEntity(List.class));
            link = r.getLink("next");
            //System.out.println(link.getUri());
        }

        return data;
    }
}