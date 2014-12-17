package com.zh.mywebservicetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.zh.mywebservicetest.WebServiceUtils.WebServiceCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {
	
	private final static String TAG = "Weather";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		
		final TextView txt  =(TextView)findViewById(R.id.txt);
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("theCityName", getIntent().getCharSequenceExtra("city").toString());
		
		WebServiceUtils.callWebService(WebServiceUtils.URL, "getWeatherbyCityName", props, new WebServiceCallBack(){
			@Override
			public void callback(SoapObject result) {
				if(result != null){
					StringBuffer sf = SoapAnalysisor.extractStr(result,"getWeatherbyCityNameResult");
					String value = (sf==null)?"":sf.toString();
					Log.i(TAG, value);
					txt.setText(value);
				}else{
					Toast.makeText(WeatherActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
