package com.buvanesh.lib.databind;

/*
 * @author Buvaneshkumar
 * © copyrights reserved 2018.
 * */

import retrofit2.Response;

public interface ServiceCallBack<T> {

    void onSuccess(Response<T> response, String apiFlag);
    void onFailure(String errorString);
}
