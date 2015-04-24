package com.example.davidsavrda.sidetracker.client;

import android.util.Base64;
import com.example.davidsavrda.sidetracker.Medication;
import com.example.davidsavrda.sidetracker.MedicationInfo;
import com.example.davidsavrda.sidetracker.model.WsAppUser;
import com.example.davidsavrda.sidetracker.model.WsMedication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * NOTE (LESWING) would prefer eager singleton with DI over static
 */
public class RestClient {
    private static final String MEDICATION_ENDPOINT = "/medication";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String USER_ENDPOINT = "/user";
    private static final DefaultHttpClient client = new DefaultHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String baseUrl = "http://10.0.2.2:8080/api";
    private static String userName = "martha";
    private static String password = "password";
    static {
    }

    public static void setUrl(final String url) {
        baseUrl = baseUrl + "/api";
    }

    public static void setAuth(final String userName, final String password) {
        RestClient.userName = userName;
        RestClient.password = password;
    }

    public static boolean login() {
        try {
            final String myUrl = baseUrl + LOGIN_ENDPOINT;
            final HttpGet get = new HttpGet(myUrl);
            setHeaders(get);
            final HttpResponse response = client.execute(get);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (final RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static WsAppUser updateFHIR(final WsAppUser wsAppUser) {
        try {
            final String myUrl = baseUrl + USER_ENDPOINT;
            HttpPut put = new HttpPut(myUrl);
            setHeaders(put);
            final String entity = objectMapper.writeValueAsString(wsAppUser);
            put.setEntity(new StringEntity(entity));
            return executeRequestForObject(put, WsAppUser.class);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<WsMedication> getMedications() {
        final String myUrl = baseUrl + MEDICATION_ENDPOINT;
        final HttpGet get = new HttpGet(myUrl);
        setHeaders(get);
        return executeRequestForList(get, WsMedication.class);
    }

    public static List<WsMedication> updateMedication(final List<WsMedication> medicationInfos) {
        try {
            final String myUrl = baseUrl + MEDICATION_ENDPOINT;
            HttpPut put = new HttpPut(myUrl);
            setHeaders(put);
            final String entity = objectMapper.writeValueAsString(medicationInfos);
            put.setEntity(new StringEntity(entity));
            return executeRequestForList(put, WsMedication.class);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T executeRequestForObject(final HttpUriRequest request, final Class<T> clazz) {
        try {
            final HttpResponse response = client.execute(request);
            return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            return null;
        }
    }

    private static <T> List<T> executeRequestForList(final HttpUriRequest request, final Class<T> clazz) {
        try {
            final HttpResponse response = client.execute(request);
            return objectMapper.readValue(response.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            return null;
        }

    }

    private static void setHeaders(final HttpRequest httpRequest) {
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader("Accept", "application/json");
        String authorizationString = "Basic " + Base64.encodeToString(
                (userName + ":" + password).getBytes(),
                Base64.NO_WRAP);
        httpRequest.setHeader("Authorization", authorizationString);
    }
}
