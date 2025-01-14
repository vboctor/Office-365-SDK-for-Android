/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.readwritelistsample.tasks;

import com.microsoft.readwritelistsample.AssetApplication;
import com.microsoft.readwritelistsample.datasource.ListItemsDataSource;
import com.microsoft.readwritelistsample.viewmodel.CarListViewItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteCarTask.
 */
public class DeleteCarTask extends AsyncTask<CarListViewItem, Void, Void> {

	/** The m source. */
	private ListItemsDataSource mSource;
	
	/** The m activity. */
	private Activity mActivity;
	
	/** The m dialog. */
	private ProgressDialog mDialog;
	
	/** The m application. */
	private AssetApplication mApplication;
	
	/** The m throwable. */
	private Throwable mThrowable;

	/**
	 * Instantiates a new delete car task.
	 *
	 * @param activity the activity
	 */
	public DeleteCarTask(Activity activity) {
		mActivity = activity;
		mDialog = new ProgressDialog(mActivity);
		mApplication = (AssetApplication) activity.getApplication();
		mSource = new ListItemsDataSource(mApplication);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {
		mDialog.setTitle("Deleting car...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}

		if (mThrowable == null) {
			Toast.makeText(mActivity, "Car deleted!", Toast.LENGTH_SHORT).show();
		} else {
			mApplication.handleError(mThrowable);
		}

		NavUtils.navigateUpFromSameTask(mActivity);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(CarListViewItem... params) {
		CarListViewItem viewItem = params[0];
		if (viewItem != null) {
			try {
				mSource.deleteCar(viewItem);
			} catch (Throwable t) {
				mThrowable = t;
			}
		} else {
			mThrowable = new IllegalArgumentException(
					"params argument must contain at least a CarListViewItem");
		}
		return null;
	}
}
