package com.androidtest.data.Dataloader.Volley;

public interface RequestEvent{
	
	public void onSuccessEvent(Object... args);
	public void onFailureEvent(Object... args);
}
