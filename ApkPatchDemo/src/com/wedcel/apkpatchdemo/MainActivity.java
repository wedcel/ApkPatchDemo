package com.wedcel.apkpatchdemo;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.wedcel.apkpatchdemo.util.ApkUtils;
import com.wedcel.apkpatchdemo.util.PatchUtils;
import com.wedcel.apkpatchdemo.util.SignUtils;

public class MainActivity extends Activity {
	//��ɢ�ڼ� ����ֱ��load�� ��ʵ��Ŀ������ģ��
	static {
		System.loadLibrary("ApkPatchDemo");
	}
	/**������patch��λ�� demo��ֱ�Ӱ�patch.apk����sd���ĸ�Ŀ¼��*/
	private static String patchPath = Environment.getExternalStorageDirectory() + File.separator+"patch.apk";
	private static String newApkPath = Environment.getExternalStorageDirectory() + File.separator+"newApk.apk";
	private Button update;
	private ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		update = (Button)findViewById(R.id.update);
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 /*	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);  
				builder.setMessage("�����µĸ��£����ȷ�����£�ȡ����ȡ�����£�")  
				        .setCancelable(false)  
				       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
				            public void onClick(DialogInterface dialog, int id) {  
				            	update();
				            }  
				       })  
				       .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
				           public void onClick(DialogInterface dialog, int id) {  
				                 dialog.cancel();  
				           }  
				       });  
				 AlertDialog alert = builder.create();  
				 alert.show();*/
					Toast.makeText(MainActivity.this,
		"���Ѿ������°汾��",
		Toast.LENGTH_LONG).show();
				
			}
		});
		( (Button)findViewById(R.id.second)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,SecondActivity.class));
			}
		});
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���÷��ΪԲ�ν�����
		mProgressDialog.setMessage("�°�Apk�ϳ��У����Ժ�...");
		mProgressDialog.setCancelable(false);
	}

	/**
	 * ����
	 */
	private void update(){
		
		
		File patchFile = new File( patchPath );
		if (!patchFile.exists()) {
			Toast.makeText(this,
					"sdk���¸�Ŀ¼����patch.apk�ļ�",
					Toast.LENGTH_LONG).show();
		}else{
			new PatchAsyncTask().execute();
		}
		
	}
	
	private class PatchAsyncTask extends  AsyncTask<Integer, Void, Integer>{

		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			
			String oldApkSource = ApkUtils.getSourceApkPath(MainActivity.this);
			if (!TextUtils.isEmpty(oldApkSource)) {
				// 0 Ϊ�ϳɳɹ� ����Ϊʧ��
				int patchResult = PatchUtils.patch(oldApkSource, newApkPath,patchPath);

				if (patchResult == 0) {
					String signatureNew = SignUtils.getUnInstalledApkSignature(newApkPath);
					String signatureSource = SignUtils.InstalledApkSignature(MainActivity.this,
							MainActivity.this.getPackageName());
					
					if ( !TextUtils.isEmpty(signatureNew) && !TextUtils.isEmpty(signatureSource) && signatureNew.equals(signatureSource)) {
						
						ApkUtils.installApk(MainActivity.this, newApkPath);
						return 1;
					} else {
						return -1;
					
					}
				}else{
					return -2;
					
				}
			}else{
				return -3;
			}
			
			 
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mProgressDialog!=null &&  mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			switch(result){
			case 1:
				Toast.makeText(MainActivity.this,
						"��apk�Ѻϳɳɹ���",
						Toast.LENGTH_LONG).show();
				break;
			case -1:
				Toast.makeText(MainActivity.this, "��apk�Ѻϳ�ʧ�ܣ�ǩ����һ��",
						Toast.LENGTH_LONG).show();
				break;
				
			case -2:
				Toast.makeText(MainActivity.this, "��apk�Ѻϳ�ʧ�ܣ�ǩ����һ��",
						Toast.LENGTH_LONG).show();
				break;
			case -3:
				Toast.makeText(MainActivity.this,
						"�޷���ȡ��app�ĵ�Դapk�ļ�",
						Toast.LENGTH_LONG).show();
				break;
			}
			
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (mProgressDialog!=null && !mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
