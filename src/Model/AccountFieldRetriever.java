package Model;

import Controller.Authenticator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("unchecked Duplicates")

public class AccountFieldRetriever {


    private Authenticator authenticator;

    private HashMap<String, HashMap<String, String>> allStandardAccountFields = new HashMap<>();
    private HashMap<String, HashMap<String, String>> allCustomAccountFields = new HashMap<>();

    private ArrayList<String> standardAccountFieldId = new ArrayList<>();
    private ArrayList<String> standardAccountFieldName = new ArrayList();

    private ArrayList<String> customAccountFieldId = new ArrayList<>();
    private ArrayList<String> customAccountFieldName = new ArrayList();



    private String auth;

    public void getContactFields() throws Exception {

        //URL TO GET ALL ACCOUNT FIELDS.
        String url = "https://secure.p06.eloqua.com/api/REST/1.0/assets/account/fields?depth=complete";

        //URL CREATION
        URL obj = new URL(url);

        //OPEN CONNECTION
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //GET REQUEST
        con.setRequestMethod("GET");

        //AUTHORIZATION FOR FIRST CALL
        Authenticator authenticator = new Authenticator();
        auth = authenticator.getAuthString();
        this.auth = Base64.getEncoder().encodeToString((auth).getBytes());
        String encodedAuthorization = auth;
        con.setRequestProperty("Authorization", "Basic " +
                encodedAuthorization);

        //RESPONSE CODE  200-> Success
        int responseCode = con.getResponseCode();
        System.out.println("\nPerforming Vone's Magic :) ");
        //System.out.println("Response Code : " + responseCode);


        //GET INPUT STREAM FROM GET REQUEST
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //GET FORM FIELD NAME, GIVEN A SPECIFIC ID.
        JSONObject formIdJson = new JSONObject(response.toString());
        JSONArray recs = formIdJson.getJSONArray("elements");

        for (int i = 0; i < recs.length(); ++i) {

            JSONObject rec = recs.getJSONObject(i);

            String heritage = rec.getString("isStandard");
            String id = rec.getString("id");

            String dataType = rec.getString("dataType").toUpperCase();
            String AccountFieldNames = rec.getString("name");


            if (heritage.equals("true")) {

                HashMap<String, String> AccountContactFields = new HashMap<>();
                AccountContactFields.put(AccountFieldNames, dataType);
                allStandardAccountFields.put(id, AccountContactFields);
                standardAccountFieldId.add(id);
                standardAccountFieldName.add(AccountFieldNames);

            } else {

                HashMap<String, String> customAccountFields = new HashMap<>();
                customAccountFields.put(AccountFieldNames, dataType);
                allCustomAccountFields.put(id, customAccountFields);
                customAccountFieldId.add(id);
                customAccountFieldName.add(AccountFieldNames);
            }

        }



    }

    public HashMap<String, HashMap<String, String>> getAllStandardAccountFields() {
        return allStandardAccountFields;
    }

    public HashMap<String, HashMap<String, String>> getAllCustomAccountFields() {
        return allCustomAccountFields;
    }

    public ArrayList<String> getCustomAccountFieldId() {

        return customAccountFieldId;
    }

    public ArrayList<String> getCustomAccountFieldName() {
        return customAccountFieldName;
    }

    public ArrayList<String> getStandardAccountFieldId() {

        return standardAccountFieldId;
    }

    public ArrayList<String> getStandardAccountFieldName() {
        return standardAccountFieldName;
    }
}


