package com.celestialsnails.covid19.coloring.util.images;

import android.os.Parcelable;

import com.celestialsnails.covid19.coloring.ui.widget.LoadImageProgress;
import com.celestialsnails.covid19.coloring.util.imports.ImagePreview;


public interface ImageDB {
    int size();
    /* Get an image at an index.
     * Expected: index >= 0
     * If index >= size(), a NullImage is returned.
     */
    Image get(int index);
    void attachObserver(Subject.Observer observer);

    interface Image extends Parcelable {
        // a scaled down version is passed to preview
        void asPreviewImage(ImagePreview preview, LoadImageProgress progress);
        boolean canBePainted();
        // a black and white version is passed to preview
        void asPaintableImage(ImagePreview preview, LoadImageProgress progress);
    }
}
