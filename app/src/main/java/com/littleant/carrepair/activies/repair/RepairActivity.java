package com.littleant.carrepair.activies.repair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.searchdemo.SelectPlaceActivity;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.BookSubmitActivity;
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.activies.datetime.TimeActivity;
import com.littleant.carrepair.activies.maintain.BookMaintainActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.GarageInfo;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainCreateCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.littleant.carrepair.activies.BookSubmitActivity.FROM;
import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;

/**
 * 维修保养
 */
public class RepairActivity extends BaseActivity {

    private Button r_btn_confrm;
    private EditText r_et_description;
    private ImageView r_btn_add_pic, r_btn_add_pic2, r_btn_add_pic3;
    private ImageView r_btn_del1, r_btn_del2, r_btn_del3;
//    private RecyclerView r_pic_list;
    private static final int REQUEST_CODE_CHOOSE1 = 100;//定义请求码常量
    private static final int REQUEST_CODE_CHOOSE2 = 101;//定义请求码常量
    private static final int REQUEST_CODE_CHOOSE3 = 102;//定义请求码常量
    private static final int REQUEST_CODE_SELECT_PLACE = 11;//定义请求码常量
    private Uri[] mSelected = new Uri[3];
//    private List<Uri> mSelected = new ArrayList<>(3);
    private double selectLat, selectLon;
    private String selectAddress;
    private GarageInfo garageInfo;
//    private MyAdapter myAdapter;
    public static final String CONTENT = "content";
    public static final String PIC_LIST = "pic_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            garageInfo = (GarageInfo) extras.getSerializable(GARAGE_INFO);
        }
        if(garageInfo == null) {
            this.finish();
        }
    }

    @Override
    protected void init() {
        super.init();
//        r_et_contact = findViewById(R.id.r_et_contact);
//        r_et_phone = findViewById(R.id.r_et_phone);
        r_et_description = findViewById(R.id.r_et_description);


        r_btn_confrm = findViewById(R.id.r_btn_confrm);
        r_btn_confrm.setOnClickListener(this);

//        r_tv_location_display = findViewById(R.id.r_tv_location_display);
//        r_tv_location_display.setOnClickListener(this);

//        r_tv_time_display = findViewById(R.id.r_tv_time_display);
//        r_tv_time_display.setOnClickListener(this);

        r_btn_add_pic = findViewById(R.id.r_btn_add_pic);
        r_btn_add_pic.setOnClickListener(this);

        r_btn_add_pic2 = findViewById(R.id.r_btn_add_pic2);
        r_btn_add_pic2.setOnClickListener(this);

        r_btn_add_pic3 = findViewById(R.id.r_btn_add_pic3);
        r_btn_add_pic3.setOnClickListener(this);

        r_btn_del1 = findViewById(R.id.r_btn_del1);
        r_btn_del1.setOnClickListener(this);

        r_btn_del2 = findViewById(R.id.r_btn_del2);
        r_btn_del2.setOnClickListener(this);

        r_btn_del3 = findViewById(R.id.r_btn_del3);
        r_btn_del3.setOnClickListener(this);

        r_et_description = findViewById(R.id.r_et_description);

//        r_pic_list = findViewById(R.id.r_pic_list);
//        r_pic_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_repair;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.r_btn_confrm:
                String content = r_et_description.getText().toString();
                if(TextUtils.isEmpty(content)) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                Intent intent = new Intent(mContext, BookSubmitActivity.class);
                intent.putExtra(GARAGE_INFO, garageInfo);
                intent.putExtra(CONTENT, content);
                intent.putExtra(FROM, RepairActivity.class.getSimpleName());
                /*if(myAdapter != null && myAdapter.getCurrentList() != null && myAdapter.getCurrentList().size() > 0) {
                    intent.putParcelableArrayListExtra(PIC_LIST, (ArrayList<Uri>) myAdapter.getCurrentList());
                }*/
                startActivity(intent);
//                requestMaintainCreate();
                break;

//            case R.id.r_tv_location_display:
//                Intent intent1 = new Intent(mContext, SelectPlaceActivity.class);
//                RepairActivity.this.startActivityForResult(intent1, REQUEST_CODE_SELECT_PLACE);
//                break;

//            case R.id.r_tv_time_display:
//                DataHelper.pickDateAndTime(this, new DataHelper.PickDateListener() {
//                    @Override
//                    public void onDatePick(String dateAndTime) {
//                        r_tv_time_display.setText(dateAndTime);
//                    }
//                });
//                break;

            case R.id.r_btn_add_pic:
                Matisse.from(RepairActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .capture(true) //是否提供拍照功能
                        .captureStrategy(new CaptureStrategy(true, "com.littleant.carrepair.fileprovider"))//存储到哪里
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE1);
                break;

            case R.id.r_btn_add_pic2:
                Matisse.from(RepairActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .capture(true) //是否提供拍照功能
                        .captureStrategy(new CaptureStrategy(true, "com.littleant.carrepair.fileprovider"))//存储到哪里
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE2);
                break;

            case R.id.r_btn_add_pic3:
                Matisse.from(RepairActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .capture(true) //是否提供拍照功能
                        .captureStrategy(new CaptureStrategy(true, "com.littleant.carrepair.fileprovider"))//存储到哪里
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE3);
                break;

            case R.id.r_btn_del1:
                mSelected[0] = null;
                r_btn_del1.setVisibility(View.INVISIBLE);
                r_btn_add_pic.setImageResource(R.drawable.r_add_pic);
                break;

            case R.id.r_btn_del2:
                mSelected[1] = null;
                r_btn_del2.setVisibility(View.INVISIBLE);
                r_btn_add_pic2.setImageResource(R.drawable.r_add_pic);
                break;

            case R.id.r_btn_del3:
                mSelected[2] = null;
                r_btn_del3.setVisibility(View.INVISIBLE);
                r_btn_add_pic3.setImageResource(R.drawable.r_add_pic);
                break;

        }
    }

    private void requestMaintainCreate() {
//        int garage_id = garageInfo.getId();
//        String name = r_et_contact.getText().toString();
//        String phone = r_et_phone.getText().toString();
//        String subscribe_time = r_tv_time_display.getText().toString();
//        String longitude = selectLon + "";
//        String latitude = selectLat + "";
//        String address = r_tv_location_display.getText().toString();
//        String content = r_et_contact.getText().toString();
//        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(name) || TextUtils.isEmpty(subscribe_time)
//                || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude) || TextUtils.isEmpty(address) || TextUtils.isEmpty(content)) {
//            MHToast.showS(mContext, R.string.need_finish_info);
//            return;
//        }
//        if (!ProjectUtil.checkPhone(mContext, phone)) {
//            MHToast.showS(mContext, R.string.phone_wrong);
//            return;
//        }
//        Bitmap[] pics = null;
//        if(myAdapter != null) {
//            pics = DataHelper.parseUriList2BitmapArray(this, myAdapter.getCurrentList());
//        }
//        MaintainCreateCmd maintainCreateCmd = new MaintainCreateCmd(mContext, garage_id, name, phone,
//                subscribe_time, longitude, latitude, address, content, pics);
//        maintainCreateCmd.setCallback(new MHCommandCallBack() {
//            @Override
//            public void cmdCallBack(MHCommand command) {
//                if (command != null) {
//                    Log.i("response", command.getResponse());
//                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
//                    if (responseBean != null && responseBean.getCode() == ParamsConstant.REAPONSE_CODE_SUCCESS) {
//                        Intent intent = new Intent(mContext, RepairRecordActivity.class);
//                        RepairActivity.this.startActivity(intent);
//                        RepairActivity.this.finish();
//                    }
//                } else {
//                    MHToast.showS(mContext, R.string.request_fail);
//                }
//            }
//        });
//        MHCommandExecute.getInstance().asynExecute(mContext, maintainCreateCmd);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE1 && resultCode == RESULT_OK) {
            if(Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                Uri uri = Matisse.obtainResult(data).get(0);
                mSelected[0] = uri;;
                Picasso.with(mContext).load(uri).into(r_btn_add_pic);
                r_btn_del1.setVisibility(View.VISIBLE);
            }
        } else if(requestCode == REQUEST_CODE_CHOOSE2 && resultCode == RESULT_OK) {
            if(Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                Uri uri = Matisse.obtainResult(data).get(0);
                mSelected[1] = uri;
                Picasso.with(mContext).load(uri).into(r_btn_add_pic2);
                 r_btn_del2.setVisibility(View.VISIBLE);
            }
        } else if(requestCode == REQUEST_CODE_CHOOSE3 && resultCode == RESULT_OK) {
            if(Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                Uri uri = Matisse.obtainResult(data).get(0);
                mSelected[2] = uri;
                Picasso.with(mContext).load(uri).into(r_btn_add_pic3);
                r_btn_del3.setVisibility(View.VISIBLE);
            }
        }
/*        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if(mSelected != null && mSelected.size() > 0) {
                myAdapter = new MyAdapter(mSelected);
                r_pic_list.setAdapter(myAdapter);
            }
        }*/
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Uri> picUrls;

        public MyAdapter(List<Uri> picUrls) {
            this.picUrls = picUrls;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imageview, parent, false);
            ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            Uri picUri = picUrls.get(position);
            if(picUri != null) {
                Picasso.with(mContext).load(picUri).into(holder.li_imageview);
                holder.li_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            removeItem(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return picUrls.size();
        }

        public void removeItem(int position) {
            if(picUrls != null && picUrls.size() > position) {
                picUrls.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        }

        public List<Uri> getCurrentList() {
            return picUrls;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView li_imageview, li_delete;

            ViewHolder(View itemView) {
                super(itemView);
                li_imageview = itemView.findViewById(R.id.li_imageview);
                li_delete = itemView.findViewById(R.id.li_delete);
            }
        }
    }
}
