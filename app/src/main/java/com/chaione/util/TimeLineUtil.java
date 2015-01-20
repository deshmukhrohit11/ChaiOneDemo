package com.chaione.util;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import com.chaione.model.Data;


/**
 * Class for TimeLine utility 
 */
public class TimeLineUtil {

    /**
     * The Constant TAG.
     */
    public static final String TAG = TimeLineUtil.class.getSimpleName();

    /**
     * The m time line util.
     */
    private static TimeLineUtil mTimeLineUtil;

    /**
     * The m time line data list.
     */
    private ArrayList<Data> mTimeLineDataList;

    /**
     * The mContext.
     */
    private Context mContext;

    /**
     * The m request queue.
     */
    private RequestQueue mRequestQueue;

    /**
     * Instantiates a new time line util.
     */
    TimeLineUtil() {

    }

    /**
     * Gets the single instance of TimeLineUtil.
     *
     * @param context the mContext
     * @return single instance of TimeLineUtil
     */
    public static TimeLineUtil getInstance(Context context) {
        if (null == mTimeLineUtil) {
            mTimeLineUtil = new TimeLineUtil();
            mTimeLineUtil.mTimeLineDataList = new ArrayList<>(1);
        }
        mTimeLineUtil.mContext = context;
        return mTimeLineUtil;
    }


    /**
     * Gets the request queue.
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    /**
     * Adds the to request queue.
     *
     * @param <T> the generic type
     * @param req the req
     * @param tag the tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        req.setTag(TextUtils.isEmpty(tag) ? TimeLineUtil.TAG : tag);
        getRequestQueue().add(req);
    }


    /**
     * Gets the time line data list.
     *
     * @return the time line data list
     */
    public ArrayList<Data> getTimeLineDataList() {
        return mTimeLineDataList;
    }

    /**
     * Sets the time line data list.
     *
     * @param mTimeLineDataList the new time line data list
     */
    public void setTimeLineDataList(ArrayList<Data> mTimeLineDataList) {
        this.mTimeLineDataList = mTimeLineDataList;
    }
}



