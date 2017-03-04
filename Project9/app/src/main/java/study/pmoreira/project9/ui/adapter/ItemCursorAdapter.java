package study.pmoreira.project9.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project9.R;
import study.pmoreira.project9.data.InventoryContract.ItemEntry;
import study.pmoreira.project9.utils.ImageUtils;

public class ItemCursorAdapter extends CursorAdapter {

    static class ViewHolder {

        @BindView(R.id.product_name_textview)
        TextView productNameTextView;

        @BindView(R.id.product_supplier_textview)
        TextView productSupplierTextView;

        @BindView(R.id.product_price_textview)
        TextView productPriceTextView;

        @BindView(R.id.product_quantity_textview)
        TextView productQuantityTextView;

        @BindView(R.id.product_imageview)
        ImageView productImageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public ItemCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_SUPPLIER);
        int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE);

        String name = cursor.getString(nameColumnIndex);
        String supplier = cursor.getString(supplierColumnIndex);
        Double price = cursor.getDouble(priceColumnIndex);
        Integer quantity = cursor.getInt(quantityColumnIndex);
        byte[] byteImage = cursor.getBlob(imageColumnIndex);

        viewHolder.productNameTextView.setText(name);
        viewHolder.productSupplierTextView.setText(supplier);
        viewHolder.productPriceTextView.setText(String.format(Locale.US, "%.2f", price));
        viewHolder.productQuantityTextView.setText(String.valueOf(quantity));
        viewHolder.productImageView.setImageBitmap(ImageUtils.getBitmap(byteImage));
    }
}
