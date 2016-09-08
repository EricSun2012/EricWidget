/*
 * MIT License
 *
 * Copyright (c) 2016 Eric
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.EricSun.EricWidget.Widget.zoom;

import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;


public interface IPhotoView {
    /**
     * Returns true if the PhotoView is set to allow zooming of Photos.
     *
     * @return true if the PhotoView allows zooming.
     */
    boolean canZoom();

    /**
     * Gets the Display Rectangle of the currently displayed Drawable. The
     * Rectangle is relative to this View and includes all scaling and
     * translations.
     *
     * @return - RectF of Displayed Drawable
     */
    RectF getDisplayRect();

    /**
     * @return The current minimum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    float getMinScale();

    /**
     * @return The current middle scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    float getMidScale();

    /**
     * @return The current maximum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    float getMaxScale();

    /**
     * Returns the current scale value
     *
     * @return float - current scale value
     */
    float getScale();

    /**
     * Return the current scale type in use by the ImageView.
     */
    ImageView.ScaleType getScaleType();

    /**
     * Whether to allow the ImageView's parent to intercept the touch event when the photo is scroll to it's horizontal edge.
     */
    void setAllowParentInterceptOnEdge(boolean allow);

    /**
     * Sets the minimum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    void setMinScale(float minScale);

    /**
     * Sets the middle scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    void setMidScale(float midScale);

    /**
     * Sets the maximum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    void setMaxScale(float maxScale);

    /**
     * Register a callback to be invoked when the Photo displayed by this view is long-pressed.
     *
     * @param listener - Listener to be registered.
     */
    void setOnLongClickListener(View.OnLongClickListener listener);

    /**
     * Register a callback to be invoked when the Matrix has changed for this
     * View. An example would be the user panning or scaling the Photo.
     *
     * @param listener - Listener to be registered.
     */
    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener);

    /**
     * Register a callback to be invoked when the Photo displayed by this View
     * is tapped with a single tap.
     *
     * @param listener - Listener to be registered.
     */
    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener);

    /**
     * Register a callback to be invoked when the View is tapped with a single
     * tap.
     *
     * @param listener - Listener to be registered.
     */
    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener);

    /**
     * Controls how the image should be resized or moved to match the size of
     * the ImageView. Any scaling or panning will happen within the confines of
     * this {@link ImageView.ScaleType}.
     *
     * @param scaleType - The desired scaling mode.
     */
    void setScaleType(ImageView.ScaleType scaleType);

    /**
     * Allows you to enable/disable the zoom functionality on the ImageView.
     * When disable the ImageView reverts to using the FIT_CENTER matrix.
     *
     * @param zoomable - Whether the zoom functionality is enabled.
     */
    void setZoomable(boolean zoomable);

    /**
     * Zooms to the specified scale, around the focal point given.
     *
     * @param scale  - Scale to zoom to
     * @param focalX - X Focus Point
     * @param focalY - Y Focus Point
     */
    void zoomTo(float scale, float focalX, float focalY);
}
