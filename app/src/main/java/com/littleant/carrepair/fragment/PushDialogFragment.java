package com.littleant.carrepair.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.util.DisplayMetrics;
import android.view.View;

import com.littleant.carrepair.R;

public class PushDialogFragment extends DialogFragment {
    private boolean isSuccess;
    private static final String IS_SUCCESS = "is_success";
//    private Context mContext;
    private PushDialogListener pushDialogListener;

    public static PushDialogFragment newInstance(boolean isSuccess) {
        PushDialogFragment fragment = new PushDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_SUCCESS, isSuccess);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        isSuccess = getArguments().getBoolean(IS_SUCCESS);
        if(isSuccess) {
            return showSuccessDialog();
        } else {
            return showFailDialog();
        }
//        return super.onCreateDialog(savedInstanceState);
    }

    private Dialog showSuccessDialog() {
        final Dialog d = new Dialog(getActivity(), R.style.MyTransparentDialog);
        View contentView = View.inflate(getActivity(), R.layout.layout_book_success, null);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.8);
        int dialogHeight = (int) (dm.heightPixels * 0.3);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        contentView.findViewById(R.id.lbs_btn_confrm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
//                Intent intent = new Intent(mContext, RepairRecordActivity.class);
//                startActivity(intent);
//                finish();
                if(pushDialogListener != null) {
                    pushDialogListener.onClickOk();
                }
            }
        });
        return d;
    }

    private Dialog showFailDialog() {
        final Dialog d = new Dialog(getActivity(), R.style.MyTransparentDialog);
        View contentView = View.inflate(getActivity(), R.layout.layout_book_fail, null);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.8);
        int dialogHeight = (int) (dm.heightPixels * 0.3);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        d.setCancelable(false);
        contentView.findViewById(R.id.lbf_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                if(pushDialogListener != null) {
                    pushDialogListener.onClickOk();
                }
            }
        });
        contentView.findViewById(R.id.lbf_btn_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                if(pushDialogListener != null) {
                    pushDialogListener.onClickAgain();
                }
//                if(RepairActivity.class.getSimpleName().equals(from)) {
//                    requestRepair();
//                } else if(BookMaintainActivity.class.getSimpleName().equals(from)) {
//                    requestMaintain();
//                }
            }
        });
        return d;
    }

    public void setListener(PushDialogListener listener) {
        pushDialogListener = listener;
    }

    public interface PushDialogListener {
        void onClickAgain();
        void onClickOk();
    }
}
