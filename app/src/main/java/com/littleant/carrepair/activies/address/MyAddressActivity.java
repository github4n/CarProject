package com.littleant.carrepair.activies.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.MyAddressListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.address.AddressQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

import static com.littleant.carrepair.activies.address.AddAddressActivity.RESULT_CODE_SUCCESS;

/**
 * 我的地址
 */
public class MyAddressActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_ADD_ADDRESS = 100;
    private static final int REQUEST_CODE_MODIFY_ADDRESS = 101;
    private RecyclerView mList;
    private List<MyAddressListBean.AddressInfo> data;
    public static final String USER_ADDRESS_BEAN = "user_address_bean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestAddressList();
    }

    private void requestAddressList() {
        AddressQueryAllCmd addressQueryAllCmd = new AddressQueryAllCmd(mContext, ParamsConstant.QueryType.NONE);
        addressQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MyAddressListBean addressListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MyAddressListBean.class);
                        data = addressListBean.getData();
                        if(data != null && data.size() > 0){
                            mList.setAdapter(new MyAdapter(data));
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, addressQueryAllCmd);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_my_address;
    }

    @Override
    protected int getOptionBackgroundId() {
        return R.drawable.address_add;
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.ma_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mOptionContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_option_content:
                Intent intent = new Intent(mContext, AddAddressActivity.class);
                MyAddressActivity.this.startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_ADDRESS && resultCode == RESULT_CODE_SUCCESS) {
            requestAddressList();
        } else if(requestCode == REQUEST_CODE_MODIFY_ADDRESS && resultCode == RESULT_CODE_SUCCESS) {
            requestAddressList();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<MyAddressListBean.AddressInfo> list;

        public MyAdapter(List<MyAddressListBean.AddressInfo> list) {
            this.list = list;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_item, parent, false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
            MyAddressListBean.AddressInfo addressInfo = list.get(position);
            if(addressInfo != null) {
                holder.lai_address.setText(addressInfo.getAddress());
                holder.lai_name.setText(addressInfo.getName());
                holder.lai_phone.setText(addressInfo.getPhone());
                holder.lai_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MHToast.showS(mContext, "编辑" + holder.getAdapterPosition());
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                        intent.putExtra(USER_ADDRESS_BEAN, list.get(position));
                        MyAddressActivity.this.startActivityForResult(intent, REQUEST_CODE_MODIFY_ADDRESS);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView lai_edit, lai_name, lai_phone, lai_address;

            ViewHolder(View itemView) {
                super(itemView);
                lai_edit = itemView.findViewById(R.id.lai_edit);
                lai_name = itemView.findViewById(R.id.lai_name);
                lai_phone = itemView.findViewById(R.id.lai_phone);
                lai_address = itemView.findViewById(R.id.lai_address);
            }

        }
    }
}
