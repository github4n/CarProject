package com.littleant.carrepair.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.info.InformationActivity;
import com.littleant.carrepair.activies.insurance.InsuranceProxyActivity;
import com.littleant.carrepair.activies.shopping.ShoppingActivity;
import com.littleant.carrepair.request.excute.system.ServiceImgCmd;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;

public class ServiceFragment extends BaseFragment {
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
                Intent intent = new Intent(getContext(), ShoppingActivity.class);
                getActivity().startActivity(intent);
            }
        });
        insuranceView = subView.findViewById(R.id.sm_cl_insurance);
        insuranceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InsuranceProxyActivity.class);
                getActivity().startActivity(intent);

            }
        });
        infoView = subView.findViewById(R.id.sm_cl_info);
        infoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InformationActivity.class);
                getActivity().startActivity(intent);
            }
        });
        moreView = subView.findViewById(R.id.sm_cl_more);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "更多服务", Toast.LENGTH_SHORT).show();
            }
        });

        requestImage();
        return subView;
    }

    private void requestImage() {
        ServiceImgCmd serviceImgCmd = new ServiceImgCmd(getContext());
        serviceImgCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    Log.i("response", command.getResponse());
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(getContext(), serviceImgCmd);
    }

}
