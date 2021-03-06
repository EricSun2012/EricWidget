/*
 * MIT License
 *
 * Copyright (c) 2016 Eric
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.EricSun.EricWidget.UI.NetworkProgressFragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;
import com.EricSun.EricWidget.Utils.BitmapUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.File;

import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Eric on 16/9/1.
 */
public class NetworkFragment extends BaseFragment {
    private ProgressBar mProgressbar;
    private Button mButton;
    private Button updateButton;
    private ImageView imageView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        setTitles("网络进度");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));

        View view = inflater.inflate(R.layout.fragment_network_progress, null);

        mProgressbar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mButton = (Button) view.findViewById(R.id.bt_download);
        mButton.setOnClickListener(this);

        updateButton = (Button) view.findViewById(R.id.bt_update);
        updateButton.setOnClickListener(this);

        imageView = (ImageView) view.findViewById(R.id.image);
        Button tokenButton = (Button) view.findViewById(R.id.bt_token);
        tokenButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    private void networkDownload() {
        String url = "http://img8.zol.com.cn/bbs/upload/10569/10568729.jpg";

        HttpRequest.download(url, new File("/sdcard/xxxbig.jpg"), new FileDownloadCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onProgress(int progress, long networkSpeed) {
                super.onProgress(progress, networkSpeed);
                mProgressbar.setProgress(progress);
                if (progress == 100) {
                    mProgressbar.setVisibility(View.GONE);
                }
                //String speed = FileUtils.generateFileSize(networkSpeed);
            }

            @Override
            public void onFailure() {
                super.onFailure();
            }

            @Override
            public void onDone() {
                super.onDone();

                Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFilePath("/sdcard/xxxbig.jpg", 200, 100);
                if (null != bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

            }

        });
    }

    private void getAccessToken() {

        String url = "";

        RequestParams params = new RequestParams();

        params.addFormDataPart("height", "12122");

        HttpRequest.post(url, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                showToast(jsonObject.toJSONString());
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }


    @Override
    protected void processClick(View v) {
        int vId = v.getId();
        if (vId == R.id.bt_download) {
            networkDownload();

        } else if (vId == R.id.bt_update) {
            mProgressbar.setVisibility(View.VISIBLE);
            mProgressbar.setProgress(0);
            imageView.setImageBitmap(null);
        } else if (vId == R.id.bt_token) {
            getAccessToken();
        }
    }
}
