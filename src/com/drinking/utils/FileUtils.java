package com.drinking.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.android.drawmemo.GestureBox;
import com.android.drawmemo.R;
import com.android.gestureview.Gesture;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

public class FileUtils {
	public static boolean Exists(String path)
	{
		File f=new File(path);
		return f.exists();
		
	}
	public static boolean CreateFolder(String path)
	{
		File f=new File(path); //"/sdcard/DoodleMemo/saved/test"
		try{ 
		        
			if(!f.exists())//���ж�Ŀ¼�Ƿ���ڣ� 
			{
				return f.mkdirs();//Ŀ¼�����ڽ���Ŀ¼�� 
				
			}
			
			}
		catch(Exception   e)
		{
			e.printStackTrace();
			Log.i("CreateFolder", "fail");
		}
		return false;
	}
	public static boolean DeleteAllFiles(String path)
	{
		File   f=new   File(path);//���ļ�Ŀ¼ ����"/sdcard/memo/saved/"
		boolean del=false;
		try{ 
			if(f.exists())
			{
				File[] files=f.listFiles();

				for(int i=0;i<files.length;i++)
				{
					if(files[i].isFile())
					{
						del=files[i].delete();
					}
				}				
			}
			return del;
		}
		
		catch(Exception   e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	public static boolean ResourceToSD(Resources res,String path)
	{
		FileOutputStream out;
		try {
			//Ч������ ��֪����ô��
			for(int i=0;i<4;i++)
			{
				out = new FileOutputStream(path+"/memoybg"+Integer.toString(i)+".png");
				BitmapFactory.decodeResource(res, R.drawable.year1+i).compress(Bitmap.CompressFormat.PNG, 90, out);
			}
			
			return true;
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	public static boolean SaveBitmapToSD(String path ,Bitmap mbitmap)
	{           	
            	try
            	{	
        	    	FileOutputStream out = new FileOutputStream(path);
        	    	return mbitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        	    	
            	}
        		catch(IOException e){
        			return false;
        		}	
	}
	public static File getFile(String path)
	{
		File file=new File(path);
		return file;
	}
	public static Bitmap readToNormal(String filepath,int width,int height,int numsample) {
		
		ArrayList<Path> pathlist=new ArrayList<Path>();
        final Bitmap bitmap = Bitmap.createBitmap(300,300 , Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);
        
		try {
			DataInputStream in=new DataInputStream(new FileInputStream(filepath));
			int j=0,k=0;
			try {
				int count=in.readInt();
				for(int i=0;i<count;i++)
				{
					 // not use but read
					 in.readInt();
			         in.readInt();
			         in.readInt();
			         in.readInt();
			         in.readInt();
			         in.readFloat();
			         //read path
			        Path tempPath=Gesture.deserialize(in).toNormalSize(width, height, numsample);
			        
		        	tempPath.offset(50*k,10+j);
		        	if(i%6==0&&i!=0){
		        		j+=50;
		        	}
		        	k=i%6;
		        	canvas.drawPath(tempPath, paint);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		


        

        	

        	
        
		
		
		return bitmap;
	}

}
