package study.pmoreira.project9.ui.product;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project9.R;
import study.pmoreira.project9.data.InventoryContract.ItemEntry;
import study.pmoreira.project9.ui.main.MainActivity;
import study.pmoreira.project9.utils.ImageUtils;

public class ProductActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ProductActivity.class.getName();

    private static final String STATE_PRODUCT_NAME = "STATE_PRODUCT_NAME";
    private static final String STATE_PRODUCT_PRICE = "STATE_PRODUCT_PRICE";
    private static final String STATE_PRODUCT_QUANTITY = "STATE_PRODUCT_QUANTITY";
    private static final String STATE_PRODUCT_IMAGE = "STATE_PRODUCT_IMAGE";
    private static final String STATE_PRODUCT_SUPPLIER = "STATE_PRODUCT_SUPPLIER";

    private static final int REQUEST_CODE_IMG_HANDLE = 1;
    private static final int PERMISSION_REQUEST_CODE = 7;

    private static final int EXISTING_ITEM_LOADER = 2;

    private static final int MAXIMUM_IMAGE_SIZE_MEGABYTE = 1;
    private static final int MAXIMUM_IMAGE_SIZE_BYTE = MAXIMUM_IMAGE_SIZE_MEGABYTE * 1024 * 1024;

    @BindView(R.id.product_supplier_editext)
    EditText mSupplierNameEditText;

    @BindView(R.id.product_name_editext)
    EditText mProductNameEditText;

    @BindView(R.id.product_price_edittext)
    EditText mProductPriceEditText;

    @BindView(R.id.minus_quantity_button)
    Button mMinusQuantityButton;

    @BindView(R.id.plus_quantity_button)
    Button mPlusQuantityButton;

    @BindView(R.id.product_quantity_textview)
    TextView mProductQuantityTextView;

    @BindView(R.id.product_imageview)
    ImageView mProductImageView;

    private Uri mCurrentItemUri;

    private Bundle mSavedInstanceState = new Bundle();

    private OnClickListener onClickQuantityListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int newValue = 0;
            String currentvalue = mProductQuantityTextView.getText().toString();

            try {
                newValue = Integer.parseInt(currentvalue);
            } catch (NumberFormatException e) {
                Log.e(TAG, "onClick: ", e);
            }

            switch (view.getId()) {
                case R.id.minus_quantity_button:
                    mProductQuantityTextView.setText(String.valueOf(newValue == 0 ? newValue : --newValue));
                    break;
                case R.id.plus_quantity_button:
                    mProductQuantityTextView.setText(String.valueOf(++newValue));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        mCurrentItemUri = getIntent().getData();

        setUpViews();

        if (savedInstanceState != null) {
            mSavedInstanceState = (Bundle) savedInstanceState.clone();
        }

        if (isEditing()) {
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.product_activity_new_product));
            if (savedInstanceState != null) {
                restoreSavedState();
            }
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Bitmap bitmap = ((BitmapDrawable) mProductImageView.getDrawable()).getBitmap();

        outState.putString(STATE_PRODUCT_NAME, mProductNameEditText.getText().toString());
        outState.putString(STATE_PRODUCT_PRICE, mProductPriceEditText.getText().toString());
        outState.putString(STATE_PRODUCT_QUANTITY, mProductQuantityTextView.getText().toString());
        outState.putByteArray(STATE_PRODUCT_IMAGE, ImageUtils.getBytes(bitmap));
        outState.putString(STATE_PRODUCT_SUPPLIER, mSupplierNameEditText.getText().toString());
    }

    private boolean isEditing() {
        return mCurrentItemUri != null;
    }

    private void setUpViews() {
        mMinusQuantityButton.setOnClickListener(onClickQuantityListener);
        mPlusQuantityButton.setOnClickListener(onClickQuantityListener);

        mProductImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestPermissionIfNeeded("android.permission.READ_EXTERNAL_STORAGE")) {
                    startPickImage();
                }
            }
        });
    }

    private void startPickImage() {
        startActivityForResult(
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                REQUEST_CODE_IMG_HANDLE);
    }

    public boolean requestPermissionIfNeeded(String permission) {
        if (VERSION.SDK_INT >= VERSION_CODES.M &&
                checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{permission}, PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PERMISSION_REQUEST_CODE == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPickImage();
            } else {
                Toast.makeText(this, R.string.ask_gallery_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMG_HANDLE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            if (cursor == null) {
                Toast.makeText(this, "Error while retrieving image.", Toast.LENGTH_SHORT).show();
                return;
            }

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
            if (ImageUtils.getUncompressedSize(bitmap) > MAXIMUM_IMAGE_SIZE_BYTE) {
                bitmap = ImageUtils.scale(bitmap, 1000, true);
            }
            mProductImageView.setImageBitmap(bitmap);

        } else {
            Toast.makeText(this, R.string.pick_an_image, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!isEditing()) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                try {
                    saveItem();
                    startActivity(new Intent(this, MainActivity.class));
                } catch (IllegalArgumentException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_dialog_msg)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteItem();
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    private void deleteItem() {
        if (isEditing()) {
            int rowsDeleted = getContentResolver().delete(mCurrentItemUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_item_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_item_successful), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveItem() {
        String name = mProductNameEditText.getText().toString().trim();
        String supplier = mSupplierNameEditText.getText().toString().trim();
        String quantity = mProductQuantityTextView.getText().toString().trim();
        String price = mProductPriceEditText.getText().toString().trim();
        Bitmap bitmap = ((BitmapDrawable) mProductImageView.getDrawable()).getBitmap();

        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, name);
        values.put(ItemEntry.COLUMN_ITEM_SUPPLIER, supplier);
        values.put(ItemEntry.COLUMN_ITEM_PRICE, price);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quantity);

        if (bitmap.sameAs(BitmapFactory.decodeResource(getResources(), R.drawable.default_product))) {
            values.put(ItemEntry.COLUMN_ITEM_IMAGE, new byte[0]);
        } else {
            values.put(ItemEntry.COLUMN_ITEM_IMAGE, ImageUtils.getBytes(bitmap));
        }

        if (isEditing()) {
            int rowsAffected = getContentResolver().update(mCurrentItemUri, values, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.update_item_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.update_item_successful), Toast.LENGTH_SHORT).show();
            }
        } else {
            Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.insert_item_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_item_successful), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_SUPPLIER,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemEntry.COLUMN_ITEM_IMAGE};

        return new CursorLoader(this, mCurrentItemUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
            int supplierColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_SUPPLIER);
            int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
            int imageColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE);

            String name = cursor.getString(nameColumnIndex);

            mProductNameEditText.setText(name);
            mSupplierNameEditText.setText(cursor.getString(supplierColumnIndex));
            mProductPriceEditText.setText(String.format(Locale.US, "%.2f", cursor.getDouble(priceColumnIndex)));

            if (mSavedInstanceState.isEmpty()) {
                mProductQuantityTextView.setText(String.valueOf(cursor.getInt(quantityColumnIndex)));
                mProductImageView.setImageBitmap(ImageUtils.getBitmap(cursor.getBlob(imageColumnIndex)));
            } else {
                restoreSavedState();
            }

            setTitle(name);
        }
    }

    private void restoreSavedState() {
        mProductNameEditText.setText(mSavedInstanceState.getString(STATE_PRODUCT_NAME));
        mProductPriceEditText.setText(mSavedInstanceState.getString(STATE_PRODUCT_PRICE));
        mProductQuantityTextView.setText(mSavedInstanceState.getString(STATE_PRODUCT_QUANTITY));
        mSupplierNameEditText.setText(mSavedInstanceState.getString(STATE_PRODUCT_SUPPLIER));
        mProductImageView.setImageBitmap(
                ImageUtils.getBitmap(mSavedInstanceState.getByteArray(STATE_PRODUCT_IMAGE)));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductNameEditText.setText("");
        mSupplierNameEditText.setText("");
        mProductPriceEditText.setText("");
        mProductQuantityTextView.setText("");
        mProductImageView.setImageResource(R.drawable.default_product);
    }

    public void orderProduct(View view) {
        String productName = mProductNameEditText.getText().toString();
        String msg = getString(R.string.order_email_message,
                productName,
                getString(R.string.default_currency_symbol),
                mProductPriceEditText.getText(),
                getString(R.string.default_currency),
                mSupplierNameEditText.getText(),
                mProductQuantityTextView.getText());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_email_subject, productName));
        intent.putExtra(Intent.EXTRA_TEXT, msg);

        startActivity(intent);
    }
}
