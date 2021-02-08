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

@SuppressWarnings("unchecked Duplicates")
public class CustomObjectRetriever {

    private HashMap<String, HashMap<String, String>> allCustomObjectData = new HashMap<>();

    private HashMap<String, String> customObjectNames = new HashMap<>();
    private ArrayList<String> customObjectIds = new ArrayList<>();

    private Authenticator authenticator;

    public void getAllCustomObjectFields() throws Exception {

        //URL TO GET ALL CONTACT FIELDS.
        String url = "https://secure.p06.eloqua.com/api/REST/2.0/assets/customObjects";

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
        JSONObject customObjectId = new JSONObject(response.toString());
        JSONArray recs = customObjectId.getJSONArray("elements");

        for (int i = 0; i < recs.length(); ++i) {

            JSONObject rec = recs.getJSONObject(i);

            String customObjectName = rec.getString("name");
            String id = rec.getString("id");

            customObjectIds.add(id);
            customObjectNames.put(id, customObjectName);

        }


    }
    public HashMap<String, String> getCustomObjectNames() {
        return customObjectNames;
    }

    public ArrayList<String> getCustomObjectIds() {
        return customObjectIds;
    }

    public HashMap<String, HashMap<String, String>> getAllCustomObjectData() {
        return allCustomObjectData;
    }

}


