package com.example.a3_a_cruddypizza;
import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBAdapter {

    private DatabaseHelper DBHelper;
    //db helper inner class
    public static class DatabaseHelper extends SQLiteOpenHelper {


        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //creates adapter
        @Override
        public void onCreate(SQLiteDatabase db) {
            //create the tables
            try {
                db.execSQL(DB_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //upgrade db version
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //notify user of upgrade
            Log.w(TAG, "Upgraded db from version: " + oldVersion + " to: " + newVersion
                    + "dropping all old data.");
            String query = "DROP TABLE IF EXISTS customer, orders, pizza_details;";
            db.execSQL(query);
            //create new db
            onCreate(db);

        }//end upgrade

    }

    //db object
    private SQLiteDatabase db;

    private Context context;
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "CruddyPizzaDB";

    //sql text variables;
    public static final String CUSTOMER_TABLE = "customers";
    public static final String CUSTOMER_ID_PK = "customer_id";
    public static final String CUSTOMER_NAME  = "customer_name";
    public static final String ORDER_TABLE    = "orders";
    public static final String ORDER_ID_PK    = "id";
    public static final String ORDER_DATE     = "order_date";
    public static final String PIZZA_ID_PK    = "pizza_id";
    public static final String PIZZA_TABLE    = "pizza_info";
    public static final String PIZZA_SIZE     = "pizza_size";
    public static final String TOPPING_ONE    = "topping_one";
    public static final String TOPPING_TWO    = "topping_two";
    public static final String TOPPING_THREE  = "topping_three";

    //db create statement
    private static final String DB_CREATE =
            "CREATE TABLE \"customers\" (\n" +
                    "\t\"customer_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"login\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"customer_id\" AUTOINCREMENT)\n" +
                    ")"
                    +
                    "CREATE TABLE \"orders\" (\n" +
                    "\t\"order_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"pizza_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"order_date\"\tTEXT NOT NULL,\n" +
                    "\t\"customer_id\"\tINTEGER NOT NULL,\n" +
                    "\tUNIQUE(\"pizza_id\"),\n" +
                    "\tPRIMARY KEY(\"order_id\"),\n" +
                    "\tFOREIGN KEY(\"customer_id\") REFERENCES \"Customer\",\n" +
                    "\tFOREIGN KEY(\"pizza_id\") REFERENCES \"pizza_info\"(\"pizza_id\")\n" +
                    ")"
                    +
                    "CREATE TABLE \"pizza_info\" (\n" +
                    "\t\"pizza_id\"\tINTEGER,\n" +
                    "\t\"pizza_size\"\tINTEGER NOT NULL,\n" +
                    "\t\"topping_one\"\tINTEGER NOT NULL,\n" +
                    "\t\"toping_two\"\tINTEGER NOT NULL,\n" +
                    "\t\"topping_three\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"pizza_id\" AUTOINCREMENT)\n" +
                    ")";


    public DBAdapter(@Nullable Context context) {
        this.context = context;
        this.DBHelper = new DatabaseHelper(context);
    }


        //db adapter functions
        //open db
        public DBAdapter open() throws SQLException {
            db = DBHelper.getWritableDatabase();
            return this;
        }

        //close db
        public void close() {
            DBHelper.close();
        }


        //insert a single customer
        public long insertCustomer(String name) {
            ContentValues customerInfo = new ContentValues();
            customerInfo.put(CUSTOMER_NAME, name);

            return db.insert(CUSTOMER_TABLE, null, customerInfo);
        }

        public Cursor getCustomer(String customerLogin) throws SQLException{
            Cursor queryResult = db.query(true, CUSTOMER_TABLE, new String[]{CUSTOMER_NAME},
                                               CUSTOMER_NAME+customerLogin, null, null,
                                            null,null,null,null);


            return validCursor(queryResult);
        }

        //delete a single customer
        public boolean deleteCustomer(long customerID) {
            //returns true if statement returns at least 1 row deleted
            return db.delete(CUSTOMER_TABLE, CUSTOMER_ID_PK + "=" + customerID, null) > 0;
        }

        //insert a single order
        public long insertOrder(String orderDate) {
            ContentValues orderInfo = new ContentValues();
            orderInfo.put(ORDER_DATE, orderDate);

            return db.insert(ORDER_TABLE, null, orderInfo);
        }

        //select a single order
        public Cursor getOrder(long orderID) throws SQLException{
            Cursor queryResult = db.query(true, ORDER_TABLE, new String[]{
                    ORDER_ID_PK, ORDER_DATE}, ORDER_ID_PK+"="+orderID, null,
                    null,null,null,null,null);


            return validCursor(queryResult);
        }

        //delete single order
        public boolean deleteOrder(long orderID) {
            return db.delete(ORDER_TABLE, ORDER_ID_PK + "=" + orderID, null) > 0;
        }


        //insert single pizza
        public long
        insertPizza(int pizzaSize, int toppingOne, int toppingTwo, int toppingThree) {
            ContentValues pizzaInfo = new ContentValues();

            pizzaInfo.put(PIZZA_SIZE, pizzaSize);
            pizzaInfo.put(TOPPING_ONE, toppingOne);
            pizzaInfo.put(TOPPING_TWO, toppingTwo);
            pizzaInfo.put(TOPPING_THREE, toppingThree);

            return db.insert(PIZZA_TABLE, null, pizzaInfo);
        }

        //delete single pizza
        public boolean deletePizza(long pizzaID) {
            return db.delete(PIZZA_TABLE, PIZZA_ID_PK + "=" + pizzaID, null) > 0;
        }

        //pizza info
        public Cursor getPizzaInfo(long pizzaID) throws SQLException {
            Cursor queryResult = db.query(true, PIZZA_TABLE, new String[]{PIZZA_SIZE, TOPPING_ONE,
                            TOPPING_TWO, TOPPING_THREE}, PIZZA_ID_PK + "=" + pizzaID,
                    null, null, null, null, null);


            return validCursor(queryResult);
        }

        public boolean
        updatePizza(int pizzaID, int pizzaSize, int toppingOne, int toppingTwo, int toppingThree) {
            ContentValues newPizzaInfo = new ContentValues();
            newPizzaInfo.put(PIZZA_SIZE, pizzaSize);
            newPizzaInfo.put(TOPPING_ONE, toppingOne);
            newPizzaInfo.put(TOPPING_TWO, toppingTwo);
            newPizzaInfo.put(TOPPING_THREE, toppingThree);

            return db.update(PIZZA_TABLE, newPizzaInfo, PIZZA_ID_PK + "=" + pizzaID, null) > 0;
        }


        //retrieve all orders
        public Cursor getAllOrders(long orderID) throws SQLException {

            Cursor queryResult =
                    db.query(true, ORDER_TABLE + " JOIN " + PIZZA_TABLE + " ON " +
                                    ORDER_TABLE + "." + PIZZA_ID_PK + " = " + PIZZA_TABLE + "." + PIZZA_ID_PK,
                            new String[]{
                                    ORDER_TABLE + "." + ORDER_ID_PK,
                                    ORDER_DATE,
                                    PIZZA_TABLE + "." + PIZZA_ID_PK,
                                    PIZZA_SIZE
                            },
                            ORDER_ID_PK + "=" + orderID, null, null, null,
                            null, null, null);



            //return orders
            return validCursor(queryResult);
        }

        public Cursor validCursor(Cursor cursorToCheck){
        //keeps from repeating code
            if (cursorToCheck != null){
                cursorToCheck.moveToFirst();
            }
            return cursorToCheck;
        }

}
