package Controller;

import Model.*;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


@SuppressWarnings("Duplicates")
public class CSVCreator {


    //GLOBAL VARIABLES

    private CustomObjectHandler cOh;
    private ContactFieldHandler cFh;
    private AccountFieldHandler aFh;

    private AccountFieldRetriever accRetriever;
    private ContactFieldRetriever conRetriever;
    private CustomObjectRetriever cRetriever;


    private ArrayList<String> standardAccountFieldName;
    private ArrayList<String> standardAccountFieldId;

    private ArrayList<String> standardContactFieldName;
    private ArrayList<String> standardContactFieldId;

    private ArrayList<String> customAccountFieldName;
    private ArrayList<String> customAccountFieldId;

    private ArrayList<String> customContactFieldName;
    private ArrayList<String> customContactFieldId;


    private  HashMap<String, String> customObjectName;
    private ArrayList<String> customObjectId;



    private void initialize() throws Exception {

        //INITIALIZING GLOBAL VARIABLES
        //CUSTOM OBJECT HANDLER
        cOh = new CustomObjectHandler();

        //CONTACT FIELD HANDLER
        cFh = new ContactFieldHandler();

        //ACCOUNT FIELD HANDLER
        aFh  = new AccountFieldHandler();

        //ACCOUNT FIELDS
        this.accRetriever = new AccountFieldRetriever();
        accRetriever.getContactFields();

        //STANDARD
        standardAccountFieldName = accRetriever.getStandardAccountFieldName();
        standardAccountFieldId  = accRetriever.getStandardAccountFieldId();
        //CUSTOM
        customAccountFieldId = accRetriever.getCustomAccountFieldId();
        customAccountFieldName = accRetriever.getCustomAccountFieldName();



        //CONTACT FIELDS
        this.conRetriever = new ContactFieldRetriever();
        conRetriever.getContactFields();

        //STANDARD
        standardContactFieldName = conRetriever.getStandardContactFieldName();
        standardContactFieldId   = conRetriever.getStandardContactFieldId();
        //CUSTOM
        customContactFieldName = conRetriever.getCustomContactFieldName();
        customContactFieldId = conRetriever.getCustomContactFieldId();




        //CUSTOM OBJECTS
        this.cRetriever = new CustomObjectRetriever();
        cRetriever.getAllCustomObjectFields();


        customObjectName = cRetriever.getCustomObjectNames();
        customObjectId = cRetriever.getCustomObjectIds();

    }


