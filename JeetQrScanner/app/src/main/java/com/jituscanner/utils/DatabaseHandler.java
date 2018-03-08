package com.jituscanner.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeetendra.achtani on 28-02-2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 6;


    // Database Name
    private static final String DATABASE_NAME = "db_QrScanner";


    // Contacts table name
    private static final String TABLE_DETAILS = "tbl_QrScanner";


    // QRScanner Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_TIME = "time";
    private static final String KEY_DETAIL = "detail";
    private static final String KEY_ORGANIZATION = "organization";
    private static final String KEY_CELL = "cell";
    private static final String KEY_URL = "url";
    private static final String KEY_IMG = "img";
    private static final String KEY_FAX = "fax";
    private static final String KEY_TYPE = "type";

    //SMS
    private static final String KEY_SMSSEND = "smssend";
    private static final String KEY_SMSPHONENO = "smsphoneno";

    //EMail
    private static final String KEY_EMAIL_TO = "emailto";
    private static final String KEY_EMAIL_SUB = "emailsub";
    private static final String KEY_EMAIL_BODY = "emailbody";



   /* public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DETAILS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_DETAIL + " TEXT,"
                + KEY_ORGANIZATION + " TEXT,"
                + KEY_CELL + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_IMG + " TEXT,"
                + KEY_FAX + " TEXT,"

                + KEY_TYPE + " TEXT," +

                //SMS
                KEY_SMSSEND + " TEXT," +
                KEY_SMSPHONENO + " TEXT," +

                //EMAIL
                KEY_EMAIL_TO + " TEXT," +
                KEY_EMAIL_SUB + " TEXT," +
                KEY_EMAIL_BODY + " TEXT" +


                ")";


        sqLiteDatabase.execSQL(CREATE_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);

        // Create tables again
        onCreate(sqLiteDatabase);

    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addDetails(Details contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_DETAIL, contact.getDetail()); //raw data
        values.put(KEY_TYPE, contact.getType());//for bifercation type


        //Contact
        values.put(KEY_NAME, contact.getName()); //  Name
        values.put(KEY_PH_NO, contact.getPhone_number()); // Phone
        values.put(KEY_EMAIL, contact.getEmail()); // Email
        values.put(KEY_ADDRESS, contact.getAddress()); // Email
        values.put(KEY_TIME, contact.getTime()); // Email
        values.put(KEY_ORGANIZATION, contact.getOrganization()); // Email
        values.put(KEY_CELL, contact.getCell()); // Email
        values.put(KEY_URL, contact.getURL()); // Email
        values.put(KEY_IMG, contact.getImg()); // Email
        values.put(KEY_FAX, contact.getFax()); // Email// Email


        //SMS
        values.put(KEY_SMSSEND, contact.getSMSMESSAGE());// Email
        values.put(KEY_SMSPHONENO, contact.getSMSPHONENO());// Email

        //EMAIL
        values.put(KEY_EMAIL_TO, contact.getEMAIL_TO());// Email to
        values.put(KEY_EMAIL_SUB, contact.getEMAIL_SUB());// Email sub
        values.put(KEY_EMAIL_BODY, contact.getEMAIL_BODY());// Email body

        // Inserting Row
        db.insert(TABLE_DETAILS, null, values);
        db.close(); // Closing database connection
    }

    // Getting(Reading single contact
    public Details getDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DETAILS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO,KEY_EMAIL,KEY_ADDRESS,KEY_TIME,KEY_DETAIL,KEY_ORGANIZATION,KEY_CELL,
                KEY_URL,KEY_IMG,KEY_FAX,KEY_TYPE,KEY_SMSSEND,KEY_EMAIL_TO,KEY_EMAIL_SUB,KEY_EMAIL_BODY}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Details details = new Details(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),
                cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),
                cursor.getString(12),cursor.getString(13),cursor.getString(14),
                cursor.getString(15),cursor.getString(16),cursor.getString(17));

        // return contact
        return details;
    }

    // Getting All Details
    public List<Details> getAllDetails() {
        List<Details> detailsArrayList = new ArrayList<Details>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Details details = new Details();
                details.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                details.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
                details.setDetail(cursor.getString(cursor.getColumnIndex(KEY_DETAIL)));

                //CONTACT
                details.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                details.setPhone_number(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
                details.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                details.setImg(cursor.getString(cursor.getColumnIndex(KEY_IMG)));
                details.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                details.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
                details.setOrganization(cursor.getString(cursor.getColumnIndex(KEY_ORGANIZATION)));
                details.setCell(cursor.getString(cursor.getColumnIndex(KEY_CELL)));
                details.setURL(cursor.getString(cursor.getColumnIndex(KEY_URL)));
                details.setFax(cursor.getString(cursor.getColumnIndex(KEY_FAX)));


                //SMS
                details.setSMSMESSAGE(cursor.getString(cursor.getColumnIndex(KEY_SMSSEND)));
                details.setSMSPHONENO(cursor.getString(cursor.getColumnIndex(KEY_SMSPHONENO)));

                //EMail
                details.setEMAIL_TO(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_TO)));
                details.setEMAIL_SUB(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_SUB)));
                details.setEMAIL_BODY(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_BODY)));
                // Adding details to list
                detailsArrayList.add(details);
            } while (cursor.moveToNext());
        }


        // return details list
        return detailsArrayList;
    }

    // Deleting single contact
    public void deleteContact(int ContactID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_DETAILS, KEY_ID + " = ?",
                    new String[]{String.valueOf(ContactID)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
