package com.celestialsnails.covid19.coloring.util.cache;


import com.celestialsnails.covid19.coloring.ui.widget.LoadImageProgress;
import com.celestialsnails.covid19.coloring.util.images.ImageDB;
import com.celestialsnails.covid19.coloring.util.imports.ImagePreview;

public interface ImageCache {
    boolean asPreviewImage(ImageDB.Image image, ImagePreview thumbPreview, LoadImageProgress progress);
}
