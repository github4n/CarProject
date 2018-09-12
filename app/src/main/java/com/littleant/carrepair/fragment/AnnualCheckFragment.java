package com.littleant.carrepair.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckFillInfoActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity;
import com.littleant.carrepair.activies.annualcheck.OwnCheckFillInfoActivity;

public class AnnualCheckFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View reservationView, myReservationView;

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
        return subView;
    }

    @Override
    public void onClick(View view) {
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
                contentView.findViewById(R.id.lct_tv_own).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), OwnCheckFillInfoActivity.class);
                        getActivity().startActivity(intent);
                        d.dismiss();
                    }
                });
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
                Intent intent = new Intent(getContext(), AnnualCheckRecordActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
