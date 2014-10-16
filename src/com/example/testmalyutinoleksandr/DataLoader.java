package com.example.testmalyutinoleksandr;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class DataLoader {

	
	

	
	public static ArrayList <Drawable> getIconsRandomly(Context con){
		
		ArrayList <Drawable> listToReturn = new ArrayList<Drawable>();
		
		int[] drawables = new int[] { R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4,
				R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_10 };
		
		Drawable d;
		
		for (int i = 0; i < drawables.length; i++) {
			d = con.getResources().getDrawable(drawables[i]);
			listToReturn.add(d);
		}
		
		Collections.shuffle(listToReturn);
		
		return listToReturn;
		
	}
	
	
	public static ArrayList<Drawable> getBackgrounds(Context con){
		
		ArrayList <Drawable> listToReturn = new ArrayList<Drawable>();
		
		int[] drawables = new int[] { R.drawable.btn_1, R.drawable.btn_2, R.drawable.btn_3, R.drawable.btn_4,
				R.drawable.btn_5, R.drawable.btn_6, R.drawable.btn_7, R.drawable.btn_8, R.drawable.btn_9, R.drawable.btn_10 };
		
		Drawable d;
		
		for (int i = 0; i < drawables.length; i++) {
			d = con.getResources().getDrawable(drawables[i]);
			listToReturn.add(d);
		}
		
		return listToReturn;
		
	}
	
	
	
	public static String [] getTitles(Context con){
		
		String [] listToReturn = new String[10];
		
		int[] strings = new int[] { R.string.title_1, R.string.title_2, R.string.title_3, R.string.title_4,
				R.string.title_5, R.string.title_6, R.string.title_7, R.string.title_8, R.string.title_9, R.string.title_10 };
		
		for (int i = 0; i < strings.length; i++) {
			listToReturn[i] = con.getResources().getString(strings[i]);
		}
		
		return listToReturn;
		
	}
	
	
}
