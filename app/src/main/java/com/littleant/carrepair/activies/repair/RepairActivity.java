package com.littleant.carrepair.activies.repair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.activies.datetime.TimeActivity;
import com.littleant.carrepair.request.utils.DataHelper;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

/**
 * 维修保养
 */
public class RepairActivity extends BaseActivity {

    private Button r_btn_confrm;
    private TextView r_tv_location_display, r_tv_time_display;
    private EditText r_et_description;
    private ImageView r_btn_add_pic;
    private RecyclerView r_pic_list;
    private static final int REQUEST_CODE_CHOOSE = 10;//定义请求码常量
    private static final int REQUEST_CODE_SELECT_PLACE = 11;//定义请求码常量
    private List<Uri> mSelected;
    private double selectLat, selectLon;
    private String selectAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init() {
        super.init();
        r_btn_confrm = findViewById(R.id.r_btn_confrm);
        r_btn_confrm.setOnClickListener(this);

        r_tv_location_display = findViewById(R.id.r_tv_location_display);
        r_tv_location_display.setOnClickListener(this);

        r_tv_time_display = findViewById(R.id.r_tv_time_display);
        r_tv_time_display.setOnClickListener(this);

        r_btn_add_pic = findViewById(R.id.r_btn_add_pic);
        r_btn_add_pic.setOnClickListener(this);

        r_et_description = findViewById(R.id.r_et_description);

        r_pic_list = findViewById(R.id.r_pic_list);
        r_pic_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
                Intent intent = new Intent(mContext, RepairRecordActivity.class);
                RepairActivity.this.startActivity(intent);
                break;

            case R.id.r_tv_location_display:
                Intent intent1 = new Intent(mContext, SelectPlaceActivity.class);
                RepairActivity.this.startActivityForResult(intent1, REQUEST_CODE_SELECT_PLACE);
                break;

            case R.id.r_tv_time_display:
                DateActivity dateActivity = new DateActivity();
                dateActivity.setCallback(new DateActivity.SelectDateCallback() {
                    @Override
                    public void onSelectDate(int year, int month, int day) {
                        Log.i("aac_tv_time", "year -- " + year);
                        Log.i("aac_tv_time", "month -- " + month);
                        Log.i("aac_tv_time", "day -- " + day);
                        //格式示例2018-03-20
                        final String date = DataHelper.parseDate(year, month, day);
                        TimeActivity timeActivity = new TimeActivity();
                        timeActivity.setCallback(new TimeActivity.SelectTimeCallback() {
                            @Override
                            public void onSelectTime(int hourOfDay, int minute) {
                                String time = DataHelper.parseTime(hourOfDay, minute);
                                r_tv_time_display.setText(date + " " + time);
                            }
                        });
                        timeActivity.show(getFragmentManager(), TimeActivity.class.getSimpleName());
                    }
                });
                dateActivity.show(getFragmentManager(), DateActivity.class.getSimpleName());

                break;

            case R.id.r_btn_add_pic:
                Matisse.from(RepairActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(3)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(120)
                        .capture(true) //是否提供拍照功能
                        .captureStrategy(new CaptureStrategy(true, "com.littleant.carrepair.fileprovider"))//存储到哪里
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if(mSelected != null && mSelected.size() > 0) {
                r_pic_list.setAdapter(new MyAdapter(mSelected));
            }
        } else if(requestCode == REQUEST_CODE_SELECT_PLACE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if(extras != null) {
                selectAddress = extras.getString(SelectPlaceActivity.SELECT_PLACE_ADDRESS, "");
                selectLat = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LAT);
                selectLon = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LON);
                r_tv_location_display.setText(selectAddress);
            }
        }
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
                holder.li_imageview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        try {
                            removeItem(position);
//                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
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

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView li_imageview;

            ViewHolder(View itemView) {
                super(itemView);
                li_imageview = itemView.findViewById(R.id.li_imageview);
            }
        }
    }
}
