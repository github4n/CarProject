package com.littleant.carrepair.activies.car;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarBaseBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarBrandLetterBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarTypeList;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.car.CarStyleQueryCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.ArrayList;
import java.util.List;

public class CarBrandSecondFragment extends Fragment {

    private ArrayList<CarBaseBean> letterBean;
    public static final String  CAR_BRAND_LETTER_BEAN = "car_brand_letter_bean";
    private ListView list;

    public CarBrandSecondFragment() {
    }

    public static CarBrandSecondFragment newInstance(ArrayList<CarBaseBean> letterBean) {
        CarBrandSecondFragment fragment = new CarBrandSecondFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CAR_BRAND_LETTER_BEAN, letterBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            letterBean = getArguments().getParcelableArrayList(CAR_BRAND_LETTER_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        list = view.findViewById(R.id.llv_list);
        if(letterBean != null && letterBean.size() > 0) {
//            List<CarTypeList> cartype_set = letterBean.getCartype_set();
//            if(cartype_set != null && cartype_set.size() > 0) {
                final MyAdapter adapter = new MyAdapter(letterBean);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CarBaseBean carTypeList = (CarBaseBean) adapter.getItem(position);
//                        if(carTypeList.getCarstyle_set() == null && carTypeList.getCarstyle_set().size() < 1) {
//                            MHToast.showS(getContext(), R.string.car_brand_empty);
//                            return;
//                        }
                        requestCarStyle(carTypeList);
                    }
                });
//            }
        }
        return view;
    }

    private void requestCarStyle(CarBaseBean carTypeList) {
        CarStyleQueryCmd queryCmd = new CarStyleQueryCmd(getContext(), carTypeList.getId());
        queryCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        CarTypeList list = ProjectUtil.getBaseResponseBean(command.getResponse(), CarTypeList.class);
                        if(list == null || list.getData() == null || list.getData().size() < 1) {
                            MHToast.showS(getContext(), R.string.car_brand_empty);
                        } else {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.acb_fragment, CarBrandThirdFragment.newInstance((ArrayList) list.getData()), CarBrandThirdFragment.class.getSimpleName());
                            transaction.addToBackStack(null); //将当前的事务添加到了回退栈
                            transaction.commit();
                        }
                    } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                        Intent intent = ProjectUtil.tokenExpiredIntent(getActivity());
                        startActivity(intent);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(getContext(), responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(getContext(), R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(getContext(), queryCmd);
    }

    private class MyAdapter extends BaseAdapter {
        ArrayList<CarBaseBean> mCartype_set;

        public MyAdapter(ArrayList<CarBaseBean> cartype_set) {
            this.mCartype_set = cartype_set;
        }

        @Override
        public int getCount() {
            return mCartype_set.size();
        }

        @Override
        public Object getItem(int position) {
            return mCartype_set.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_listview_item3, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            CarTypeList carTypeList = mCartype_set.get(position);
//            if(carTypeList != null) {
                viewHolder.mTextView.setText(mCartype_set.get(position).getName());
//            }

            return convertView;
        }

        class ViewHolder {
            TextView mTextView;

            public ViewHolder(View convertView) {
                mTextView = convertView.findViewById(R.id.lli_text3);
            }
        }

    }
}
