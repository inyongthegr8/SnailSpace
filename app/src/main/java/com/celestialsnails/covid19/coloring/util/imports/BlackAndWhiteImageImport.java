package com.celestialsnails.covid19.coloring.util.imports;

import android.graphics.Bitmap;
import android.net.Uri;

import com.celestialsnails.covid19.coloring.ui.widget.LoadImageProgress;
import com.celestialsnails.covid19.coloring.util.FloodFill;


public class BlackAndWhiteImageImport extends UriImageImport {

    public BlackAndWhiteImageImport(Uri imageUri, LoadImageProgress progress, ImagePreview imagePreview) {
        super(imageUri, progress, imagePreview);
    }

    @Override
    protected void runWithBitmap(Bitmap image) {
        imagePreview.setImage(image);
        progress.stepConvertingToBinaryImage();
        Bitmap binaryImage = FloodFill.asBlackAndWhite(image);
        super.runWithBitmap(binaryImage);
    }
}
