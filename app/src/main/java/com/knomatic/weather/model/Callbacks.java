package com.knomatic.weather.model;

import com.rm.androidesentials.model.utils.CoupleParams;

import java.io.Serializable;
import java.util.List;

/**
 * Created by oscargallon on 5/22/16.
 */

public class Callbacks {

    /**
     * Private contructor
     */
    private Callbacks(){

    }

    public interface IGetForecastFromLocationCallbacks{
        void onForecastGot(List<CoupleParams> coupleParamsList);

        void onGetForecastError(Exception error);
    }

    public interface IFragmentUpdate{
        void receiveUpdated(Serializable serializable);
    }
}
