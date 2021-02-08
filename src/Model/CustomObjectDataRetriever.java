package Model;

import Controller.Authenticator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

@SuppressWarnings("unchecked Duplicates")

public class CustomObjectDataRetriever {

    private Authenticator authenticator;

    private HashMap<String,String> customObjectData = new HashMap<>();

    private int customDataObjectRecords;

    public HashMap<String,String> retrieveCustomObjectData(String id) throws Exception {


        //URL TO GET CUSTOM OBJECT DATA.
        String url = "https://secure.p06.eloqua.com//api/REST/2.0/assets/customObject/" + id + "?depth=complete";

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
        JSONArray recs = customObjectJson.getJSONArray("fields");
        customDataObjectRecords = recs.length();

        for (int i = 0; i < recs.length(); ++i) {

            JSONObject rec = recs.getJSONObject(i);

            String fieldName = rec.getString("name");
            String fieldDataType = rec.getString("dataType").toUpperCase();


            customObjectData.put(fieldName,fieldDataType);
        }


        return customObjectData;

    }

    public int getCustomDataObjectRecords() {
        return customDataObjectRecords;
    }

    public void setCustomDataObjectRecords(int customDataObjectRecords) {

    }
}


