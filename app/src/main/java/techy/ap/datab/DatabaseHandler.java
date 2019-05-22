package techy.ap.datab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Contactdb";
    private static final String DATABASE_TABLe="ContactTables";
    private static final int DATABASEVERSION=1;

    private static final String NAME="name";
    private static final String EMAIL="email";
    private static final String PHONE="phone";
    private static final String KEY_ID="id";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASEVERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="create Table "+DATABASE_TABLe + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," +EMAIL+ " TEXT,"+PHONE+" TEXT);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ DATABASE_TABLe);

    }

    public void insertdata(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,contact.getName());
        contentValues.put(EMAIL,contact.getEmail());
        contentValues.put(PHONE,contact.getPhone());
        db.insert(DATABASE_TABLe,null,contentValues);
        db.close();

        }

    public int updatedata(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,contact.getName());
        contentValues.put(EMAIL,contact.getEmail());
        contentValues.put(PHONE,contact.getPhone());
       return db.update(DATABASE_TABLe,contentValues,KEY_ID +"=?",new String[]{contact.getId()});


    }

    public void deletedata(Contact contact){
        SQLiteDatabase db=this.getReadableDatabase();
       db.delete(DATABASE_TABLe,KEY_ID+"=?",new String[]{contact.getId()});
       db.close();
    }


public ArrayList<Contact> getAllRecords(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM "+ DATABASE_TABLe,null);
        ArrayList<Contact>contacts=new ArrayList<Contact>();
        Contact contact;
        if(cursor.getCount()>0){
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToNext();
                contact=new Contact();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contact.setPhone(cursor.getString(3));
                contacts.add(contact);
            }
        }

        cursor.close();
        db.close();
        return contacts;
}


}
