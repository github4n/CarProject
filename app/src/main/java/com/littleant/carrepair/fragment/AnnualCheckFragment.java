package com.littleant.carrepair.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckFillInfoActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity;
import com.littleant.carrepair.activies.annualcheck.OwnCheckFillInfoActivity;
import com.littleant.carrepair.request.bean.system.PictureListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.system.SurveyImgCmd;
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

public class AnnualCheckFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View reservationView, myReservationView;
    private String picUrl;
    private List<String> picList;
    private Banner ac_banner;
    private ImageView ac_picture;

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
    public static AnnualCheckFragment newInstance(String param1, String param2) {
        AnnualCheckFragment fragment = new AnnualCheckFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        reservationView = subView.findViewById(R.id.ac_cl_reservation);
        reservationView.setOnClickListener(this);

        myReservationView = subView.findViewById(R.id.ac_cl_my_reservation);
        myReservationView.setOnClickListener(this);

        ac_banner = subView.findViewById(R.id.ac_banner);
        ac_banner.setImageLoader(new GlideImageLoader());
        //设置banner样式
        ac_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        ac_picture= subView.findViewById(R.id.ac_picture);

        requestImage();
        return subView;
    }

    private void requestImage() {
        SurveyImgCmd surveyImgCmd = new SurveyImgCmd(getContext());
        surveyImgCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    PictureListBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), PictureListBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        PictureListBean.PictureBean pictureBean = responseBean.getData();
                        if(pictureBean != null) {
                            picUrl = pictureBean.getPic_url();
                            picList = pictureBean.getPic_url_list();
                            if(!TextUtils.isEmpty(picUrl)) {
                                Picasso.with(getContext()).load(Uri.parse(picUrl)).into(ac_picture);
                            }
                            if(picList != null) {
                                ac_banner.setImages(picList);
                                ac_banner.start();
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
        MHCommandExecute.getInstance().asynExecute(getContext(), surveyImgCmd);
    }

    @Override
    public void onClick(View view) {
        if(DataHelper.getGuestLogin(getContext())) {
            getActivity().finish();
            return;
        }
        switch (view.getId()) {
            case R.id.ac_cl_reservation:
                final Dialog d = new Dialog(getActivity(), R.style.MyTransparentDialog);
                View contentView = View.inflate(getActivity(), R.layout.layout_check_type, null);
//                        d.setContentView(R.layout.layout_point);
                DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
                int dialogWidth = (int) (dm.widthPixels * 0.7);
                int dialogHeight = (int) (dm.heightPixels * 0.25);
                d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                contentView.findViewById(R.id.lct_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                //自驾年检
                contentView.findViewById(R.id.lct_tv_own).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), OwnCheckFillInfoActivity.class);
                        getActivity().startActivity(intent);
                        d.dismiss();
                    }
                });
                //代驾年检
                contentView.findViewById(R.id.lct_tv_other).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AnnualCheckFillInfoActivity.class);
                        getActivity().startActivity(intent);
                        d.dismiss();
                    }
                });
                d.show();
                break;

            case R.id.ac_cl_my_reservation:
                //我的年检记录
                Intent intent = new Intent(getContext(), AnnualCheckRecordActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
