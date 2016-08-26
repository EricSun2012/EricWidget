package com.EricSun.EricWidget.UI.ActionSheetFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Widget.PSActionSheet;

/**
 * Created by Eric on 16/8/26.
 */
public class ActionsheetFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_actionsheet, null);

        TextView title = (TextView) view.findViewById(R.id.txt_title);
        title.setText("表单");

        view.findViewById(R.id.bt_operation).setOnClickListener(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private void showActionsheetView() {
        PSActionSheet.showSheet(ct, "卖萌标题党", "热键-要吗?",
                new PSActionSheet.OnActionSheetClickListener() {
                    @Override
                    public void onActionSheetClick() {
                        showToast("<响应要吗>你想要还是想要更多?");
                    }
                }, new String[]{"热键-没良心的", "热键-死鬼"},
                new PSActionSheet.OnActionSheetClickListener[]{
                        new PSActionSheet.OnActionSheetClickListener() {
                            @Override
                            public void onActionSheetClick() {
                                showToast("<响应没良心的>还不快点");
                            }
                        },
                        new PSActionSheet.OnActionSheetClickListener() {
                            @Override
                            public void onActionSheetClick() {
                                showToast("<响应死鬼>快去干活");
                            }
                        }
                });
    }


    @Override
    protected void processClick(View v) {
        int vId = v.getId();
        if (vId == R.id.bt_operation) {
            showActionsheetView();
        }
    }
}
