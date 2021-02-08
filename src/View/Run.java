package View;

import Controller.Authenticator;
import Controller.CSVCreator;
import Model.*;

public class Run {

    public static void main(String[] args) throws Exception {

       // ContactFieldRetriever retriever = new ContactFieldRetriever();
       // retriever.getContactFields();


       //System.out.println(customObjectNames.get(customObjectId.get(20)));
        //System.out.println(customObjectFields.get(customObjectId.get(20)));

       CSVCreator csvCreator = new CSVCreator();
       csvCreator.createCsv();

        //CustomObjectDataRetriever dRetriever = new CustomObjectDataRetriever();

        //CustomObjectHandler ch = new CustomObjectHandler();
       // System.out.println(ch.checkCustomObjectRecord("11"));
        //System.out.println(ch.getRecordCount("11"));
       //System.out.println(retriever.getStandardContactFields());

        //ContactFieldHandler cFh = new ContactFieldHandler();
        //System.out.println(cFh.checkContactField("100041"));

        //AccountFieldHandler aFh  = new AccountFieldHandler();
        //System.out.println(aFh.checkAccountField("100085"));

    }
}
