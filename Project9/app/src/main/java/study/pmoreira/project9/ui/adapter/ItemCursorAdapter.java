package study.pmoreira.project9.ui.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project9.R;
import study.pmoreira.project9.data.InventoryContract.ItemEntry;
import study.pmoreira.project9.ui.product.ProductActivity;
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

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.sell_button)
        Button sellButton;

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
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int idColumnIndex = cursor.getColumnIndex(ItemEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_SUPPLIER);
        int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE);

        final long id = cursor.getLong(idColumnIndex);
        String name = cursor.getString(nameColumnIndex);
        String supplier = cursor.getString(supplierColumnIndex);
        Double price = cursor.getDouble(priceColumnIndex);
        final Integer quantity = cursor.getInt(quantityColumnIndex);
        byte[] byteImage = cursor.getBlob(imageColumnIndex);

        viewHolder.productNameTextView.setText(name);
        viewHolder.productSupplierTextView.setText(supplier);
        viewHolder.productPriceTextView.setText(String.format(Locale.US, "%.2f", price));
        viewHolder.productQuantityTextView.setText(String.valueOf(quantity));
        viewHolder.productImageView.setImageBitmap(ImageUtils.getBitmap(byteImage));

        viewHolder.cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.setData(ContentUris.withAppendedId(ItemEntry.CONTENT_URI, id));
                context.startActivity(intent);
            }
        });

        if (quantity < 1) {
            viewHolder.sellButton.setEnabled(false);
            viewHolder.sellButton.setText(context.getString(R.string.sold_out));
            viewHolder.sellButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
        viewHolder.sellButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quantity - 1);

                context.getContentResolver().update(ContentUris.withAppendedId(ItemEntry.CONTENT_URI, id),
                        values, null, null);

                notifyDataSetChanged();
            }
        });
    }
}
