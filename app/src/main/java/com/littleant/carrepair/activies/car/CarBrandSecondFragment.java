package com.littleant.carrepair.activies.car;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.carbrand.CarBrandLetterBean;
import com.littleant.carrepair.request.bean.carbrand.CarTypeSet;
import com.mh.core.tools.MHToast;

import java.util.List;

public class CarBrandSecondFragment extends Fragment {

    private CarBrandLetterBean letterBean;
    public static final String  CAR_BRAND_LETTER_BEAN = "car_brand_letter_bean";
    private ListView list;

    public CarBrandSecondFragment() {
    }

    public static CarBrandSecondFragment newInstance(CarBrandLetterBean letterBean) {
        CarBrandSecondFragment fragment = new CarBrandSecondFragment();
        Bundle args = new Bundle();
        args.putSerializable(CAR_BRAND_LETTER_BEAN, letterBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            letterBean = (CarBrandLetterBean) getArguments().getSerializable(CAR_BRAND_LETTER_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        list = view.findViewById(R.id.llv_list);
        if(letterBean != null) {
            List<CarTypeSet> cartype_set = letterBean.getCartype_set();
            if(cartype_set != null && cartype_set.size() > 0) {
                final MyAdapter adapter = new MyAdapter(cartype_set);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CarTypeSet carTypeSet = (CarTypeSet) adapter.getItem(position);
                        if(carTypeSet.getCarstyle_set() == null && carTypeSet.getCarstyle_set().size() < 1) {
                            MHToast.showS(getContext(), R.string.car_brand_empty);
                            return;
                        }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.acb_fragment, CarBrandThirdFragment.newInstance(carTypeSet), CarBrandThirdFragment.class.getSimpleName());
                        transaction.addToBackStack(null); //将当前的事务添加到了回退栈
                        transaction.commit();
                    }
                });
            }
        }
        return view;
    }

    private class MyAdapter extends BaseAdapter {
        List<CarTypeSet> mCartype_set;

        public MyAdapter(List<CarTypeSet> cartype_set) {
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

            CarTypeSet carTypeSet = mCartype_set.get(position);
            if(carTypeSet != null) {
                viewHolder.mTextView.setText(carTypeSet.getName());
            }

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
