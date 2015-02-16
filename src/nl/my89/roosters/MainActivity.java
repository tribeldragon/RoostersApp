package nl.my89.roosters;

import java.util.Calendar;
import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public static Roster monday;
	public static Roster tuesday;
	public static Roster wednesday;
	public static Roster thursday;
	public static Roster friday;
	public static Roster time;
	
	public static int[] timeObjects = { R.id.time1, R.id.time2, R.id.time3,
			R.id.time4, R.id.time5, R.id.time6, R.id.time7, R.id.time8,
			R.id.time9 };
	public static int[] classObjects = { R.id.class1, R.id.class2, R.id.class3,
			R.id.class4, R.id.class5, R.id.class6, R.id.class7, R.id.class8,
			R.id.class9 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		monday = StartUpActivity.monday;
		tuesday = StartUpActivity.tuesday;
		wednesday = StartUpActivity.wednesday;
		thursday = StartUpActivity.thursday;
		friday = StartUpActivity.friday;
		time = StartUpActivity.time;
		
		int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		
		switch (today) {
		case Calendar.SATURDAY:
		case Calendar.SUNDAY:
		case Calendar.MONDAY:
			today = 0;
			break;
		case Calendar.TUESDAY:
			today = 1;
			break;
		case Calendar.WEDNESDAY:
			today = 2;
			break;
		case Calendar.THURSDAY:
			today = 3;
			break;
		case Calendar.FRIDAY:
			today = 4;
			break;
		}
		mViewPager.setCurrentItem(today);
		
		if (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				getApplicationContext()).getString("type_list", "0")) == 0) {
			this.setTitle(getResources().getStringArray(
					R.array.pref_class_titles)[Integer
					.parseInt(PreferenceManager.getDefaultSharedPreferences(
							this).getString("class_list", "0"))].toUpperCase(Locale.getDefault()));
		} else {
			this.setTitle(getResources().getStringArray(
					R.array.pref_teacher_titles)[Integer
					.parseInt(PreferenceManager.getDefaultSharedPreferences(
							this).getString("class_list", "0"))].toUpperCase(Locale.getDefault()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.refresh_settings:
			intent = new Intent(getApplicationContext(), StartUpActivity.class);
			startActivity(intent);
			this.finish();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		Intent intent = new Intent(getApplicationContext(), StartUpActivity.class);
		startActivity(intent);
		this.finish();
		super.onActivityResult(arg0, arg1, arg2);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}
	
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			
			int s = getArguments().getInt(ARG_SECTION_NUMBER);
			
			Roster currentRoster = null;
			
			switch (s) {
			case 1:
				currentRoster = monday;
				break;
			case 2:
				currentRoster = tuesday;
				break;
			case 3:
				currentRoster = wednesday;
				break;
			case 4:
				currentRoster = thursday;
				break;
			case 5:
				currentRoster = friday;
				break;
			}
			
			TextView tv;
			
			for (int i = 0; i < 9; i++) {
				tv = (TextView) rootView.findViewById(timeObjects[i]);
				tv.setText(time.map.get(i));
				
				tv = (TextView) rootView.findViewById(classObjects[i]);
				tv.setText(currentRoster.map.get(i));
			}

			return rootView;
		}
	}
}
