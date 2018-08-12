package com.littleant.carrepair.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.AnnualCheckFillInfoActivity;
import com.littleant.carrepair.activies.PickCarActivity;

public class AnnualCheckFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    // TODO: Rename and change types and number of parameters
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
        reservationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AnnualCheckFillInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });
        myReservationView = subView.findViewById(R.id.ac_cl_my_reservation);
        myReservationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PickCarActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return subView;
    }
}
