package com.buvanesh.lib.databind;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/*
 * @author Buvaneshkumar
 * © copyrights reserved 2018.
 * */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity{

    /*
     * viewBinding is the ViewBinder of the layout
     * */
    public T viewBinding;

    /*
     * Method to get the layout of the Activity
     * */
    public abstract int getLayout();

    /*
     * Method to get the Activity
     * */
    public abstract Activity getActivity();

    /*
     * To intialize the viewmodel in fragement
     * */
    public abstract void setViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(getActivity(),getLayout());
        setViewModel();
    }

    /*
     * Return the viewBinder
     * */
    public T getViewBinding(){
        return viewBinding;
    }
}
