package com.example.testfileobserver;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findImageDires("/");
	}
	
	private Set<File> findImageDires(String path){
		Set<File> imageFileDirs = new HashSet<File>();
		File file = new File(path);
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
		queue.add(file);
		while(! queue.isEmpty()){
			File f = queue.poll();
			File listFiles[] = f.listFiles();
			if(listFiles != null){
				for(File a : listFiles){
					Log.e("HOGEHOGE", a.getAbsolutePath());
					if(a.isDirectory()){
						if(! ( a.getAbsolutePath().startsWith("/sys") || a.getAbsolutePath().startsWith("/proc"))){
							try {
								if( a.getAbsolutePath().equals(a.getCanonicalPath())){
									queue.add(a);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else if(a.isFile()){
						if(! imageFileDirs.contains(a.getParentFile())){
							Bitmap b = BitmapFactory.decodeFile(a.getAbsolutePath());
							if(b != null){
								imageFileDirs.add(a.getParentFile());
								b.recycle();
							}
						}
					}
				}
			}
		}
		
		Log.e("HOGEHOGE", "**********");
		Log.e("HOGEHOGE", "imageFileDirs");
		
		for(File f : imageFileDirs){
			Log.e("HOGEHOGE", f.getAbsolutePath());
		}
		Log.e("HOGEHOGE", "imageFileDirs = " + imageFileDirs.size());
		Log.e("HOGEHOGE", "**********");
		return imageFileDirs;
	}
}
