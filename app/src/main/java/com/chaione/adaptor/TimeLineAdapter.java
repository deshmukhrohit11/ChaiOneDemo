package com.chaione.adaptor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.chaione.R;
import com.chaione.model.AvatarImage;
import com.chaione.model.Data;
import com.chaione.model.User;

/**
 * The Class TimeLineAdapter.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineDataHolder> {

    /**
     * The m time line list.
     */
    private List<Data> mTimeLineList;

    /**
     * Instantiates a new time line adapter.
     *
     * @param mTimeLineList the m time line list
     */
    public TimeLineAdapter(List<Data> mTimeLineList) {
        this.mTimeLineList = mTimeLineList;
    }

    /**
     * Gets the rounded rect bitmapForImage.
     *
     * @param bitmap the bitmapForImage
     * @return the rounded rect bitmapForImage
     */
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap) {
        Bitmap result = null;
        result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, 400, 400);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(100, 100, 100, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return result;
    }

    /**
     * Gets the item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return mTimeLineList.size();
    }

    /**
     * On bind view holder.
     *
     * @param timeLineDataHolder the time line data holder
     * @param i                  the i
     */
    @Override
    public void onBindViewHolder(TimeLineDataHolder timeLineDataHolder, int i) {
        if (mTimeLineList.size() > 0) {
            Data data = mTimeLineList.get(i);
            if (null != data) {
                User user = data.getUser();

                if (null != data.getText()) {
                    timeLineDataHolder.vDescription.setText(data.getText());
                }
                if (null != user.getName()) {
                    timeLineDataHolder.vName.setText(user.getName());
                }
                AvatarImage image = user.getAvatar_image();
                if (null != image) {
                    timeLineDataHolder.vAvatar.setTag(image.getUrl());//tag of imageView == path to image
                    if (null == image.getBitmapForImage()) {
                        new LoadImage(timeLineDataHolder.vAvatar, timeLineDataHolder.vProgress, image).execute();
                    } else {
                        timeLineDataHolder.vAvatar.setImageBitmap(image.getBitmapForImage());
                    }
                }
            }
        }
    }

    /**
     * On create view holder.
     *
     * @param viewGroup the view group
     * @param i         the counter
     * @return the time line data holder
     */
    @Override
    public TimeLineDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);
        return new TimeLineDataHolder(itemView);
    }

    /**
     * The Class TimeLineDataHolder.
     */
    public static class TimeLineDataHolder extends RecyclerView.ViewHolder {

        /**
         * The name.
         */
        protected TextView vName;

        /**
         * The description.
         */
        protected TextView vDescription;

        /**
         * The avatar.
         */
        protected ImageView vAvatar;

        /**
         * The progress.
         */
        protected ProgressBar vProgress;

        /**
         * Instantiates a new time line data holder.
         *
         * @param view
         */
        public TimeLineDataHolder(View view) {
            super(view);
            vName = (TextView) view.findViewById(R.id.txtName);
            vDescription = (TextView) view.findViewById(R.id.txtDescription);
            vAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            vProgress = (ProgressBar) view.findViewById(R.id.loadingPanel);
        }
    }

    /**
     * Image loading.
     */
    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        /**
         * The image.
         */
        AvatarImage image;
        /**
         * The imv.
         */
        private ImageView imv;
        /**
         * The path.
         */
        private String path;
        /**
         * The progress bar.
         */
        private ProgressBar progressBar;

        /**
         * Instantiates a new load image.
         *
         * @param imv         the imv
         * @param progressBar the progress bar
         * @param image       the image
         */
        public LoadImage(ImageView imv, ProgressBar progressBar, AvatarImage image) {
            this.imv = imv;
            this.path = imv.getTag().toString();
            this.progressBar = progressBar;
            this.image = image;
        }

        /**
         * Do in background.
         *
         * @param params the params
         * @return the bitmapForImage
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            Bitmap bitmap = null;
            if (null != this.path) {
                bitmap = downloadImage(this.path);
                Bitmap reSized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                bitmap = getRoundedRectBitmap(reSized);
            }
            return bitmap;
        }

        // Creates Bitmap from InputStream and returns it

        /**
         * Download image.
         *
         * @param url the url
         * @return the bitmapForImage
         */
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                if (null != stream) {
                    bitmap = BitmapFactory.
                            decodeStream(stream, null, bmOptions);
                    stream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream

        /**
         * Gets the http connection.
         *
         * @param urlString the url string
         * @return the http connection
         * @throws IOException Signals that an I/O exception has occurred.
         */
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

        /**
         * On post execute.
         *
         * @param result the result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (!imv.getTag().toString().equals(path)) {
               /* The path is not same. This means that this
                  image view is handled by some other async task.
                  We don't do anything and return. */
                return;
            }
            if (result != null && imv != null) {
                imv.setVisibility(View.VISIBLE);
                imv.setImageBitmap(result);
                image.setBitmapForImage(result);
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                imv.setVisibility(View.GONE);
            }
        }

    }

}
