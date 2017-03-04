package study.pmoreira.project9.ui.main;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project9.BuildConfig;
import study.pmoreira.project9.R;
import study.pmoreira.project9.data.InventoryContract.ItemEntry;
import study.pmoreira.project9.ui.adapter.ItemCursorAdapter;
import study.pmoreira.project9.ui.product.ProductActivity;
import study.pmoreira.project9.utils.ImageUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ITEM_LOADER_ID = 0;

    @BindView(R.id.add_product_button)
    FloatingActionButton mAddProductButton;

    @BindView(R.id.product_list)
    ListView mProductListView;

    private CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCursorAdapter = new ItemCursorAdapter(this);

        getLoaderManager().initLoader(ITEM_LOADER_ID, null, this);

        setUpViews();
    }

    private void setUpViews() {
        mProductListView.setAdapter(mCursorAdapter);
        mProductListView.setEmptyView(findViewById(R.id.empty_view));
        mProductListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.setData(ContentUris.withAppendedId(ItemEntry.CONTENT_URI, id));
                startActivity(intent);
            }
        });

        mAddProductButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!BuildConfig.DEBUG) {
            MenuItem menuItem = menu.findItem(R.id.action_insert_dummy_data);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertDummyData();
                return true;
            case R.id.action_delete_all_entries:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertDummyData() {

        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, "Product " + Math.ceil(Math.random() * 10));
        values.put(ItemEntry.COLUMN_ITEM_IMAGE, ImageUtils.getBytes(this, R.drawable.default_product));
        values.put(ItemEntry.COLUMN_ITEM_SUPPLIER, "Supplier " + Math.ceil(Math.random() * 10));
        values.put(ItemEntry.COLUMN_ITEM_PRICE, (double) Math.round(Math.ceil(Math.random() * 10) * 100) / 100);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, Math.ceil(Math.random() * 10));

        getContentResolver().insert(ItemEntry.CONTENT_URI, values);
    }

    private void deleteAll() {
        getContentResolver().delete(ItemEntry.CONTENT_URI, null, null);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_all_dialog_msg)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAll();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                })
                .create()
                .show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_IMAGE,
                ItemEntry.COLUMN_ITEM_SUPPLIER,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_QUANTITY,};

        return new CursorLoader(this, ItemEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
