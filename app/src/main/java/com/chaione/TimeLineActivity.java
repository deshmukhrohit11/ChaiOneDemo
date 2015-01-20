package com.chaione;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chaione.util.TimeLineUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import com.chaione.adaptor.TimeLineAdapter;
import com.chaione.model.Data;
import com.chaione.util.CheckInternetConnection;


/**
 * The Time line class.
 */
public class TimeLineActivity extends Activity {


    /**
     * The time line util.
     */
    private TimeLineUtil mTimeLineUtil;

    /**
     * The recycle view.
     */
    private RecyclerView mRecycleView;

    /**
     * The Progress.
     */
    private ProgressDialog mProgress;


    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading data..");
        mProgress.setCancelable(false);

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final CheckInternetConnection connection =
                        new CheckInternetConnection(getApplicationContext());
                if (connection.isConnectingToInternet()) {
                    swipeView.setRefreshing(true);
                    final android.os.Handler handler = new android.os.Handler();
                    fetchData();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeView.setRefreshing(false);
                        }
                    }, 3000);
                } else {
                    swipeView.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "You don't have internet connection.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecycleView = (RecyclerView) findViewById(R.id.cardList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(llm);
        mTimeLineUtil = TimeLineUtil.getInstance(getApplicationContext());
        mProgress.show();
        fetchData();
    }

    /**
     * On create options menu.
     *
     * @param menu the menu
     * @return true, if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    /**
     * On options item selected.
     *
     * @param item the item
     * @return true, if successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Fetch data.
     */
    private void fetchData() {
        final CheckInternetConnection connection =
                new CheckInternetConnection(getApplicationContext());
        if (connection.isConnectingToInternet()) {
            try {

                final String URL = "https://alpha-api.app.net/stream/0/posts/stream/global";
                final JsonObjectRequest jsonObjReq =
                        new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                            /**
                             * On successful response, call the Survey Launch Screen
                             */
                            @Override
                            public void onResponse(JSONObject response) {

                                if (!response.toString().contains("ERROR")) {
                                    // PROCESS DATA
                                    final Gson gson = new GsonBuilder().serializeNulls().create();

                                    final JsonElement jelem = gson.fromJson(response.toString(), JsonElement.class);
                                    JsonObject jsonObj = jelem.getAsJsonObject();
                                    final JsonArray jsonArray = jsonObj.getAsJsonArray("data");
                                    final ArrayList<Data> dataList =
                                            Data.createDataCollection(jsonArray);
                                    mTimeLineUtil.setTimeLineDataList(dataList);
                                    TimeLineAdapter timeLineAdapter = new TimeLineAdapter(mTimeLineUtil.getTimeLineDataList());
                                    mRecycleView.setAdapter(timeLineAdapter);
                                    mProgress.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {

                            /**s
                             * Activation Failure
                             */
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TimeLineUtil.TAG, "Error: " + error.getMessage());
                            }
                        }) {
                        };

                // Adding request to request queue
                TimeLineUtil.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq, "jobj_req");

            } catch (final Exception e) {
                Log.e(TimeLineUtil.TAG, "Exception: ", e);
            }
        } else {
            mProgress.dismiss();
            Toast.makeText(getApplicationContext(), "You don't have internet connection.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
