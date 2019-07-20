package com.androidtest.data.Dataloader;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.androidtest.data.Dataloader.Volley.RequestManager;
import com.androidtest.data.Interface.IDataLoader;
import com.androidtest.util.Constants;

import java.io.UnsupportedEncodingException;


public class DataLoader {

    private IDataLoader eventLsitener;
    private RequestManager requestManager = null;



    public void loadServerResponse(Context context, String url)
    {

        requestManager = RequestManager.getInstance(context);

        requestManager.getResponce(url, 1, networkResponceListener, errorListener);


    }

    public void registerEventListener(IDataLoader dataLoaderEvents) {

        this.eventLsitener = dataLoaderEvents;
    }

    private Response.Listener<NetworkResponse> networkResponceListener = new Response.Listener<NetworkResponse>()
    {
        @Override
        public void onResponse(NetworkResponse responce)
        {

            if(responce != null)
            {
                int statusCode = responce.statusCode;
                String responseString = "";

                try
                {
                    responseString = new String(responce.data, HttpHeaderParser.parseCharset(responce.headers) );
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
                if(eventLsitener != null)
                    eventLsitener.onDataLoaderEventListener(Constants.ON_SUCCESS,responseString);

            }
            else
            {
                if(eventLsitener != null)
                    eventLsitener.onDataLoaderEventListener(Constants.ON_FAILURE);
            }
        }

    };

    private Response.ErrorListener errorListener = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {


            if(eventLsitener != null)
                eventLsitener.onDataLoaderEventListener(Constants.ON_FAILURE);
        }

    };

}
