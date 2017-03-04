package study.pmoreira.project9.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import study.pmoreira.project9.data.InventoryContract.ItemEntry;

class InventoryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventort.db";
    private static final int DATABASE_VERSION = 1;

    InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + ItemEntry.TABLE_NAME + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_IMAGE + " BLOB, "
                + ItemEntry.COLUMN_ITEM_SUPPLIER + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_PRICE + " REAL NOT NULL DEFAULT 0, "
                + ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
