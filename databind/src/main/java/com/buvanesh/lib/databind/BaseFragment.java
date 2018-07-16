package com.buvanesh.lib.databind;

/*
* @author Buvaneshkumar
* © copyrights reserved 2018.
* */

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    /*
    * viewBinding is the ViewBinder of the layout
    * */
    public T viewBinding;

    /*
    * Method to get the layout of the fragment
    * */
    public abstract int getLayout();

    /*
    * To intialize the viewmodel in fragement
    * */
    public abstract void setViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            viewBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
            setViewModel();
        return viewBinding.getRoot();
    }

    /*
    * Return the viewBinder
    * */
    public T getViewBinding(){
        return viewBinding;
    }
}
