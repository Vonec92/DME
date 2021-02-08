package Model;

import Controller.Authenticator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

/**
 * Author: Vone Cloots
 **/

@SuppressWarnings("unchecked Duplicates")

public class ContactFieldRetriever {

    //private HashMap<String, HashMap<String, String>> allFieldsData = new HashMap<>();
    // private HashMap<String, String> ContactFieldNames = new HashMap<>();

    private Authenticator authenticator;

    private HashMap<String, HashMap<String, String>> allStandardContactFields = new HashMap<>();
    private HashMap<String, HashMap<String, String>> allCustomContactFields = new HashMap<>();

    private ArrayList<String> standardContactFieldId = new ArrayList<>();
    private ArrayList<String> standardContactFieldName = new ArrayList();

    private ArrayList<String> customContactFieldId = new ArrayList<>();
    private ArrayList<String> customContactFieldName = new ArrayList();


    public void getContactFields() throws Exception {

        //URL TO GET ALL CONTACT FIELDS.
        String url = "https://secure.p06.eloqua.com/api/REST/1.0/assets/contact/fields?depth=complete";

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

        //RESPONSE CODE  200-> Success
        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
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
            String contactFieldNames = rec.getString("name");


            if (heritage.equals("true")) {

                HashMap<String, String> standardContactFields = new HashMap<>();
                standardContactFields.put(contactFieldNames, dataType);
                allStandardContactFields.put(id, standardContactFields);
                standardContactFieldId.add(id);
                standardContactFieldName.add(contactFieldNames);

            } else {

                HashMap<String, String> customContactFields = new HashMap<>();
                customContactFields.put(contactFieldNames, dataType);
                allCustomContactFields.put(id, customContactFields);
                customContactFieldId.add(id);
                customContactFieldName.add(contactFieldNames);
            }

        }


    }

    public HashMap<String, HashMap<String, String>> getAllStandardContactFields() {
        return allStandardContactFields;
    }

    public HashMap<String, HashMap<String, String>> getAllCustomContactFields() {
        return allCustomContactFields;
    }

    public ArrayList<String> getCustomContactFieldId() {

        return customContactFieldId;
    }

    public ArrayList<String> getCustomContactFieldName() {
        return customContactFieldName;
    }

    public ArrayList<String> getStandardContactFieldId() {

        return standardContactFieldId;
    }

    public ArrayList<String> getStandardContactFieldName() {
        return standardContactFieldName;
    }
}





