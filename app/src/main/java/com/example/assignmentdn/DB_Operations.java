package com.example.assignmentdn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Operations extends SQLiteOpenHelper {


    public DB_Operations(@Nullable Context context) {
        super(context, "DogNutrition", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE tblUser(Username VARCHAR (20) PRIMARY KEY, Mobile INTEGER, Address VARCHAR, Password VARCHAR, Usertype VARCHAR)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE tblProduct(ProductID INTEGER PRIMARY KEY, ProductName VARCHAR, ProductPrice DOUBLE ,ProductBrand VARCHAR, ProductType VARCHAR, ProductAge VARCHAR, ProductDescription VARCHAR, ProductImage BLOG)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE tblPost (PostID INTEGER PRIMARY KEY AUTOINCREMENT, PostTitle VARCHAR, PostDescription VARCHAR, PostImage BLOB)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE tblCart (CartID INTEGER PRIMARY KEY AUTOINCREMENT, Username VARCHAR, ProductID INTEGER, Quantity INTEGER, ProductName VARCHAR)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE tblOrder (OrderID INTEGER PRIMARY KEY AUTOINCREMENT, Username VARCHAR, TotalPrice DOUBLE, OrderDetails TEXT)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS tblUser";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS tblProduct";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS tblPost";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS tblCart";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS tblOrder";
        db.execSQL(sql);
    }

    public void createUser(User user) {
        String sql = "INSERT INTO tblUser  VALUES('" + user.getUsername() + "'," + user.getMobile() + ",'" + user.getAddress() + "','" + user.getPassword() + "','" + user.getUsertype() + "'" +
                ")";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public User findUser(String username) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM tblUser WHERE Username = '" + username + "'", null);
        User user = new User();
        if (cursor.getCount() != 0) {

            cursor.moveToFirst();
            user.setUsername(cursor.getString(0));
            user.setMobile(cursor.getInt(1));
            user.setAddress(cursor.getString(2));
            user.setPassword(cursor.getString(3));


        } else {
            user = null;
        }
        return user;
    }

    public boolean  checkLogin(String useername, String password){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tblUser WHERE Username='"+useername+"' AND Password ="+password,null);

        if (cursor.getCount()==0){
            return false;
        }else {
            return true;
        }
    }

    public int UpdateUser(User user){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put("Mobile",user.getMobile());
        cv.put("Address",user.getAddress());
        cv.put("Password",user.getPassword());

        return database.update("tblUser",cv, "Username='"+user.getUsername()+"'",null);
    }


    public ArrayList<User> getAllUsers(){
        SQLiteDatabase database =getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tblUser",null);

        ArrayList<User> arrayList = new ArrayList<>();

        if (cursor.getCount()!=0){
            while (cursor.moveToNext()){
                User user = new User();
                user.setUsername(cursor.getString(0));
                user.setMobile(cursor.getInt(1));
                user.setAddress(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                arrayList.add(user);
            }
        }else {
            arrayList = null;
        }
        return arrayList;
    }

    public int deleteuser(String username){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        return database.delete("tblUser","Username='"+username+"'",null);
    }


    public void insertProduct(Product product){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ProductID",product.getpID());
        cv.put("ProductName",product.getpName());
        cv.put("ProductPrice",product.getpPrice());
        cv.put("ProductBrand",product.getpBrand());
        cv.put("ProductType",product.getpType());
        cv.put("ProductAge",product.getpAge());
        cv.put("ProductDescription",product.getpDescription());
        cv.put("ProductImage",product.getpImage());

        database.insert("tblProduct",null,cv);
    }

    public ArrayList<Product> ViewAllProduct(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM tblProduct";

        Cursor cursor = database.rawQuery(sql,null);

        ArrayList<Product> productArrayList = new ArrayList<>();


        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                Product product = new Product();

                product.setpID(cursor.getInt(0));
                product.setpName(cursor.getString(1));
                product.setpPrice(cursor.getDouble(2));
                product.setpBrand(cursor.getString(3));
                product.setpType(cursor.getString(4));
                product.setpAge(cursor.getInt(5));
                product.setpDescription(cursor.getString(6));
                product.setpImage(cursor.getBlob(7));

                productArrayList.add(product);

            }

        }else {
            productArrayList = null;
        }
        return productArrayList;
    }

    public void createPost(Post post){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PostTitle",post.geteTitle());
        cv.put("PostDescription",post.geteDescription());
        cv.put("PostImage",post.geteImage());

        database.insert("tblPost",null,cv);
    }

    public ArrayList<Post> ViewAllPost(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM tblPost";

        Cursor cursor = database.rawQuery(sql,null);

        ArrayList<Post> postArrayList = new ArrayList<>();


        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                Post post = new Post();

                post.seteTitle(cursor.getString(1));
                post.seteDescription(cursor.getString(2));
                post.seteImage(cursor.getBlob(3));

                postArrayList.add(post);

            }

        }else {
            postArrayList = null;
        }
        return postArrayList;
    }

    public ArrayList<Product> SimpleViewAllProduct(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM tblProduct";

        Cursor cursor = database.rawQuery(sql, null);

        ArrayList<Product> productArrayList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Product product = new Product();

                product.setpID(cursor.getInt(0));
                product.setpName(cursor.getString(1));
                product.setpPrice(cursor.getDouble(2));
                product.setpBrand(cursor.getString(3));
                product.setpType(cursor.getString(4));
                product.setpAge(cursor.getInt(5));
                product.setpDescription(cursor.getString(6));
                product.setpImage(cursor.getBlob(7));

                productArrayList.add(product);
            }
        }
        cursor.close();
        database.close();
        return productArrayList;
    }



    public void addToCart(String username, int productID, int quantity, String productName ) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("ProductID", productID);
        cv.put("Quantity", quantity);
        cv.put("ProductName", productName);

        database.insert("tblCart", null, cv);
    }

    public ArrayList<Cart> ViewCart(String username) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM tblCart WHERE Username = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{username});

        ArrayList<Cart> cartArrayList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Cart cart = new Cart();
                cart.setCartID(cursor.getInt(0));
                cart.setUsername(cursor.getString(1));
                cart.setProductID(cursor.getInt(2));
                cart.setQuantity(cursor.getInt(3));
                cart.setProductName(cursor.getString(4));

                cartArrayList.add(cart);
            }
        }
        cursor.close();
        database.close();
        return cartArrayList;
    }

    public double getProductPriceById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ProductPrice FROM tblProduct WHERE ProductID = ?", new String[]{String.valueOf(productId)});

        double price = 0;
        if (cursor.moveToFirst()) {
            price = cursor.getDouble(0);
        }

        cursor.close();
        db.close();
        return price;
    }

    public void deleteCartItem(int cartID) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("tblCart", "CartID=?", new String[]{String.valueOf(cartID)});
        database.close();
    }

    public void createOrder(String username, double totalPrice, String orderDetails) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("TotalPrice", totalPrice);
        cv.put("OrderDetails", orderDetails);

        database.insert("tblOrder", null, cv);
    }

    public int deletePost(String postTitle) {
        SQLiteDatabase database = getWritableDatabase();
        int rowsAffected = database.delete("tblPost", "PostTitle=?", new String[]{postTitle});
        database.close();
        return rowsAffected;
    }

    public int deleteProduct(int productID) {
        SQLiteDatabase database = getWritableDatabase();
        int rowsAffected = database.delete("tblProduct", "ProductID=?", new String[]{String.valueOf(productID)});
        database.close();
        return rowsAffected;
    }

    public ArrayList<Product> getProductsByType(String type) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM tblProduct WHERE ProductType = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{type});

        ArrayList<Product> productArrayList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Product product = new Product();

                product.setpID(cursor.getInt(0));
                product.setpName(cursor.getString(1));
                product.setpPrice(cursor.getDouble(2));
                product.setpBrand(cursor.getString(3));
                product.setpType(cursor.getString(4));
                product.setpAge(cursor.getInt(5));
                product.setpDescription(cursor.getString(6));
                product.setpImage(cursor.getBlob(7));

                productArrayList.add(product);
            }
        }
        cursor.close();
        database.close();
        return productArrayList;
    }


    public ArrayList<String> getAllCategories() {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT DISTINCT ProductType FROM tblProduct";
        Cursor cursor = database.rawQuery(sql, null);

        ArrayList<String> categories = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                categories.add(cursor.getString(0));
            }
        }
        cursor.close();
        database.close();
        return categories;
    }

}
