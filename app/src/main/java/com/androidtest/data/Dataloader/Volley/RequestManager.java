package com.androidtest.data.Dataloader.Volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;



public class RequestManager
{
	private static RequestQueue requestQueue = null;
	
	private static RequestEvent requestEventListener = null;
	
	private static NetworkResponseRequest networkRequest = null;
	
	static Context mContext = null;
	
	private static String requestUrl = null;
	
	private static boolean shouldCacheAble = true;
	
	private static int timeOutDuration = 2500;
	
	private static int noOfTimeToRetry = 1;
	
	private static RequestManager mInstance = null;
	
	public static RequestManager getInstance(Context contxt)
	{
		if(contxt != mContext)
			destroy();
		
		mContext = contxt;
		
		if (mInstance == null)
		{
			mInstance = new RequestManager();
		}
		
		return mInstance;
	}

	public void getResponce(String requestUrl, int shouldCacheAble,
							Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener)
	{
		if(requestUrl != null)
		{	
			

			RequestManager.requestUrl = requestUrl;
			
			setShouldCacheable(shouldCacheAble);
			
			requestQueue = RequestQueue.getInstance(mContext);
			
			getNetworkResponce(RequestManager.requestUrl, listener, errorListener);
		}
		else
		{
			requestEventListener.onFailureEvent("Inavlid Request Url");
		}
	}
	

	private void setShouldCacheable(int shouldCacheAble) 
	{
		RequestManager.shouldCacheAble = shouldCacheAble == 1;
	}
	
	private void getNetworkResponce(String url, Response.Listener<NetworkResponse> listener,
                                    Response.ErrorListener errorListener)
	{
		networkRequest = new NetworkResponseRequest(Request.Method.GET, url, listener, errorListener);
		
		networkRequest.setRetryPolicy(new DefaultRetryPolicy(timeOutDuration, noOfTimeToRetry, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		
		networkRequest.setShouldCache(shouldCacheAble);
		
		requestQueue.addToRequestQueue(networkRequest);
	}



	public static void destroy()
	{
		mContext = null;
		
		requestUrl = null;
		
		networkRequest = null;
		
		requestQueue = null;
	}
}
