package com.example.davidsavrda.sidetracker.client;

import com.example.davidsavrda.sidetracker.MedicationInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * NOTE (LESWING) would prefer eager singleton with DI over static
 */
public class RestClient {
    private static final String MEDICATION_ENDPOINT = "/medication";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final DefaultHttpClient client = new DefaultHttpClient();
    private static String baseUrl = "http://localhost:8080";
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
    }

    static void setUrl(final String url) {
        baseUrl = baseUrl;
    }

    static void setAuth(final String userName, final String password) {
        final CredentialsProvider credProvider = new BasicCredentialsProvider();
        credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(userName, password));
        client.setCredentialsProvider(credProvider);

    }

    static MedicationInfo updateMedication(final MedicationInfo medicationInfo) {
        try {
            final String myUrl = baseUrl + MEDICATION_ENDPOINT;
            HttpPut put = new HttpPut(myUrl);
            setHeaders(put);
            final String entity = objectMapper.writeValueAsString(medicationInfo);
            put.setEntity(new StringEntity(entity));
            return executeRequest(put, MedicationInfo.class);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T executeRequest(final HttpUriRequest request, final Class<T> clazz) {
        try {
            final HttpResponse response = client.execute(request);
            return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            return null;
        }

    }

    private static void setHeaders(final HttpRequest httpRequest) {
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader("Accept", "application/json");
    }
}
