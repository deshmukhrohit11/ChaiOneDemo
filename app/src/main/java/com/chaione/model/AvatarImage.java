package com.chaione.model;

import android.graphics.Bitmap;

/**
 * The Class Avatar_image.
 */
public class AvatarImage {

    /**
     * The bitmap for image.
     */
    public Bitmap bitmapForImage;
    /**
     * The height.
     */
    private Number height;
    /**
     * The is_default.
     */
    private boolean is_default;
    /**
     * The url.
     */
    private String url;
    /**
     * The width.
     */
    private Number width;

    /**
     * Gets the height.
     *
     * @return the height
     */
    public Number getHeight() {
        return this.height;
    }

    /**
     * Sets the height.
     *
     * @param height the new height
     */
    public void setHeight(Number height) {
        this.height = height;
    }

    /**
     * Gets the is_default.
     *
     * @return the is_default
     */
    public boolean getIs_default() {
        return this.is_default;
    }

    /**
     * Sets the is_default.
     *
     * @param is_default the new is_default
     */
    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Sets the url.
     *
     * @param url the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public Number getWidth() {
        return this.width;
    }

    /**
     * Sets the width.
     *
     * @param width the new width
     */
    public void setWidth(Number width) {
        this.width = width;
    }

    /**
     * Gets the bitmap for image.
     *
     * @return the bitmap for image
     */
    public Bitmap getBitmapForImage() {
        return bitmapForImage;
    }

    /**
     * Sets the bitmap for image.
     *
     * @param bitmapForImage the new bitmap for image
     */
    public void setBitmapForImage(Bitmap bitmapForImage) {
        this.bitmapForImage = bitmapForImage;
    }
}
