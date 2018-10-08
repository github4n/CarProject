package com.littleant.carrepair.activies.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.carbrand.CarBrandLetterBean;
import com.littleant.carrepair.request.bean.carbrand.CarBrandLetterList;
import com.littleant.carrepair.request.bean.carbrand.CarTypeSet;

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
            ArrayList<CarBrandLetterBean> carBrandLetterBeans = brandList.toArrayList();
            int l = carBrandLetterBeans.size();
            for(int i = 0; i < l; i++) {
                CarBrandLetterBean letterBean = carBrandLetterBeans.get(i);
                List<CarTypeSet> cartype_set = letterBean.getCartype_set();
                for(int j = 0; j < cartype_set.size(); j++) {
                    childString[i][j] = cartype_set.get(j).getName();
                }
            }
            MyAdapter myAdapter = new MyAdapter(groupString, childString);
            list.setAdapter(myAdapter);
        }
        return view;
    }

    private class MyAdapter extends BaseExpandableListAdapter {
        private String[] groups;
        private String[][] childs;

        public MyAdapter(String[] groups, String[][] childs) {
            this.groups = groups;
            this.childs = childs;
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return childs[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return groups[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return childs[i][i1];
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
            groupViewHolder.mTextView.setText(groups[groupPosition]);
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
            childViewHolder.mTextView.setText(childs[groupPosition][childPosition]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
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
