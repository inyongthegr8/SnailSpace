package com.celestialsnails.covid19.coloring.util.cache;


import com.celestialsnails.covid19.coloring.ui.widget.LoadImageProgress;
import com.celestialsnails.covid19.coloring.util.images.ImageDB;
import com.celestialsnails.covid19.coloring.util.imports.ImagePreview;

public class NullImageCache implements ImageCache {
    @Override
    public boolean asPreviewImage(ImageDB.Image image, ImagePreview preview, LoadImageProgress progress) {
        image.asPreviewImage(preview, progress);
        return false;
    }
}
