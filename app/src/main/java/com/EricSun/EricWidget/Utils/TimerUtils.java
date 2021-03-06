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

package com.EricSun.EricWidget.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TimerUtils {

    private List<TimerProcessor> processList;
    private long mDelayMs;
    private long mPeriodMs;
    private boolean switchFlag;

    private Timer mTimer;
    private TimerTask mTimerTask;

    /**
     * 鏋勯�鍑芥暟
     *
     * @param delayMs   寤舵椂
     * @param periodMs  瀹氭椂鍣ㄧ殑鍚姩闂磋窛鏃堕棿
     * @param processor 瀹氭椂澶勭悊鍣紝鐢辫皟鐢ㄨ�瀹氬埗瀹炵幇
     */
    public TimerUtils(long delayMs, long periodMs, TimerProcessor processor) {
        this(delayMs, periodMs);
        processList.add(processor);
    }

    public TimerUtils(long delayMs, long periodMs) {
        processList = new ArrayList<TimerProcessor>();
        mDelayMs = delayMs;
        mPeriodMs = periodMs;
    }

    // 娣诲姞瀹氭椂鍣ㄥ鐞嗘柟娉�
    public void addTimerProcessor(TimerProcessor processor) {
        int i = 0;
        for (; i < processList.size(); i++) {
            TimerProcessor object = processList.get(i);
            if (object.getSerialNumber() == processor.getSerialNumber()) {
                boolean result = processList.remove(object);
                break;
            }
        }

        processList.add(processor);
        if (processList.size() == 1 && switchFlag == false) {
            startTimer();
            switchFlag = true;
        }
    }

    // 鑾峰彇鐗瑰畾鐨勫鐞嗗櫒
    public TimerProcessor getTimerProcessor(int serialNumber) {
        if (processList == null) {
            return null;
        }
        for (int i = 0; i < processList.size(); i++) {
            TimerProcessor processor = processList.get(i);
            if (processor.getSerialNumber() == serialNumber) {
                return processor;
            }
        }
        return null;
    }

    // 绉婚櫎瀹氭椂鍣ㄥ鐞嗘柟娉�
    public void removeTimerProcessor(TimerProcessor processor) {
        if (processList.size() == 0 && switchFlag == true) {
            stopTimer();
            switchFlag = false;
        }
        processList.remove(processor);
    }

    // 绉婚櫎鎵�湁澶勭悊鍣�
    public void removeAllTimerProcessor() {
        stopTimer();
        processList.clear();
        switchFlag = false;
    }

    // 杩斿洖瀹氭椂鍣ㄥ紩鐢ㄨ鏁�
    public int count() {
        if (processList != null) {
            return processList.size();
        }
        return 0;
    }

    /**
     * 鍚姩瀹氭椂鍣�
     */
    public void startTimer() {

        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < processList.size(); i++) {
                    TimerProcessor timerProcessor = processList.get(i);
                    if (timerProcessor != null) {
                        timerProcessor.process();
                    }
                }
            }
        };
        mTimer.schedule(mTimerTask, mDelayMs, mPeriodMs);
    }

    /**
     * 鍋滄瀹氭椂鍣�
     */
    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}