    public void createCsv() throws Exception {

        initialize();

        HashMap<String, HashMap<String, String>> standardFields;
        HashMap<String, HashMap<String, String>> customFields;

        HashMap<String, HashMap<String, String>> accStandardFields;
        HashMap<String, HashMap<String, String>> accCustomFields;


        accStandardFields = accRetriever.getAllStandardAccountFields();
        accCustomFields = accRetriever.getAllCustomAccountFields();

        standardFields = conRetriever.getAllStandardContactFields();
        customFields = conRetriever.getAllCustomContactFields();

        customObjectName = cRetriever.getCustomObjectNames();
        customObjectId = cRetriever.getCustomObjectIds();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        String dateStamp = sdf.format(date);
        String companyName = "LCM";

        String fileName = "C:\\Users\\clootvo\\Desktop\\" + dateStamp + companyName + "_DATA_MODEL.xls";

        WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));

        WritableSheet sheet = workbook.createSheet("Standard Account Fields", 0);
        WritableSheet sheet2 = workbook.createSheet("Custom Account Fields", 1);
        WritableSheet sheet3 = workbook.createSheet("Standard Contact Fields", 2);
        WritableSheet sheet4 = workbook.createSheet("Custom Contact Fields", 3);

        Label header_1 = new Label(0, 0, "Account field name");
        Label header_2 = new Label(1, 0, "Data Type");
        Label header_3 = new Label(2, 0, "Max. Length / Format");
        Label header_4 = new Label(3, 0, "In use?");


        Label header_1_1 = new Label(0, 0, "Account field name");
        Label header_2_1 = new Label(1, 0, "Data Type");
        Label header_3_1 = new Label(2, 0, "Max. Length / Format");
        Label header_4_1 = new Label(3, 0, "In use?");

        Label header_1_2 = new Label(0, 0, " Contact field name");
        Label header_2_2 = new Label(1, 0, "Data Type");
        Label header_3_2 = new Label(2, 0, "Max. Length / Format");
        Label header_4_2 = new Label(3, 0, "In use?");

        Label header_1_3 = new Label(0, 0, "Contact field name");
        Label header_2_3 = new Label(1, 0, "Data Type");
        Label header_3_3 = new Label(2, 0, "Max. Length / Format");
        Label header_4_3 = new Label(3, 0, "In use?");


        sheet.addCell(header_1);
        sheet.addCell(header_2);
        sheet.addCell(header_3);
        sheet.addCell(header_4);

        sheet2.addCell(header_1_1);
        sheet2.addCell(header_2_1);
        sheet2.addCell(header_3_1);
        sheet2.addCell(header_4_1);

        sheet3.addCell(header_1_2);
        sheet3.addCell(header_2_2);
        sheet3.addCell(header_3_2);
        sheet3.addCell(header_4_2);

        sheet4.addCell(header_1_3);
        sheet4.addCell(header_2_3);
        sheet4.addCell(header_3_3);
        sheet4.addCell(header_4_3);

        //STANDARD ACCOUNT FIELDS
        for (int i = 0; i < accStandardFields.size(); i++) {

            if(aFh.checkAccountField(standardAccountFieldId.get(i))) {

                Label sAfInUse;
                sAfInUse = new Label(3, i + 1, "Y");
                sheet.addCell( sAfInUse);
            }

            else{

                Label sAfInUse;
                sAfInUse = new Label(3, i + 1, "N");
                sheet.addCell( sAfInUse);
            }


            Label sAccountName;
            sAccountName = new Label(0, i+1, standardAccountFieldName.get(i));
            sheet.addCell(sAccountName);

            Label sAccountDataType;
            sAccountDataType = new Label(1,i+1, accStandardFields.get(standardAccountFieldId.get(i)).get(standardAccountFieldName.get(i)));
            sheet.addCell(sAccountDataType);

            if(accStandardFields.get(standardAccountFieldId.get(i)).get(standardAccountFieldName.get(i)).equals("DATE")){

                Label sAccountFieldFormat;
                sAccountFieldFormat = new Label(2,i+1, "MM-DD-YYYY  HH:24:MM:SS");
                sheet.addCell( sAccountFieldFormat);
            }

            else{

                Label sAccountFieldFormat;
                sAccountFieldFormat = new Label(2,i+1, "100");
                sheet.addCell(sAccountFieldFormat); }


        }

        //END STANDARD ACCOUNT FIELDS


        //CUSTOM ACCOUNT FIELDS

        for (int i = 0; i < accCustomFields.size(); i++) {

            if(aFh.checkAccountField(customAccountFieldId.get(i))) {

                Label cAfInUse;
                cAfInUse = new Label(3, i + 1, "Y");
                sheet2.addCell( cAfInUse);
            }

            else{

                Label cAfInUse;
                cAfInUse = new Label(3, i + 1, "N");
                sheet2.addCell( cAfInUse);
            }

            Label accCustomFieldName;
            accCustomFieldName = new Label(0, i + 1, customAccountFieldName.get(i));
            sheet2.addCell(accCustomFieldName);

            Label accCustomFieldDataType;
            accCustomFieldDataType = new Label(1, i + 1, accCustomFields.get(customAccountFieldId.get(i)).get(customAccountFieldName.get(i)));
            sheet2.addCell(accCustomFieldDataType);

            if (accCustomFields.get(customAccountFieldId.get(i)).get(customAccountFieldName.get(i)).equals("DATE")) {

                Label cAccountFieldFormat;
                cAccountFieldFormat = new Label(2, i + 1, "MM-DD-YYYY  HH:24:MM:SS");
                sheet2.addCell(cAccountFieldFormat);
            } else {

                Label cAccountFieldFormat;
                cAccountFieldFormat = new Label(2, i + 1, "100");
                sheet2.addCell(cAccountFieldFormat);
            }


            if (accCustomFields.get(customAccountFieldId.get(i)).get(customAccountFieldName.get(i)).equals("LARGETEXT")) {

                Label cAccountFieldFormat;
                cAccountFieldFormat = new Label(2, i + 1, "32000");
                sheet2.addCell(cAccountFieldFormat);

            }
        }

        //STANDARD CONTACT FIELDS
        for (int i = 0; i < standardFields.size(); i++) {

            if(cFh.checkContactField(standardContactFieldId.get(i))) {

                Label sCfInUse;
                sCfInUse = new Label(3, i + 1, "Y");
                sheet3.addCell( sCfInUse);
            }

            else{

                Label sCfInUse;
                sCfInUse = new Label(3, i + 1, "N");
                sheet3.addCell( sCfInUse);
            }


            Label sContactFieldName;
            sContactFieldName = new Label(0, i+1, standardContactFieldName.get(i));
            sheet3.addCell(sContactFieldName);

            Label sContactFieldDataType;
            sContactFieldDataType = new Label(1,i+1, standardFields.get(standardContactFieldId.get(i)).get(standardContactFieldName.get(i)));
            sheet3.addCell(sContactFieldDataType);


            if(standardFields.get(standardContactFieldId.get(i)).get(standardContactFieldName.get(i)).equals("DATE")){

                Label sContactFieldFormat;
                sContactFieldFormat = new Label(2,i+1, "MM-DD-YYYY  HH:24:MM:SS");
                sheet3.addCell( sContactFieldFormat);
            }

            else{

                Label sContactFieldFormat;
                sContactFieldFormat = new Label(2,i+1, "100");
                sheet3.addCell(sContactFieldFormat); }

        }

        //END STANDARD CONTACT FIELDS

        //CUSTOM CONTACT FIELDS

        for (int i = 0; i < customFields.size(); i++) {

            if(cFh.checkContactField(customContactFieldId.get(i))) {

                Label cCfInUse;
                cCfInUse = new Label(3, i + 1, "Y");
                sheet4.addCell( cCfInUse);
            }

            else{

                Label cCfInUse;
                cCfInUse = new Label(3, i + 1, "N");
                sheet4.addCell( cCfInUse);
            }

            Label cContactFieldName;
            cContactFieldName = new Label(0, i + 1, customContactFieldName.get(i));
            sheet4.addCell(cContactFieldName);

            Label cContactFieldDataType;
            cContactFieldDataType = new Label(1, i + 1, customFields.get(customContactFieldId.get(i)).get(customContactFieldName.get(i)));
            sheet4.addCell(cContactFieldDataType);

            if (customFields.get(customContactFieldId.get(i)).get(customContactFieldName.get(i)).equals("DATE")) {

                Label cContactFieldFormat;
                cContactFieldFormat = new Label(2, i + 1, "MM-DD-YYYY  HH:24:MM:SS");
                sheet4.addCell(cContactFieldFormat);
            } else {

                Label cContactFieldFormat;
                cContactFieldFormat = new Label(2, i + 1, "100");
                sheet4.addCell(cContactFieldFormat);
            }


            if (customFields.get(customContactFieldId.get(i)).get(customContactFieldName.get(i)).equals("LARGETEXT")) {

                Label cContactFieldFormat;
                cContactFieldFormat = new Label(2, i + 1, "32000");
                sheet4.addCell(cContactFieldFormat);

            }
        }




        ArrayList<WritableSheet> sheetCount = new ArrayList<>(customObjectId.size());

        int j = 4;
        //CREATE CUSTOM OBJECT SHEET AND ADD TO EXCEL FILE
        for (String aCustomObjectId : customObjectId) {

            WritableSheet dynamicSheet = workbook.createSheet(customObjectName.get(aCustomObjectId), j);
           // WritableSheet dynamicSheet = workbook.createSheet("Test", j);
            sheetCount.add(dynamicSheet);
            j++;

        }


       for(int i = 0; i < sheetCount.size(); i ++) {

           int k = 0;

           Label headerOne = new Label(0, 2, "Field name");
           Label headerTwo = new Label(1, 2, "Data Type");
           Label headerThree = new Label(2, 2, "Max. Length / Format");
           Label headerFour = new Label(0, 0, "Record count:");
           Label headerFive = new Label(3, 2, "CDO In use?");

           sheetCount.get(i).addCell(headerOne);
           sheetCount.get(i).addCell(headerTwo);
           sheetCount.get(i).addCell(headerThree);
           sheetCount.get(i).addCell(headerFour);
           sheetCount.get(i).addCell(headerFive);

           HashMap<String, String> customObjectData;

           //ADD CUSTOM OBJECT DATA TO RELATED SHEET

           CustomObjectDataRetriever customObjectDataRetriever = new CustomObjectDataRetriever();
           customObjectData = customObjectDataRetriever.retrieveCustomObjectData(customObjectId.get(i));

           for (String keys : customObjectData.keySet()) {

               Label customObjectFieldName;
               customObjectFieldName = new Label(0, k + 3, keys);
               sheetCount.get(i).addCell(customObjectFieldName);
               k++;
           }


           //RECORD COUNT
           Label recordCount;
           recordCount = new Label(1,0, cOh.getRecordCount(customObjectId.get(i)));
           sheetCount.get(i).addCell(recordCount);

           // IN USE?
           if (cOh.checkCustomObjectRecord(customObjectId.get(i))) {
               Label inUse;
               inUse = new Label(4, 2, "YES");
               sheetCount.get(i).addCell(inUse);

           } else {

               Label inUse;
               inUse = new Label(4, 2, "NO");
               sheetCount.get(i).addCell(inUse);
           }


           //   DATA TYPE
           int p = 0;

           for (String v : customObjectData.values()) {

               Label customObjectFieldDataType;
               customObjectFieldDataType = new Label(1, p + 3, v);
               sheetCount.get(i).addCell(customObjectFieldDataType);

            //   LENGTH
               if (v.equals("DATE")) {

                   Label format;
                   format = new Label(2, p + 3, "MM-DD-YYYY");
                   sheetCount.get(i).addCell(format);


               } else {

                   Label format;
                   format = new Label(2, p + 3, "250");
                   sheetCount.get(i).addCell(format);

               }

               if (v.equals("LARGETEXT")) {

                   Label format;
                   format = new Label(2, p + 3, "32000");
                   sheetCount.get(i).addCell(format);

               }

               p++;

           }



       }
       workbook.write();
       workbook.close();

        }



}







