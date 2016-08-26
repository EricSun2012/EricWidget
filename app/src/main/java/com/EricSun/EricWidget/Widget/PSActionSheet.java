package com.EricSun.EricWidget.Widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EricSun.EricWidget.R;


/**
 * 仿ios弹窗
 */
public class PSActionSheet {

	public static Dialog showSheet(Context mContext, String title,
								   String positiveButton,
								   final OnActionSheetClickListener positiveListener,
								   String[] otherButtons,
								   final OnActionSheetClickListener[] otherButtonListeners) {

		final Dialog dialog = new Dialog(mContext, R.style.actionSheetAnimation);

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.actionsheet, null);

		LinearLayout actionsheetLayout = (LinearLayout) layout
				.findViewById(R.id.actionsheetLayout);

		int count = 0;
		if (null != title && !title.equalsIgnoreCase("")) {
			count++;
		}
		if (null != positiveButton && !positiveButton.equalsIgnoreCase("")) {
			count++;
		}
		if (null != otherButtons) {
			count += otherButtons.length;
		}

		int begin = 0;
		if (null != title && !title.equalsIgnoreCase("")) {
			TextView textView = new TextView(mContext);
			textView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, dip2px(mContext, 45)));
			textView.setPadding(0, dip2px(mContext, 10), 0, 0);
			textView.setText(title);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(mContext.getResources().getColor(
					R.color.actionsheettitle));
			if (count > 1) {
				textView.setBackgroundResource(R.drawable.actionsheet_top);
			} else {
				textView.setBackgroundResource(R.drawable.actionsheet_single);
			}
			actionsheetLayout.addView(textView, begin);
			begin++;
		}
		if (null != positiveButton && !positiveButton.equalsIgnoreCase("")) {
			TextView textView = new TextView(mContext);
			textView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, dip2px(mContext, 45)));
			textView.setPadding(0, dip2px(mContext, 10), 0, 0);
			textView.setText(positiveButton);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(mContext.getResources().getColor(
					R.color.actionsheetblue));
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					positiveListener.onActionSheetClick();
					dialog.dismiss();
				}
			});
			textView.setClickable(true);
			if (begin == 0 && count == 1) {
				textView.setBackgroundResource(R.drawable.actionsheet_single);
			} else if (begin == 0 && count > 1) {
				textView.setBackgroundResource(R.drawable.actionsheet_top);
			} else if (begin > 0 && begin < count - 1) {
				textView.setBackgroundResource(R.drawable.actionsheet_mid);
			} else {
				textView.setBackgroundResource(R.drawable.actionsheet_bottom);
			}
			actionsheetLayout.addView(textView, begin);
			begin++;
		}
		if (null != otherButtons) {
			for (int i = 0; i < otherButtons.length; i++) {
				final int index = i;
				TextView textView = new TextView(mContext);
				textView.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, dip2px(mContext, 45)));
				textView.setPadding(0, dip2px(mContext, 10), 0, 0);
				textView.setText(otherButtons[i]);
				textView.setGravity(Gravity.CENTER);
				textView.setTextColor(mContext.getResources().getColor(
						R.color.actionsheetblue));
				textView.setClickable(true);
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						otherButtonListeners[index].onActionSheetClick();
						dialog.dismiss();
					}
				});
				if (begin == 0 && count == 1) {
					textView.setBackgroundResource(R.drawable.actionsheet_single);
				} else if (begin == 0 && count > 1) {
					textView.setBackgroundResource(R.drawable.actionsheet_top);
				} else if (begin > 0 && begin < count - 1) {
					textView.setBackgroundResource(R.drawable.actionsheet_mid);
				} else {
					textView.setBackgroundResource(R.drawable.actionsheet_bottom);
				}
				actionsheetLayout.addView(textView, begin);
				begin++;

			}
		}

		TextView cancel = (TextView) layout.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}

		});

		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);
		layout.setBackgroundResource(R.color.color_black);
		layout.setGravity(Gravity.BOTTOM);
		Window w = dialog.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		
		lp.alpha = 0.6f;
		lp.x = 0;
//		final int cMakeBottom = -1000;
//		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dialog.onWindowAttributesChanged(lp);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(layout);
		dialog.show();

		return dialog;
	}

	public interface OnActionSheetClickListener {
		void onActionSheetClick();
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
