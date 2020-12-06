package com.tutorial.movieapp.ui.base;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment
{
    protected Activity activity;
    protected Context context;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.activity = requireActivity();
        this.context = requireContext();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}
