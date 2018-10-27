package com.littleant.carrepair.activies.car;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.car.carbrand.CarBaseBean;
import com.littleant.carrepair.request.bean.car.carbrand.CarStyleSet;
import com.littleant.carrepair.request.bean.car.carbrand.CarTypeList;

import java.util.ArrayList;
import java.util.List;

public class CarBrandThirdFragment extends Fragment {

    private ExpandableListView list;
    private String[] groupString;
    private String[][] childString;
    private ArrayList<CarBaseBean> styleList;
    private static final String CAR_TYPE_SET = "car_type_set";
    public static final String CAR_STYLE_SET = "car_style_set";
    private List<Integer> groupList = new ArrayList<>();
    private List<CarBaseBean> childList = new ArrayList<>();
    private List<List<CarBaseBean>> dataList = new ArrayList<>();

    public CarBrandThirdFragment() {
    }

    public static CarBrandThirdFragment newInstance(ArrayList<CarBaseBean> styleList) {
        CarBrandThirdFragment fragment = new CarBrandThirdFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CAR_TYPE_SET, styleList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            styleList = getArguments().getParcelableArrayList(CAR_TYPE_SET);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_expandablelistview, container, false);
        list = view.findViewById(R.id.lelv_list);
        if (styleList != null && styleList.size() > 0) {
            for (CarBaseBean styleSet : styleList) {
                int year = styleSet.getYear();
                boolean out = false; //对象是否已被接收
                for (List<CarBaseBean> temp : dataList) {
                    if (year == temp.get(0).getYear()) {
                        out = true; //表示当前对象已有匹配
                        temp.add(styleSet);
                        break;
                    }
                }
                if(!out) { //对象没有匹配，新建一列
                    ArrayList<CarBaseBean> tempList = new ArrayList<>();
                    tempList.add(styleSet);
                    dataList.add(tempList);
                }
            }
            final MyAdapter adapter = new MyAdapter(dataList);
            list.setAdapter(adapter);
            for (int i = 0; i < adapter.getGroupCount(); i++) {
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
                    CarStyleSet styleSet = (CarStyleSet) adapter.getChild(groupPosition, childPosition);
                    Intent intent = new Intent();
                    intent.putExtra(CAR_STYLE_SET, styleSet);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    return true;
                }
            });
        }
        return view;
    }

    private class MyAdapter extends BaseExpandableListAdapter {

        List<List<CarBaseBean>> mDataList;

        public MyAdapter(List<List<CarBaseBean>> mDataList) {
            this.mDataList = mDataList;
        }

        @Override
        public int getGroupCount() {
            return mDataList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mDataList.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return mDataList.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return mDataList.get(i).get(i1);
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
            groupViewHolder.mTextView.setText(mDataList.get(groupPosition).get(0).getYear() + "");
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
            childViewHolder.mTextView.setText(mDataList.get(groupPosition).get(childPosition).getName());
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

}
