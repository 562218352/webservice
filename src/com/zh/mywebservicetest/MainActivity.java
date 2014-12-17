package com.zh.mywebservicetest;

import java.util.ArrayList;
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

public class MainActivity extends Activity {

	 private List<String> provinceList = new ArrayList<String>();  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		final ListView provinces = (ListView)findViewById(R.id.provinces);
		WebServiceUtils.callWebService(WebServiceUtils.URL, "getSupportProvince", null, new WebServiceCallBack(){
			@Override
			public void callback(SoapObject result) {
				if(result != null){
					provinceList = SoapAnalysisor.extract(result, "getSupportProvinceResult");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, provinceList);
					provinces.setAdapter(adapter);
					
				}else{
					Toast.makeText(MainActivity.this, "获取省份数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		provinces.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String province = provinceList.get(position);
				Intent intent = new Intent(MainActivity.this,CityActivity.class);
				intent.putExtra("province", province);
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
