package com.littleant.carrepair.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.order.AllOrderActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity;
import com.littleant.carrepair.activies.login.LoginActivity;
import com.littleant.carrepair.activies.address.MyAddressActivity;
import com.littleant.carrepair.activies.car.MyCarActivity;
import com.littleant.carrepair.activies.order.MyOrderActivity;
import com.littleant.carrepair.activies.setting.SettingActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.system.user.UserMeBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.UserMeCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.littleant.circleimageview.CircleImageView;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.activies.order.MyOrderActivity.SELECT_TYPE;

public class UserCenterFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RelativeLayout uc_rl_my_car, uc_rl_check_record, uc_rl_shop_order, uc_rl_address, uc_rl_cs;
    private ImageView iv_all_order, iv_wait_pay, iv_wait_service, iv_wait_rate;
    private TextView tv_user_name, tv_user_score;
    private CircleImageView iv_userImg;
    private UserMeBean meBean;

    private static final int REQUEST_CODE_SETTING = 100;


    public UserCenterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected int getOptionBackgroundId() {
        return R.drawable.uc_setting;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCenterFragment.
     */
    public static UserCenterFragment newInstance(String param1, String param2) {
        UserCenterFragment fragment = new UserCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //设置
        mOptionContent.setOnClickListener(this);

        //我的车库
        uc_rl_my_car = subView.findViewById(R.id.uc_rl_my_car);
        uc_rl_my_car.setOnClickListener(this);

        //年检记录
        uc_rl_check_record = subView.findViewById(R.id.uc_rl_check_record);
        uc_rl_check_record.setOnClickListener(this);

        //商城订单
        uc_rl_shop_order = subView.findViewById(R.id.uc_rl_shop_order);
        uc_rl_shop_order.setOnClickListener(this);

        //维修记录
//        uc_rl_repair_record = subView.findViewById(R.id.uc_rl_repair_record);
//        uc_rl_repair_record.setOnClickListener(this);

        //收货地址
        uc_rl_address = subView.findViewById(R.id.uc_rl_address);
        uc_rl_address.setOnClickListener(this);

        //全部
        iv_all_order = subView.findViewById(R.id.iv_all_order);
        iv_all_order.setOnClickListener(this);

        //待支付
        iv_wait_pay = subView.findViewById(R.id.iv_wait_pay);
        iv_wait_pay.setOnClickListener(this);

        //待服务
        iv_wait_service = subView.findViewById(R.id.iv_wait_service);
        iv_wait_service.setOnClickListener(this);

        //待评价
        iv_wait_rate = subView.findViewById(R.id.iv_wait_rate);
        iv_wait_rate.setOnClickListener(this);

        tv_user_name = subView.findViewById(R.id.tv_user_name);

        tv_user_score = subView.findViewById(R.id.tv_user_score);

        iv_userImg = subView.findViewById(R.id.iv_userImg);

        return subView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestUserInfo();
    }

    private void requestUserInfo() {
        UserMeCmd userMeCmd = new UserMeCmd(getActivity());
        userMeCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    Log.i("user me response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && responseBean.getCode() == ParamsConstant.REAPONSE_CODE_SUCCESS) {
                        meBean = ProjectUtil.getBaseResponseBean(command.getResponse(), UserMeBean.class);
                        UserMeBean.MeBean data = meBean.getData();
                        tv_user_name.setText(data.getName());
                        tv_user_score.setText(String.format(getActivity().getResources().getString(R.string.text_user_center_score), data.getPoint() + ""));
                        Picasso.with(getContext()).load(data.getPic_url()).into(iv_userImg);
                    } else {
                        MHToast.showS(getContext(), R.string.request_fail);
                    }
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(getContext(), userMeCmd);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.header_option_content: //设置
                intent = new Intent(getContext(), SettingActivity.class);
                intent.putExtra(ParamsConstant.EXTRA_USER_ME_BEAN, meBean);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
                break;

            case R.id.uc_rl_my_car://我的车库
                intent = new Intent(getContext(), MyCarActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.uc_rl_check_record://年检记录
                intent = new Intent(getContext(), AnnualCheckRecordActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.uc_rl_shop_order://商城订单
                intent = new Intent(getContext(), AllOrderActivity.class);
                getActivity().startActivity(intent);
                break;

//            case R.id.uc_rl_repair_record://维修记录
//                intent = new Intent(getContext(), RepairRecordActivity.class);
//                getActivity().startActivity(intent);
//                break;

            case R.id.uc_rl_address://收货地址
                intent = new Intent(getContext(), MyAddressActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.iv_all_order://全部
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra(SELECT_TYPE, MyOrderActivity.ALL);
                getActivity().startActivity(intent);
                break;

            case R.id.iv_wait_pay://待支付
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra(SELECT_TYPE, MyOrderActivity.WAIT_PAY);
                getActivity().startActivity(intent);
                break;

            case R.id.iv_wait_service: //待服务
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra(SELECT_TYPE, MyOrderActivity.WAIT_SERVICE);
                getActivity().startActivity(intent);
                break;

            case R.id.iv_wait_rate://待评价
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra(SELECT_TYPE, MyOrderActivity.WAIT_RATE);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("usercenter", "onActivityResult");
        if(requestCode == REQUEST_CODE_SETTING && resultCode == ParamsConstant.ACTIVITY_RESULT_LOGOUT) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        } else if(requestCode == REQUEST_CODE_SETTING && resultCode == ParamsConstant.ACTIVITY_RESULT_ME_MODIFY) {
            requestUserInfo();
        }
    }
}
