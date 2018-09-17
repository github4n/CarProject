package com.littleant.carrepair.activies.info;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.GarageInfo;
import com.littleant.carrepair.request.bean.NewsBannerListBean;
import com.littleant.carrepair.request.bean.NewsCatalogListBean;
import com.littleant.carrepair.request.bean.NewsInfoListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.news.NewsQueryAllCmd;
import com.littleant.carrepair.request.excute.service.newscatalog.NewsCatalogQueryAllCmd;
import com.littleant.carrepair.request.excute.service.newsinfo.NewsInfoQueryAllCmd;
import com.littleant.carrepair.utils.GlideImageLoader;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InformationActivity extends BaseActivity {
    private RecyclerView mList;
    private List<NewsCatalogListBean.InfoBean> infoList;
    private RadioButton info_tv_activity, info_tv_news, info_tv_konwledge;
    private int infoListSize;
    private RadioGroup radioGroup3;
    private List<NewsBannerListBean.BannerBean> bannerList1, bannerList2, bannerList3;
    private List<NewsInfoListBean.NewsInfoBean> newsList1, newsList2, newsList3;
    private MyAdapter myAdapter;
    private Banner info_iv_logo;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.info_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.info_tv_activity:
//                        if(newsList1 != null && bannerList1 != null) {
//                            setBanner(bannerList1);
//                            setListItem(newsList1);
//                        } else {
                            requestBannerInfo(infoList.get(0).getId());
//                        }
                        break;

                    case R.id.info_tv_news:
//                        if(newsList2 != null && bannerList2 != null) {
//                            setBanner(bannerList2);
//                            setListItem(newsList2);
//                        } else {
                            requestBannerInfo(infoList.get(1).getId());
//                        }
                        break;

                    case R.id.info_tv_konwledge:
//                        if(newsList3 != null && bannerList3 != null) {
//                            setBanner(bannerList3);
//                            setListItem(newsList3);
//                        } else {
                            requestBannerInfo(infoList.get(2).getId());
//                        }
                        break;
                }
            }
        });

        info_tv_activity = findViewById(R.id.info_tv_activity);
        info_tv_news = findViewById(R.id.info_tv_news);
        info_tv_konwledge = findViewById(R.id.info_tv_konwledge);

        info_iv_logo = findViewById(R.id.info_iv_logo);
        info_iv_logo.setImageLoader(new GlideImageLoader());
        //设置banner样式
        info_iv_logo.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        requestCatalogList();
    }

    private void requestCatalogList() {
        NewsCatalogQueryAllCmd newsCatalogQueryAllCmd = new NewsCatalogQueryAllCmd(mContext);
        newsCatalogQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {

                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        NewsCatalogListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), NewsCatalogListBean.class);
                        if(listBean != null) {
                            infoList = listBean.getData();
                            infoListSize = infoList.size();
                            setTitleCatalog();
                            requestBannerInfo(infoList.get(0).getId());
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, newsCatalogQueryAllCmd);

    }

    private void setTitleCatalog() {
        if(infoList != null && infoListSize > 2) {
            info_tv_activity.setText(infoList.get(0).getName());
            info_tv_news.setText(infoList.get(1).getName());
            info_tv_konwledge.setText(infoList.get(2).getName());
        }
    }

    //请求banner的图片
    private void requestBannerInfo(final int catalogId) {
        NewsInfoQueryAllCmd newsInfoQueryAllCmd = new NewsInfoQueryAllCmd(mContext, catalogId);
        newsInfoQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        NewsBannerListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), NewsBannerListBean.class);
                        bannerList1 = listBean.getData();
                        setBanner(bannerList1);
                        requestNewsInfo(catalogId);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, newsInfoQueryAllCmd);
    }

    //请求资讯列表
    private void requestNewsInfo(int catalogId) {
        NewsQueryAllCmd newsQueryAllCmd = new NewsQueryAllCmd(mContext, catalogId);
        newsQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        NewsInfoListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), NewsInfoListBean.class);
                        newsList1 = listBean.getData();
                        setListItem(newsList1);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, newsQueryAllCmd);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_info_title;
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<NewsInfoListBean.NewsInfoBean> newsList;

        public MyAdapter(List<NewsInfoListBean.NewsInfoBean> newsList) {
            this.newsList = newsList;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_item, parent, false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            NewsInfoListBean.NewsInfoBean newsInfoBean = newsList.get(position);
            if(newsInfoBean != null) {
                holder.ii_tv_title.setText(newsInfoBean.getTitle());
                holder.ii_tv_description.setText(newsInfoBean.getContent());
                Picasso.with(mContext).load(Uri.parse(newsInfoBean.getPic_url())).into(holder.ii_iv_itemImg);
            }
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView ii_tv_title, ii_tv_description;
            ImageView ii_iv_itemImg;

            ViewHolder(View itemView) {
                super(itemView);
                ii_tv_title = itemView.findViewById(R.id.ii_tv_title);
                ii_tv_description = itemView.findViewById(R.id.ii_tv_description);
                ii_iv_itemImg = itemView.findViewById(R.id.ii_iv_itemImg);
            }

        }
    }

    private void setListItem(List<NewsInfoListBean.NewsInfoBean> newsList) {
        if(newsList != null) {
            mList.setAdapter(new MyAdapter(newsList));
        }
    }

    private void setBanner(List<NewsBannerListBean.BannerBean> bannerList) {
        if(bannerList == null) {
            return;
        }
        List<String> titles = new ArrayList<>();
        List<String> picUrls = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            titles.add(bannerList.get(i).getTitle());
            picUrls.add(bannerList.get(i).getPic_url());
        }
        if(titles.size() < 1 || picUrls.size() < 1) {
            info_iv_logo.releaseBanner();
        } else {
            info_iv_logo.setBannerTitles(titles);
            info_iv_logo.setImages(picUrls);
            info_iv_logo.start();
        }
    }
}
