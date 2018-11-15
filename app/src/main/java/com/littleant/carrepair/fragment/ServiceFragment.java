package com.littleant.carrepair.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.info.InformationActivity;
import com.littleant.carrepair.activies.insurance.InsuranceProxyActivity;
import com.littleant.carrepair.activies.shopping.ShoppingActivity;
import com.littleant.carrepair.request.bean.system.PictureListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.system.ServiceImgCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.GlideImageLoader;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

public class ServiceFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View mallView, insuranceView, infoView, moreView;
    private String picUrl;
    private List<String> picList;
    private Banner sm_banner;
    private ImageView sm_picture;
    private static  PictureListBean.PictureBean pictureBean;

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
        mallView.setOnClickListener(this);

        insuranceView = subView.findViewById(R.id.sm_cl_insurance);
        insuranceView.setOnClickListener(this);

        infoView = subView.findViewById(R.id.sm_cl_info);
        infoView.setOnClickListener(this);

        moreView = subView.findViewById(R.id.sm_cl_more);
        moreView.setOnClickListener(this);

        sm_banner = subView.findViewById(R.id.sm_banner);
        sm_banner.setImageLoader(new GlideImageLoader());
        //设置banner样式
        sm_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        sm_picture= subView.findViewById(R.id.sm_picture);

        requestImage();
        return subView;
    }

    private void requestImage() {
        if(pictureBean==null){
            ServiceImgCmd serviceImgCmd = new ServiceImgCmd(getContext());
            serviceImgCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if (command != null) {
                        Log.i("response", command.getResponse());
                        PictureListBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), PictureListBean.class);
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            pictureBean = responseBean.getData();
                            if(pictureBean != null) {
                                picUrl = pictureBean.getPic_url();
                                picList = pictureBean.getPic_url_list();
                                if(!TextUtils.isEmpty(picUrl)) {
                                    Picasso.with(getContext()).load(Uri.parse(picUrl)).into(sm_picture);
                                }
                                if(picList != null) {
                                    sm_banner.setImages(picList);
                                    sm_banner.start();
                                }
                            }
                        } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                            Intent intent = ProjectUtil.tokenExpiredIntent(getActivity());
                            startActivity(intent);
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(getContext(), responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(getContext(), R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(getContext(), serviceImgCmd);
        }else {
            picUrl = pictureBean.getPic_url();
            picList = pictureBean.getPic_url_list();
            if(!TextUtils.isEmpty(picUrl)) {
                Picasso.with(getContext()).load(Uri.parse(picUrl)).into(sm_picture);
            }
            if(picList != null) {
                sm_banner.setImages(picList);
                sm_banner.start();
            }
        }

    }

    @Override
    public void onClick(View view) {
        if(DataHelper.getGuestLogin(getContext())) {
            getActivity().finish();
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.sm_cl_mall:
//                intent = new Intent(getContext(), ShoppingActivity.class);
//                getActivity().startActivity(intent);
                MHToast.showS(getContext(), "商城敬请期待");
                break;

            case R.id.sm_cl_insurance:
//                intent = new Intent(getContext(), InsuranceProxyActivity.class);
//                getActivity().startActivity(intent);
                MHToast.showS(getContext(), "代办保险敬请期待");
                break;

            case R.id.sm_cl_info:
//                intent = new Intent(getContext(), InformationActivity.class);
//                getActivity().startActivity(intent);
                MHToast.showS(getContext(), "行业资讯敬请期待");
                break;

            case R.id.sm_cl_more:
                Toast.makeText(getContext(), "更多服务敬请期待", Toast.LENGTH_SHORT).show();
                break;

            case R.id.sm_banner:

                break;

            case R.id.sm_picture:

                break;
        }
    }
}
