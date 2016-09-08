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

package com.EricSun.EricWidget.UI.DownloadFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.EricSun.EricWidget.Framework.Fragment.BaseFragment;
import com.EricSun.EricWidget.R;

import java.io.File;

import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;

/**
 * Created by Eric on 16/9/6.
 */
public class DownloadFragment extends BaseFragment {

    private String downloadUrl = "http://dd.shouji.com.cn/app/soft/2016/20160321/0889512027.apk?filename=com.android2.calculator3_5.1.1_87.apk&auth_key=edf75fed50433b2629147573ce04e9c4";
    private TextView progressText;
    private ProgressBar progressBar;
    private Button downloadButton;
    private boolean downloading;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        setTitles("下载&安装QQ");
        setTitleTextColor(getResources().getColor(R.color.color_white));
        setTitleBarColor(getResources().getColor(R.color.color_mediumTurquoise));

        View view = inflater.inflate(R.layout.fragment_download, null);

        progressBar = (ProgressBar) view.findViewById(R.id.download_progressBar);
        progressText = (TextView) view.findViewById(R.id.download_progress);
        downloadButton = (Button) view.findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private void setProgress(int progress) {
        progressBar.setProgress(progress);
        progressText.setText(progress + "%");
    }

    protected void downloadFile(String url) {

        File targetFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "mobileqq_android.apk");

        HttpRequest.download(url, targetFile, new FileDownloadCallback() {
            @Override
            public void onStart() {
                super.onStart();
                downloading = true;
            }

            @Override
            public void onProgress(int progress, long networkSpeed) {
                super.onProgress(progress, networkSpeed);
                setProgress(progress);
            }

            @Override
            public void onDone() {
                super.onDone();
                downloading = false;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "mobileqq_android.apk")),
                        "application/vnd.android.package-archive");
                startActivity(intent);

            }

            @Override
            public void onFailure() {
                super.onFailure();
                downloading = false;
                showToast("失败了");
            }
        });
    }

    @Override
    protected void processClick(View v) {

        int vId = v.getId();
        if (vId == R.id.downloadButton) {
            if (downloading == true) {
                showToast("正在下载");
                return;
            }
            downloadFile(downloadUrl);
        }
    }
}
