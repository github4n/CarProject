package com.littleant.carrepair.activies.annualcheck;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.SurveyStationInfo;
import com.littleant.carrepair.request.bean.SurveyStationListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.surveystation.SurveyStationQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

public abstract class BaseFillInfoActivity extends BaseFlowActivity {

    protected SurveyStationInfo selectedStation;
    protected int selectedPosition;
    protected String[] carType;
    protected List<SurveyStationInfo> stations;
    protected boolean showCarType = true;

    @Override
    protected void init() {
        super.init();
        carType = getResources().getStringArray(R.array.annual_car_type);
    }

    protected Dialog setDialog(Context activity, View contentView) {
        final Dialog d = new Dialog(activity, R.style.MyTransparentDialog);
        d.setContentView(contentView);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int dialogWidth = dm.widthPixels;
        int dialogHeight = (int) (dm.heightPixels * 0.4);

        Window window = d.getWindow();
        window.setGravity(Gravity.BOTTOM);
//                window.getDecorView().setPadding(0, 0, 0, 0);
        //设置显示动画
//                window.setWindowAnimations(R.style.main_menu_animstyle);
        //设置显示位置
        WindowManager.LayoutParams p = window.getAttributes(); //获取对话框当前的参数值
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = dialogHeight;
        window.setAttributes(p);

        d.setCanceledOnTouchOutside(false);
        return d;
    }

    protected void showList(final String[] list, final List<SurveyStationInfo> infos, final TextView showView) {
        showCarType = infos == null;
        View contentView2 = LayoutInflater.from(mContext).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
        final Dialog d2 = setDialog(mContext, contentView2);
        d2.setContentView(contentView2);
        contentView2.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
            }
        });

        contentView2.findViewById(R.id.lsd_tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
                if(selectedStation != null) {
                    showView.setText(selectedStation.getName());
                } else if(list != null) {
                    showView.setText(list[selectedPosition]);
                }
            }
        });

        ListView listView2 = contentView2.findViewById(R.id.lsd_list);
        final MyAdapter myAdapter = new MyAdapter(infos, list);
        listView2.setAdapter(myAdapter);
//        listView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("listview", "onItemClick = " + position);
                selectedPosition = position;
                if(infos != null) {
                    selectedStation = (SurveyStationInfo) parent.getItemAtPosition(position);
                }
                //改变选中状态
                myAdapter.setCurrentItem(position);
                //通知ListView改变状态
                myAdapter.notifyDataSetInvalidated();
            }
        });
        d2.show();
    }

    protected class MyAdapter extends BaseAdapter {
        private List<SurveyStationInfo> infos;
        private String[] strings;

        public MyAdapter(List<SurveyStationInfo> infos, String[] strings) {
            this.infos = infos;
            this.strings = strings;
        }

        //当前Item被点击的位置
        private int currentItem = -1;

        public void setCurrentItem(int currentItem) {
            this.currentItem = currentItem;
        }

        @Override
        public int getCount() {
            return infos == null ? strings.length : infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos == null ? strings[position] : infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String text;
            if(infos != null) {
                text = infos.get(position).getName();
            } else {
                text = strings[position];
            }
            holder.mTextView.setText(text);

            if (currentItem == position) {
                //如果被点击，设置当前TextView被选中
                holder.mTextView.setTextColor(getResources().getColor(R.color.color_main));
            } else {
                //如果没有被点击，设置当前TextView未被选中
                holder.mTextView.setTextColor(getResources().getColor(R.color.color_uc_text));
            }

            return convertView;
        }

        class ViewHolder {
            TextView mTextView;

            public ViewHolder(View convertView) {
                mTextView = convertView.findViewById(R.id.lli_text);
            }
        }
    }

    protected interface RequestStationListener {
        void onRequestComplete(List<SurveyStationInfo> stations);
    }

    protected void requestStation(final RequestStationListener listener) {
        SurveyStationQueryAllCmd surveyStationQueryAllCmd = new SurveyStationQueryAllCmd(mContext);
        surveyStationQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        SurveyStationListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyStationListBean.class);
                        stations = listBean.getData();
                        if(stations != null && stations.size() > 0) {
                            if(listener != null) {
                                listener.onRequestComplete(stations);
                            }
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, surveyStationQueryAllCmd);
    }
}
