package com.littleant.carrepair.activies.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarBaseBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarBrandLetterBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarBrandLetterList;
import com.littleant.carrepair.request.bean.car.carbrand.CarTypeList;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.car.CarTypeQueryCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.ArrayList;
import java.util.List;

public class CarBrandFirstFragment extends android.support.v4.app.Fragment {
    private static final String CARBRANDLIST = "CarBrandList";
    private ExpandableListView list;
    private CarBrandLetterList brandList;
    private String[] groupString = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String[][] childString;

    public CarBrandFirstFragment() {
    }

    public static CarBrandFirstFragment newInstance(CarBrandLetterList letterList) {
        CarBrandFirstFragment fragment = new CarBrandFirstFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARBRANDLIST, letterList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            brandList = (CarBrandLetterList) getArguments().getSerializable(CARBRANDLIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_expandablelistview, container, false);
        list = view.findViewById(R.id.lelv_list);
        if(brandList != null) {
            ArrayList<List<CarBaseBean>> carBrandLetterBeans = brandList.toArrayList();
            if(carBrandLetterBeans != null && carBrandLetterBeans.size() > 0) {
                final MyAdapter myAdapter = new MyAdapter(carBrandLetterBeans);
                list.setAdapter(myAdapter);
                for (int i = 0; i < myAdapter.getGroupCount(); i++) {
                    list.expandGroup(i);
                }
                list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        return true;
                    }
                });
                list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Log.i("carbrand", "groupPosition - " + groupPosition + "  childPosition - " + childPosition);
                        CarBaseBean child = (CarBaseBean) myAdapter.getChild(groupPosition, childPosition);
//                        if(child.getCartype_set() == null || child.getCartype_set().size() < 1) {
//                            MHToast.showS(getContext(), R.string.car_brand_empty);
//                            return true;
//                        }
                        requestCarType(child);

                        return true;
                    }
                });
            }
        }
        return view;
    }

    private class MyAdapter extends BaseExpandableListAdapter {

        ArrayList<List<CarBaseBean>> carBrandLetterBeans;

        public MyAdapter(ArrayList<List<CarBaseBean>> carBrandLetterBeans) {
            this.carBrandLetterBeans = carBrandLetterBeans;
        }

        @Override
        public int getGroupCount() {
            return carBrandLetterBeans.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return carBrandLetterBeans.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return carBrandLetterBeans.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return carBrandLetterBeans.get(i).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_listview_item2, viewGroup, false);
                groupViewHolder = new GroupViewHolder(convertView);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
            groupViewHolder.mTextView.setText(groupString[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_listview_item3, viewGroup, false);
                childViewHolder = new ChildViewHolder(convertView);
                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.mTextView.setText(carBrandLetterBeans.get(groupPosition).get(childPosition).getName());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        class GroupViewHolder {
            TextView mTextView;

            public GroupViewHolder(View convertView) {
                mTextView = convertView.findViewById(R.id.lli_text2);
            }
        }

        class ChildViewHolder {
            TextView mTextView;

            public ChildViewHolder(View convertView) {
                mTextView = convertView.findViewById(R.id.lli_text3);
            }
        }
    }

    private void requestCarType(CarBaseBean letterBean) {
        CarTypeQueryCmd queryCmd = new CarTypeQueryCmd(getContext(), letterBean.getId());
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
                            transaction.replace(R.id.acb_fragment, CarBrandSecondFragment.newInstance((ArrayList) list.getData()), CarBrandSecondFragment.class.getSimpleName());
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
}
