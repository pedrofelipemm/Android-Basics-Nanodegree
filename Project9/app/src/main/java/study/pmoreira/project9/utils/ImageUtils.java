package study.pmoreira.project9.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import java.io.ByteArrayOutputStream;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static byte[] getBytes(Context context, int imageId) {
        return getBytes(BitmapFactory.decodeResource(context.getResources(), imageId));
    }

    public static Bitmap getBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static int getUncompressedSize(Bitmap bitmap) {
        if (VERSION.SDK_INT < VERSION_CODES.KITKAT) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        } else {
            return bitmap.getAllocationByteCount();
        }
    }

    public static Bitmap scale(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());

        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width, height, filter);
    }
}
