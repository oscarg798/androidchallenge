package com.knomatic.weather.model.utils.interfaces;

/**
 * Created by oscargallon on 5/22/16.
 */

public interface IAsyncTaskExecutor {

    Object execute();

    void onExecuteComplete(Object object);

    void onExecuteFaliure(Exception e);
}
