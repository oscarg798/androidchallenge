package com.knomatic.weather.model.utils;

import android.os.AsyncTask;

import com.knomatic.weather.model.utils.interfaces.IAsyncTaskExecutor;


public class AsyncTaskExecutor extends AsyncTask<String, Boolean, Boolean> {

    /**
     * An interfaces to call when do in background is done
     */
    private final IAsyncTaskExecutor iExecutatorAsynTask;

    /**
     * An object to return
     */
    private Object returnedObject = null;

    /**
     * an exception to return
     */
    private Exception returnedException = null;

    public AsyncTaskExecutor(IAsyncTaskExecutor iExecutatorAsynTask) {
        this.iExecutatorAsynTask = iExecutatorAsynTask;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            returnedObject = iExecutatorAsynTask.execute();

        } catch (Exception e) {
            returnedException = e;
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            iExecutatorAsynTask.onExecuteComplete(returnedObject);
        } else {
            iExecutatorAsynTask.onExecuteFaliure(returnedException);
        }
    }

    public Object getReturnedObject() {
        return returnedObject;
    }

    public void setReturnedObject(Object returnedObject) {
        this.returnedObject = returnedObject;
    }

    public Exception getReturnedException() {
        return returnedException;
    }

    public void setReturnedException(Exception returnedException) {
        this.returnedException = returnedException;
    }
}