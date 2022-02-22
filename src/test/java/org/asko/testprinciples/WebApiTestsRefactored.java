package org.asko.testprinciples;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpResponse.BodyHandlers.discarding;

public class WebApiTestsRefactored {
    private static final String BASE_URL = "https://api.github.com/";
    static HttpResponse<Void> response;

    @BeforeClass
    public void getResponse() throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URL))
                .setHeader("User-Agent", "Java 11 Http bot")
                .build();
        response =  httpClient.send(get, discarding());
    }

    @Test
    void getReturns200() throws IOException, InterruptedException{

        int actualCode = response.statusCode();
        Assert.assertEquals(actualCode, 200);
    }

    @Test
    void contentTypeIsJson() throws IOException, InterruptedException{

        String contentType = response.headers().firstValue("content-type").get();
        Assert.assertEquals(contentType, "application/json; charset=utf-8");
    }
}
