package com.littleant.carrepair.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.AllOrderActivity;
import com.littleant.carrepair.activies.AnnualCheckRecordActivity;
import com.littleant.carrepair.activies.MyAddressActivity;
import com.littleant.carrepair.activies.MyCarActivity;
import com.littleant.carrepair.activies.MyOrderActivity;
import com.littleant.carrepair.activies.RepairRecordActivity;
import com.littleant.carrepair.activies.SettingActivity;

public class UserCenterFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RelativeLayout uc_rl_my_car, uc_rl_check_record, uc_rl_shop_order, uc_rl_address, uc_rl_repair_record, uc_rl_cs;
    private ImageView iv_all_order, iv_wait_pay, iv_wait_service, iv_wait_rate;


    public UserCenterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_user_center;
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
    // TODO: Rename and change types and number of parameters
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
        mOptionContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //我的车库
        uc_rl_my_car = subView.findViewById(R.id.uc_rl_my_car);
        uc_rl_my_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyCarActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //年检记录
        uc_rl_check_record = subView.findViewById(R.id.uc_rl_check_record);
        uc_rl_check_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AnnualCheckRecordActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //商城订单
        uc_rl_shop_order = subView.findViewById(R.id.uc_rl_shop_order);
        uc_rl_shop_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //维修记录
        uc_rl_repair_record = subView.findViewById(R.id.uc_rl_repair_record);
        uc_rl_repair_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RepairRecordActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //收货地址
        uc_rl_address = subView.findViewById(R.id.uc_rl_address);
        uc_rl_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyAddressActivity.class);
                getActivity().startActivity(intent);
            }
        });

        iv_all_order = subView.findViewById(R.id.iv_all_order);
        iv_all_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        iv_wait_pay = subView.findViewById(R.id.iv_wait_pay);
        iv_wait_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        iv_wait_service = subView.findViewById(R.id.iv_wait_service);
        iv_wait_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        iv_wait_rate = subView.findViewById(R.id.iv_wait_rate);
        iv_wait_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return subView;
    }
}
