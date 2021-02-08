package Model;

import Controller.Authenticator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.function.BiFunction;


@SuppressWarnings("unchecked Duplicates")


public class CustomObjectHandler {


    private Authenticator authenticator;


    public boolean  checkCustomObjectRecord(String id) throws Exception {


        //URL TO GET CUSTOM OBJECT DATA.
        String url = "https://secure.p06.eloqua.com/api/REST/1.0/data/customObject/" + id;


        //URL CREATION
        URL obj = new URL(url);

        //OPEN CONNECTION
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //GET REQUEST
        con.setRequestMethod("GET");


        //AUTHORIZATION FOR FIRST CALL
        Authenticator authenticator = new Authenticator();
        String auth = authenticator.getAuthString();
        auth = Base64.getEncoder().encodeToString((auth).getBytes());
        String encodedAuthorization = auth;
        con.setRequestProperty("Authorization", "Basic " +
                encodedAuthorization);

        //GET INPUT STREAM FROM GET REQUEST
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //GET FORM FIELD NAME, GIVEN A SPECIFIC ID.s
        JSONObject customObjectJson = new JSONObject(response.toString());
        JSONArray recs = customObjectJson.getJSONArray("elements");

        if (recs.length() == 0) {

            return false;
        }


        return true;

    }

    public String getRecordCount(String id) throws IOException {


        //URL TO GET CUSTOM OBJECT DATA.
        String url = "https://secure.p06.eloqua.com/api/REST/1.0/data/customObject/" + id;


        //URL CREATION
        URL obj = new URL(url);

        //OPEN CONNECTION
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //GET REQUEST
        con.setRequestMethod("GET");

        //AUTHORIZATION FOR FIRST CALL
        Authenticator authenticator = new Authenticator();
        String auth = authenticator.getAuthString();
        auth = Base64.getEncoder().encodeToString((auth).getBytes());
        String encodedAuthorization = auth;
        con.setRequestProperty("Authorization", "Basic " +
                encodedAuthorization);

        //GET INPUT STREAM FROM GET REQUEST
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //GET FORM FIELD NAME, GIVEN A SPECIFIC ID.s
        JSONObject customObjectJson = new JSONObject(response.toString());
        String totalRecords = customObjectJson.getString("total");

        return totalRecords;

    }


}
