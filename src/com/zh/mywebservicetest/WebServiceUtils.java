package com.zh.mywebservicetest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.security.auth.PrivateCredentialPermission;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsServiceConnectionSE;

import android.os.Handler;
import android.os.Message;

public class WebServiceUtils {
	
	public final static String URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
	
	private final static String NAME_SPACE = "http://WebXml.com.cn/";
	
	private static ExecutorService eService = Executors.newFixedThreadPool(5);
	
	public static void callWebService(String url , final String methodName , HashMap<String, String> props 
			, final WebServiceCallBack webServiceCallBack){
		final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
		SoapObject so = new SoapObject(NAME_SPACE, methodName);
		
		//配置参数
		if(props!=null){
			Iterator<String> iterator = props.keySet().iterator();
			while(iterator.hasNext()){
				String key  = iterator.next();
				String value = props.get(key);
				so.addProperty(key, value);
			}
		}
		
		final SoapSerializationEnvelope sEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sEnvelope.setOutputSoapObject(so);
		sEnvelope.dotNet = true;

		httpTransportSE.debug = true;
		
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.obj!=null){
					webServiceCallBack.callback((SoapObject)msg.obj);
				}
			}
		};
		
		
		eService.submit(new Runnable() {
			@Override
			public void run() {
				SoapObject soapObject = null;
				try {
					httpTransportSE.call(NAME_SPACE + methodName, sEnvelope);
					if(sEnvelope.getResponse()!=null){
						soapObject = (SoapObject)sEnvelope.bodyIn;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					Message msg = handler.obtainMessage();
					msg.obj = soapObject;
					handler.sendMessage(msg);
				}
			}
		});
		
	}
	
			
	

	public interface WebServiceCallBack{
		public void callback(SoapObject result);
		
	}
}
