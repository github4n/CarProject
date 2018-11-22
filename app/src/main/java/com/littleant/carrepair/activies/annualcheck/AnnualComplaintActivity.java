package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.BookSubmitActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.ServerComplaintCmd;
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
import static com.littleant.carrepair.activies.repair.RepairActivity.CONTENT;
import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/11/22 0022
 * 版本号:1
 */


public class AnnualComplaintActivity extends BaseActivity{
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
    private SurveyInfo surveyInfo;
    //    private MyAdapter myAdapter;
    public static final String ID = "id";

    public static final String CONTENT = "content";
    public static final String PIC_LIST = "pic_list";
    public static Activity repairActivity;
    private String id,content;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_complaint;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_users_complainting;
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
                ArrayList<Uri> picList = new ArrayList<>();
                for (Uri uri : mSelected) {
                    if(uri != null) {
                        picList.add(uri);
                    }
                }
                Bitmap[] pics = DataHelper.parseUriList2BitmapArray(this, picList);

                ServerComplaintCmd serverComplaintCmd=new ServerComplaintCmd(this,id,content,pics);
                serverComplaintCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            MHToast.showS(mContext, responseBean.getMsg());
                            finish();

                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, serverComplaintCmd);

//                Intent intent = new Intent(mContext, BookSubmitActivity.class);
//                intent.putExtra(GARAGE_INFO, garageInfo);
//                intent.putExtra(CONTENT, content);
//                if(picList.size() > 0) {
//                    intent.putExtra(PIC_LIST, picList);
//                }
//                intent.putExtra(FROM, RepairActivity.class.getSimpleName());
//
//                startActivity(intent);
                break;



            case R.id.r_btn_add_pic:
                Matisse.from(AnnualComplaintActivity.this)
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
                Matisse.from(AnnualComplaintActivity.this)
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
                Matisse.from(AnnualComplaintActivity.this)
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE1 && resultCode == RESULT_OK) {
            if(Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                Uri uri = Matisse.obtainResult(data).get(0);
                mSelected[0] = uri;
                Picasso.with(mContext).load(uri).resize(100, 100).centerCrop().into(r_btn_add_pic);
                r_btn_del1.setVisibility(View.VISIBLE);
            }
        } else if(requestCode == REQUEST_CODE_CHOOSE2 && resultCode == RESULT_OK) {
            if(Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                Uri uri = Matisse.obtainResult(data).get(0);
                mSelected[1] = uri;
                Picasso.with(mContext).load(uri).resize(100, 100).centerCrop().into(r_btn_add_pic2);
                r_btn_del2.setVisibility(View.VISIBLE);
            }
        } else if(requestCode == REQUEST_CODE_CHOOSE3 && resultCode == RESULT_OK) {
            if(Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                Uri uri = Matisse.obtainResult(data).get(0);
                mSelected[2] = uri;
                Picasso.with(mContext).load(uri).resize(100, 100).centerCrop().into(r_btn_add_pic3);
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
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
                id=extras.getString(ID);
                //content = extras.getString(CONTENT);
            }
//        r_pic_list = findViewById(R.id.r_pic_list);
//        r_pic_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    private class MyAdapter extends RecyclerView.Adapter<AnnualComplaintActivity.MyAdapter.ViewHolder> {
        private List<Uri> picUrls;

        public MyAdapter(List<Uri> picUrls) {
            this.picUrls = picUrls;
        }

        @Override
        public AnnualComplaintActivity.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imageview, parent, false);
            AnnualComplaintActivity.MyAdapter.ViewHolder viewHolder = new AnnualComplaintActivity.MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AnnualComplaintActivity.MyAdapter.ViewHolder holder, final int position) {
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
