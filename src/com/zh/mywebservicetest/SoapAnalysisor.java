package com.zh.mywebservicetest;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.ListActivity;
import android.util.Log;

public class SoapAnalysisor {
	
	private final static String TAG = "Weather";
	
	
	public static List<String> extract(SoapObject o, String prorName){
		List<String> list = new ArrayList<String>();
		if(o!=null){
			SoapObject tmpObject = (SoapObject)o.getProperty(prorName);
			Log.i(TAG, tmpObject.toString());
			if(tmpObject!=null){
				for(int i=0 ; i<tmpObject.getPropertyCount();i++){
					list.add(tmpObject.getPropertyAsString(i).toString());
				}
			}
		}else{
			return null;
		}
		return list;
	}

	public static StringBuffer extractStr(SoapObject result, String prorName) {
		StringBuffer sBuffer = new StringBuffer();
		SoapObject vObject = (SoapObject)result.getProperty(prorName);
		Log.i(TAG, vObject.toString());
		if(vObject!=null){
			for(int i=0; i<vObject.getPropertyCount(); i++){  
				sBuffer.append(vObject.getProperty(i)).append("\r\n");  
            }  
		}
		return sBuffer;
	}

}
