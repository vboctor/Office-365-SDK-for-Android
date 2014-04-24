package com.microsoft.mailservice;

//import com.microsoft.mailservice.adapters.ContactItemAdapter;
import com.microsoft.mailservice.tasks.RetrieveContactsTask;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ContactsActivity extends Activity{

	//  DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_contact);

//		mDemoCollectionPagerAdapter =
//				new DemoCollectionPagerAdapter(
//						getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		new RetrieveContactsTask(ContactsActivity.this, Authentication.getCurrentCredentials()).execute();
	}

	public void setListAdapter(PagerAdapter adapter) {		
		mViewPager.setAdapter(adapter);		
	}
}