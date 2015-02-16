package nl.my89.roosters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;

public class StartUpActivity extends Activity {
	public static Roster monday;
	public static Roster tuesday;
	public static Roster wednesday;
	public static Roster thursday;
	public static Roster friday;
	public static Roster time;
	
	public static final String classUrlArray[] = {
		"http://marceldev.nl/rooster_leerling_url_320klassen_DEV41A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_DEV42A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_DEV43A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MBI33A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MBI33B.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MEDB41A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MEDB41B.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MEDB42A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MEDB43A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MEDB43B.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MEDB44A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MI21A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MI22A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MV41A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MV42A.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MV43GV.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MV43IV.htm",
		"http://marceldev.nl/rooster_leerling_url_320klassen_MV44A.htm"
	};
	
	public static final String teacherUrlArray[] = {
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_bitj.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_boed0.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_boyk.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_ceta.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_dema.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_hala.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_howi.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_huhe.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_joed.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_kaju.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_kuke.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_ovak.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_paro.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_rewi.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_sore.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_teyv.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_uyeh.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_voja.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_wiea.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_zeri.htm",
		"http://studienet.fcroc.nl/bbcswebdav/pid-240622-dt-content-rid-455049_2/courses/ICT_Heerenveen/roosters%20henk/320docenten_zuja.htm"
	};
	
	public static int urlInt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_up);
		 
		 if (monday != null) {
			 time = null;
			 monday = null;
			 tuesday = null;
			 wednesday = null;
			 thursday = null;
			 friday = null;
		 }
		
		new RetreiveData().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_up, menu);
		return true;
	}
	
	public class RetreiveData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			SharedPreferences sharedPref = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			urlInt = Integer.parseInt(sharedPref.getString("class_list", "0"));

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("https://studienet.fcroc.nl/");

			String html = "";

			try {
				List<NameValuePair> data = new ArrayList<NameValuePair>(10);
				data.add(new BasicNameValuePair("user_id", ""));
				data.add(new BasicNameValuePair("remote-user", ""));
				data.add(new BasicNameValuePair("password", ""));
				data.add(new BasicNameValuePair("one_time_token", ""));
				data.add(new BasicNameValuePair("new_loc", ""));
				data.add(new BasicNameValuePair("login", "Aanmelden"));
				data.add(new BasicNameValuePair("encoded_pw_unicode", ""));
				data.add(new BasicNameValuePair("encoded_pw", ""));
				data.add(new BasicNameValuePair("auth_type", ""));
				data.add(new BasicNameValuePair("action", "login"));

				post.setEntity(new UrlEncodedFormEntity(data));

				HttpResponse response = client.execute(post);
				response.getEntity().getContent().close();

				HttpGet request;

				if (Integer.parseInt(sharedPref.getString("type_list", "0")) == 0) {
					request = new HttpGet(classUrlArray[urlInt]);
				} else {
					request = new HttpGet(teacherUrlArray[urlInt]);
				}

				response = client.execute(request);

				InputStream in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				StringBuilder str = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					str.append(line);
				}

				in.close();

				html = str.toString();

				monday = new Roster();
				tuesday = new Roster();
				wednesday = new Roster();
				thursday = new Roster();
				friday = new Roster();
				time = new Roster();

				Roster rosterArray[] = { time, monday, tuesday, wednesday,
						thursday, friday };

				Document doc = Jsoup.parse(html);
				Element table = doc.select("table").get(1);

				Elements rows = table
						.select("table[border=3] > tbody > tr:has(td)");

				int index = 0;
				int l = 0;

				Roster currentRoster;

				for (int i = 1; i < rows.size(); i++) {
					Elements columns = rows.get(i).select(":root > td");
					l = 0;

					for (int j = 0; j < columns.size(); j++) {
						if (columns.get(j).hasAttr("colspan")) {
							if (Integer
									.parseInt(columns.get(j).attr("colspan")) == 3) {
								if (columns.get(j - 1).hasAttr("colspan")) {
									if (Integer.parseInt(columns.get(j - 1)
											.attr("colspan")) == 3) {
										l--;
										for (int k = 0; k < Integer
												.parseInt(columns.get(j).attr(
														"rowspan")) / 2; k++) {
											if (k == 0) {
												rosterArray[j + l].map
														.put(rosterArray[j + l].map
																.size() - 1,
																rosterArray[j
																		+ l].map
																		.get(rosterArray[j
																				+ l].map
																				.size() - 1)
																		+ "\n\n"
																		+ columns
																				.get(j)
																				.text());
											} else {
												rosterArray[j + l].map.put(
														rosterArray[j + l].map
																.size(),
														columns.get(j).text());
											}
										}
										continue;
									}
								}
							}
						}

						String cellString = columns.get(j).text();
						currentRoster = rosterArray[j + l];

						while (currentRoster.map.size() > index) {
							currentRoster = rosterArray[j + ++l];
						}

						if (j == 0)
							cellString = cellString.substring(2).replace(" - ",
									"\n");

						for (int k = 0; k < Integer.parseInt(columns.get(j)
								.attr("rowspan")) / 2; k++) {
							currentRoster.map.put(currentRoster.map.size(),
									cellString);
						}
					}

					index++;
				}
			} catch (Exception e) {
				// Log.e("Exception", e.toString());
				monday = new Roster();
				tuesday = new Roster();
				wednesday = new Roster();
				thursday = new Roster();
				friday = new Roster();
				time = new Roster();
			}

			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intent);
			finish();
			return html;
		}
	}

}
