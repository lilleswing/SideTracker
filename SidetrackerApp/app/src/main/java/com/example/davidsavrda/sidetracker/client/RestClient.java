package com.example.davidsavrda.sidetracker.client;

import com.example.davidsavrda.sidetracker.MedicationInfo;
import com.example.davidsavrda.sidetracker.model.WsAppUser;
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
    private static final DefaultHttpClient client = new DefaultHttpClient();
    private static String baseUrl = "http://localhost:8080";
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
    }

    public static void setUrl(final String url) {
        baseUrl = baseUrl;
    }

    public static void setAuth(final String userName, final String password) {
        final CredentialsProvider credProvider = new BasicCredentialsProvider();
        credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(userName, password));
        client.setCredentialsProvider(credProvider);
    }

    public static boolean login() {
        try {
            final String myUrl = baseUrl + LOGIN_ENDPOINT;
            final HttpGet get = new HttpGet();
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
        }
        return false;
    }

    public static WsAppUser updateFHIR(final MedicationInfo medicationInfo) {
        throw new UnsupportedOperationException("Failure");
    }

    public static List<MedicationInfo> updateMedication(final List<MedicationInfo> medicationInfos) {
        try {
            final String myUrl = baseUrl + MEDICATION_ENDPOINT;
            HttpPut put = new HttpPut(myUrl);
            setHeaders(put);
            final String entity = objectMapper.writeValueAsString(medicationInfos);
            put.setEntity(new StringEntity(entity));
            return executeRequestForList(put, MedicationInfo.class);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
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
    }
}
