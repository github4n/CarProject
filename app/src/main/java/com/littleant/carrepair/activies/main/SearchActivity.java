package com.littleant.carrepair.activies.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.repair.RepairStationActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.bean.maintain.garage.GarageListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.garage.GarageQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;
import static com.littleant.carrepair.fragment.MainFragment.GARAGE_LIST;
import static com.littleant.carrepair.fragment.MainFragment.MY_LATITUDE;
import static com.littleant.carrepair.fragment.MainFragment.MY_LONGITUDE;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private RecyclerView mList;
    private TextView as_tv_cancel;
    private ArrayList<GarageInfo> allData;
    private ArrayList<GarageInfo> distanceData;
    private ArrayList<GarageInfo> popularData;
    private ArrayList<GarageInfo> searchData;
    private RadioButton as_tv_band, as_tv_near, as_tv_high;
    private RadioGroup as_radioGroup;
    private EditText as_tv_search;
    //我的位置
    private double myLatitude, myLongitude;
    //排序
    private ParamsConstant.OrderRule orderby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            allData = (ArrayList<GarageInfo>) extras.getSerializable(GARAGE_LIST);
            myLatitude = extras.getDouble(MY_LATITUDE);
            myLongitude = extras.getDouble(MY_LONGITUDE);
        }

        mList = findViewById(R.id.as_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        setListItem(allData);

        as_tv_cancel = findViewById(R.id.as_tv_cancel);
        as_tv_cancel.setOnClickListener(this);

        as_tv_band = findViewById(R.id.as_tv_band);
//        as_tv_band.setOnClickListener(this);

        as_tv_near = findViewById(R.id.as_tv_near);
//        as_tv_near.setOnClickListener(this);

        as_tv_high = findViewById(R.id.as_tv_high);
//        as_tv_high.setOnClickListener(this);

        as_radioGroup = findViewById(R.id.as_radioGroup);
        as_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.as_tv_band:
                        as_tv_search.setText("");
                        if(allData != null) {
                            setListItem(allData);
                        }
                        break;

                    case R.id.as_tv_near:
                        as_tv_search.setText("");
                        orderby = ParamsConstant.OrderRule.DISTANCE;
                        if(distanceData == null || distanceData.size() < 1) {
                            requestGarageList();
                        } else {
                            setListItem(distanceData);
                        }
                        break;

                    case R.id.as_tv_high:
                        as_tv_search.setText("");
                        orderby = ParamsConstant.OrderRule.POPULAR;
                        if(popularData == null || popularData.size() < 1) {
                            requestGarageList();
                        } else {
                            setListItem(popularData);
                        }
                        break;
                }
            }
        });

        as_tv_search = findViewById(R.id.as_tv_search);
        as_tv_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            }

            @Override
            public void afterTextChanged(Editable s) {
                //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
                Log.i("carproject", "搜索关键词： " + s.toString());
                searchGarage(s.toString());
            }
        });

    }

    private void searchGarage(String s) {
        if(TextUtils.isEmpty(s)) {
            if(as_tv_band.isChecked()) {
                setListItem(allData);
            } else if(as_tv_near.isChecked()) {
                setListItem(distanceData);
            } else if(as_tv_high.isChecked()) {
                setListItem(popularData);
            }
        } else {
            if(as_tv_band.isChecked()) {
                garageFliter(allData, s);
            } else if(as_tv_near.isChecked()) {
                garageFliter(distanceData, s);
            } else if(as_tv_high.isChecked()) {
                garageFliter(popularData, s);
            }
        }
    }

    private synchronized void garageFliter(ArrayList<GarageInfo> fliterList, String fliter) {
        if(fliterList == null || fliterList.size() < 1) {
            Log.i("carproject", "没有年检站，无法搜索");
            return;
        }
        searchData = new ArrayList<>();
        for (GarageInfo info : fliterList) {
            if(info.getName().contains(fliter)) {
                Log.i("carproject", info.getName() + "匹配上了" + fliter);
                searchData.add(info);
            } else {
                Log.i("carproject", info.getName() + "没有匹配" + fliter);
            }
        }
        setListItem(searchData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.as_tv_cancel:
                SearchActivity.this.finish();
                break;
        }
    }

    private void requestGarageList() {
        GarageQueryAllCmd garageQueryAllCmd = new GarageQueryAllCmd(mContext, "", orderby, myLongitude, myLatitude);
        garageQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        GarageListBean garageListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), GarageListBean.class);
                        switch (orderby) {
                            case DISTANCE:
                                distanceData = (ArrayList<GarageInfo>) garageListBean.getData();
                                setListItem(distanceData);
                                break;

                            case POPULAR:
                                popularData = (ArrayList<GarageInfo>) garageListBean.getData();
                                setListItem(popularData);
                                break;
                        }

                    } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                        Intent intent = ProjectUtil.tokenExpiredIntent(mContext);
                        startActivity(intent);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, garageQueryAllCmd);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
        private ArrayList<GarageInfo> list;

        public MyAdapter(ArrayList<GarageInfo> list) {
            this.list = list;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_item, parent, false);
            view.setOnClickListener(this);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            GarageInfo garageInfo = list.get(position);
            if(garageInfo != null) {
                holder.lsi_tv_title.setText(garageInfo.getName());
                holder.lsi_tv_distance.setText(String.format(mContext.getResources().getString(R.string.text_search_distance), garageInfo.getDistance() + ""));
//                holder.lsi_tv_like.setText(garageInfo.getPopular() + "");
                holder.lsi_contact.setText(garageInfo.getUser_name() + " | " + garageInfo.getPhone());
                holder.lsi_tv_location.setText(garageInfo.getAddress());
                Picasso.with(mContext).load(Uri.parse(garageInfo.getPic_url())).into(holder.lsi_iv_itemImg);
                holder.itemView.setTag(position);
                holder.lsi_ratingBar.setCountSelected(garageInfo.getScore());
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            GarageInfo garageInfo = list.get(position);
            if(garageInfo != null) {
                Intent intent = new Intent(SearchActivity.this, RepairStationActivity.class);
                intent.putExtra(GARAGE_INFO, garageInfo);
                SearchActivity.this.startActivity(intent);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            //text_search_distance
            TextView lsi_tv_title, lsi_tv_distance, lsi_tv_like, lsi_contact, lsi_tv_location;
            ImageView  lsi_iv_itemImg;
            XLHRatingBar lsi_ratingBar;

            ViewHolder(View itemView) {
                super(itemView);
                lsi_tv_title = itemView.findViewById(R.id.lsi_tv_title);
                lsi_tv_distance = itemView.findViewById(R.id.lsi_tv_distance);
//                lsi_tv_like = itemView.findViewById(R.id.lsi_tv_like);
                lsi_contact = itemView.findViewById(R.id.lsi_contact);
                lsi_tv_location = itemView.findViewById(R.id.lsi_tv_location);
                lsi_iv_itemImg = itemView.findViewById(R.id.lsi_iv_itemImg);
                lsi_ratingBar = itemView.findViewById(R.id.lsi_ratingBar);
            }

        }
    }

    private void setListItem(ArrayList<GarageInfo> listItem) {
        if(listItem != null) {
            mList.setAdapter(new MyAdapter(listItem));
        }
    }
}
