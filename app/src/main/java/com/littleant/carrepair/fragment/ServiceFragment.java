package com.littleant.carrepair.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.littleant.carrepair.R;

public class ServiceFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View mallView, insuranceView, infoView, moreView;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_main;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_service;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mallView = subView.findViewById(R.id.sm_cl_mall);
        mallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "购物商城", Toast.LENGTH_SHORT).show();
            }
        });
        insuranceView = subView.findViewById(R.id.sm_cl_insurance);
        insuranceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "代办保险", Toast.LENGTH_SHORT).show();
            }
        });
        infoView = subView.findViewById(R.id.sm_cl_info);
        infoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "行业资讯", Toast.LENGTH_SHORT).show();
            }
        });
        moreView = subView.findViewById(R.id.sm_cl_more);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "更多服务", Toast.LENGTH_SHORT).show();
            }
        });
        return subView;
    }

}
