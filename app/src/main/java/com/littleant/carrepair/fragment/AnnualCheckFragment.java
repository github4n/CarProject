package com.littleant.carrepair.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.littleant.carrepair.R;

public class AnnualCheckFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public AnnualCheckFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_annual_check;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnualCheckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnualCheckFragment newInstance(String param1, String param2) {
        AnnualCheckFragment fragment = new AnnualCheckFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}