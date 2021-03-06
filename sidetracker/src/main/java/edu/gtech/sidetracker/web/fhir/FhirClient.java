package edu.gtech.sidetracker.web.fhir;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gtech.sidetracker.web.model.AppUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Singleton
public class FhirClient {
    private static final int TIMEOUT_SECONDS = 20;
    private static final String MEDICATION_PRESCRIPTION = "/MedicationPrescription?subject:Patient=";
    private String url;

    @Inject
    public FhirClient() {
        url = "https://healthport.i3l.gatech.edu:8443/dstu1/fhir";
    }

    public String getPatient(final AppUser appUser) {
        try {
            if (Strings.isNullOrEmpty(appUser.getFhirId())) {
                return "";
            }
            final String fullUrl = this.url + MEDICATION_PRESCRIPTION + appUser.getFhirId();
            final URL oracle = new URL(fullUrl);
            HttpURLConnection huc = (HttpURLConnection) oracle.openConnection();
            HttpURLConnection.setFollowRedirects(false);
            huc.setConnectTimeout(TIMEOUT_SECONDS * 1000);
            huc.setRequestMethod("GET");
            huc.connect();
            final BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));

            final StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                sb.append(inputLine);
            in.close();
            return sb.toString();
        } catch (final Exception e) {
            return "";
        }
    }

    public void setUrl(final String newUrl) {
        if (newUrl.endsWith("/")) {
            this.url = newUrl.substring(newUrl.length()-1);
        }
        this.url = newUrl;
    }
}
