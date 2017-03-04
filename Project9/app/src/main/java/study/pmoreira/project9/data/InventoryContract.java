package study.pmoreira.project9.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    static final String CONTENT_AUTHORITY = "study.pmoreira.project9";
    static final String PATH_ITEMS = "items";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private InventoryContract() {
    }

    public static final class ItemEntry implements BaseColumns {

        static final String TABLE_NAME = "items";

        static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        public static final String COLUMN_ITEM_NAME = "name";
        public static final String COLUMN_ITEM_IMAGE = "image";
        public static final String COLUMN_ITEM_PRICE = "price";
        public static final String COLUMN_ITEM_SUPPLIER = "supplier";
        public static final String COLUMN_ITEM_QUANTITY = "quantity";

    }

}
