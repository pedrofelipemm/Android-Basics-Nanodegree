package study.pmoreira.project9.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import study.pmoreira.project9.R;
import study.pmoreira.project9.data.InventoryContract.ItemEntry;

@SuppressWarnings("ConstantConditions")
public class InventoryProvider extends ContentProvider {

    private static final String TAG = InventoryProvider.class.getSimpleName();

    private static final int ITEMS = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_ITEMS, ITEMS);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_ITEMS + "/#", ITEM_ID);
    }

    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String
            sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                cursor = db.query(ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.error_unknown_uri, uri));
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException(
                        getContext().getString(R.string.error_inventory_provider_operation_not_supported, "Insert", uri));
        }
    }

    private Uri insertItem(Uri uri, ContentValues values) {
        validateItem(values);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long rowId = db.insert(ItemEntry.TABLE_NAME, null, values);

        if (rowId == -1) {
            Log.e(TAG, getContext().getString(R.string.error_insertion_failed, uri));
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, rowId);
    }

    private void validateItem(ContentValues values) {
        String name = values.getAsString(ItemEntry.COLUMN_ITEM_NAME);
        if (TextUtils.isEmpty(name.trim())) {
            throw new IllegalArgumentException(
                    getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_NAME));
        }

        Double price = values.getAsDouble(ItemEntry.COLUMN_ITEM_PRICE);
        if (price == null || price < 0) {
            throw new IllegalArgumentException(
                    getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_PRICE));
        }

        Integer quantity = values.getAsInteger(ItemEntry.COLUMN_ITEM_QUANTITY);
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException(
                    getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_QUANTITY));
        }

        byte[] image = values.getAsByteArray(ItemEntry.COLUMN_ITEM_IMAGE);
        if (image == null || image.length < 1) {
            throw new IllegalArgumentException(
                    getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_IMAGE));
        }

        String supplier = values.getAsString(ItemEntry.COLUMN_ITEM_SUPPLIER);
        if (TextUtils.isEmpty(supplier.trim())) {
            throw new IllegalArgumentException(
                    getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_SUPPLIER));
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return updateItem(uri, contentValues, selection, selectionArgs);
            case ITEM_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(
                        getContext().getString(R.string.error_inventory_provider_operation_not_supported, "Update", uri));
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.size() == 0) {
            return 0;
        }

        validateUpdate(values);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(ItemEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    private void validateUpdate(ContentValues values) {
        if (values.containsKey(ItemEntry.COLUMN_ITEM_NAME)) {
            String name = values.getAsString(ItemEntry.COLUMN_ITEM_NAME);
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException(
                        getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_NAME));
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_SUPPLIER)) {
            String supplier = values.getAsString(ItemEntry.COLUMN_ITEM_SUPPLIER);
            if (TextUtils.isEmpty(supplier)) {
                throw new IllegalArgumentException(
                        getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_SUPPLIER));
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_PRICE)) {
            Double price = values.getAsDouble(ItemEntry.COLUMN_ITEM_PRICE);
            if (price == null || price < 0) {
                throw new IllegalArgumentException(
                        getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_PRICE));
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_QUANTITY)) {
            Integer quantity = values.getAsInteger(ItemEntry.COLUMN_ITEM_QUANTITY);
            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException(
                        getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_QUANTITY));
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_IMAGE)) {
            byte[] image = values.getAsByteArray(ItemEntry.COLUMN_ITEM_IMAGE);
            if (image == null || image.length < 1) {
                throw new IllegalArgumentException(
                        getContext().getString(R.string.field_required, ItemEntry.COLUMN_ITEM_IMAGE));
            }
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                rowsDeleted = db.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(
                        getContext().getString(R.string.error_inventory_provider_operation_not_supported, "Deletion", uri));
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return ItemEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException(getContext().getString(R.string.unknows_uri_match, uri, match));
        }
    }

}
