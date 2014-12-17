package com.zh.mywebservicetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.zh.mywebservicetest.WebServiceUtils.WebServiceCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CityActivity extends Activity {

	 private List<String> cityList = new ArrayList<String>();  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city);
		
		
		final ListView citys = (ListView)findViewById(R.id.city);
		
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("byProvinceName", getIntent().getCharSequenceExtra("province").toString());
		
		WebServiceUtils.callWebService(WebServiceUtils.URL, "getSupportCity", props, new WebServiceCallBack(){
			@Override
			public void callback(SoapObject result) {
				if(result != null){
					List<String> tmpList = new ArrayList<String>(); 
					tmpList = SoapAnalysisor.extract(result,"getSupportCityResult");
					for(String city_code : tmpList){
						String cityName = city_code.split(" ")[0];
						cityList.add(cityName);
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(CityActivity.this, android.R.layout.simple_list_item_1, cityList);
					citys.setAdapter(adapter);
					
				}else{
					Toast.makeText(CityActivity.this, "获取城市数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		citys.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String city = cityList.get(position);
				Intent intent = new Intent(CityActivity.this,WeatherActivity.class);
				intent.putExtra("city", city);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
