package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.SurveyInfo;
import com.littleant.carrepair.request.bean.SurveyListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.SurveyQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

public class AnnualCheckRecordActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mList;
    private int queryType = ParamsConstant.SURVEY_NOT_FINISH;
    private List<SurveyInfo> dataNotFinish;
    private List<SurveyInfo> dataFinish;
    private RadioGroup acr_radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mList.setAdapter(new MyAdapter());

        requestCheckRecord();
    }

    private void requestCheckRecord() {
        SurveyQueryAllCmd surveyQueryAllCmd = new SurveyQueryAllCmd(mContext, queryType);
        surveyQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        SurveyListBean surveyListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyListBean.class);
                        if(queryType == ParamsConstant.SURVEY_NOT_FINISH) {
                            dataNotFinish = surveyListBean.getData();
                            setListItem(dataNotFinish);
                        } else if(queryType == ParamsConstant.SURVEY_FINISH) {
                            dataFinish = surveyListBean.getData();
                            setListItem(dataFinish);
                        }


                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, surveyQueryAllCmd);
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.acr_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        acr_radiogroup = findViewById(R.id.acr_radiogroup);
        acr_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.acr_doing:
                        queryType = ParamsConstant.SURVEY_NOT_FINISH;
                        if(dataNotFinish == null) {
                            requestCheckRecord();
                        } else {
                            setListItem(dataNotFinish);
                        }
                        break;

                    case R.id.acr_finish:
                        queryType = ParamsConstant.SURVEY_FINISH;
                        queryType = ParamsConstant.MAINTAIN_FINISH;
                        if(dataFinish == null) {
                            requestCheckRecord();
                        } else {
                            setListItem(dataFinish);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_check_record;
    }

    @Override
    public void onClick(View view) {

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<SurveyInfo> surveyInfos;

        public MyAdapter(List<SurveyInfo> surveyInfos) {
            this.surveyInfos = surveyInfos;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_annual_record_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            SurveyInfo surveyInfo = surveyInfos.get(position);
            if(surveyInfo != null) {
                holder.ari_book.setText(surveyInfo.getSubscribe_time());
                holder.ari_finish.setText(surveyInfo.getSubscribe_time());
                holder.ari_location.setText(surveyInfo.getSurveystation().getName());
                holder.ari_driver.setText(surveyInfo.getDriver_user_name());
            }
        }

        @Override
        public int getItemCount() {
            return surveyInfos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView ari_book, ari_finish, ari_location, ari_driver;

            ViewHolder(View itemView) {
                super(itemView);
                ari_book = itemView.findViewById(R.id.ari_book);
                ari_finish = itemView.findViewById(R.id.ari_finish);
                ari_location = itemView.findViewById(R.id.ari_location);
                ari_driver = itemView.findViewById(R.id.ari_driver);
            }

        }
    }

    private void setListItem(List<SurveyInfo> listItem) {
        if(listItem != null) {
            mList.setAdapter(new MyAdapter(listItem));
        }
    }

}
