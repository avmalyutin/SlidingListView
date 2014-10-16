package com.example.testmalyutinoleksandr;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	  private ListView listview;
	  private TextView textView;
	  
	  private Context con;
	  
	  private static final String LABELSKEY = "myListLabels";
	  private static final String COUNTERSKEY = "myListCounters";
	  private String values [];
	  private int counters [];

	  private static Calendar currentDate;
	  private Timer timer;
	  
	  private SimpleListAdapter adapter;
	  	  
	  private static int height;
	  private static int width;
	  private static boolean flag = true;

	  private static String text = null;
	  
	  
	  private boolean touch = true;
	  	    
	  private AbsListView view;
	  private int visibleItemCount;
	  
	  
	  static {
			
			currentDate = GregorianCalendar.getInstance();
	  }
	  
	  private int [] screenlocation = {-1,-1};
	  private ScaleAnimation scaleAnimation;
	  private AnimationTask animationTask;
	  
	  
	  public void onCreate(Bundle savedInstanceState) {
		  
		setTheme(android.R.style.Theme_Black_NoTitleBar);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    con = this;
	    
	    listview = (ListView) findViewById(R.id.myListView);
	    textView = (TextView) findViewById(R.id.timeTextView);
	    
	    if (savedInstanceState != null && savedInstanceState.containsKey(LABELSKEY))
	    {
	        values = savedInstanceState.getStringArray(LABELSKEY);
	        counters = savedInstanceState.getIntArray(COUNTERSKEY);
	    }
	    else
	    {
	    	values = DataLoader.getTitles(getApplication());
	    	counters = new int [] {0,0,0,0,0,0,0,0,0,0};
	    }
	   	    
	    adapter = new SimpleListAdapter(this, values, counters);
	    listview.setAdapter(adapter);
	        
		animationTask = new AnimationTask();
		

	    timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  
				  runOnUiThread(new Runnable() {
					  @Override
			            public void run() {
					     	  currentDate = GregorianCalendar.getInstance();
					     	  int hour = currentDate.get(Calendar.HOUR_OF_DAY);
					     	  int minutes = currentDate.get(Calendar.MINUTE);
					     	  int seconds = currentDate.get(Calendar.SECOND);
					 		  textView.setText( hour + ":" + (minutes<10?"0":"") + minutes  + ":" +  (seconds<10?"0":"") + seconds);
						  }
					  }
			        );			  
			  	}
			} , 1, 1*1000);
	    

		
	    listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				TextView counterTextView = (TextView) view.findViewById(R.id.counterTextView);
				int counter = Integer.valueOf(counterTextView.getText().toString());
				counter++;
				counterTextView.setText(counter + "");
				MainActivity.this.counters[position] = counter;
				
				Toast.makeText(getApplication(), "Нажата кнопка " + (position+1), Toast.LENGTH_SHORT).show();
			}
		});
	 
	  
		  listview.setOnItemLongClickListener(new OnItemLongClickListener() {
	
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
								
		        AlertDialog.Builder alert = new AlertDialog.Builder(con);
		        
		        alert.setTitle("Редактирование");
	            alert.setMessage("Введите текст для кнопки");
	            final EditText input = new EditText(con);
	            input.setText("");
	            alert.setView(input);

	            alert.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                      // the adapter you set in the listView.setAdapter();
	                	
	                	values[position] = input.getText().toString();
	                	SimpleListAdapter adapter = new SimpleListAdapter(con, values, counters);
					    listview.setAdapter(adapter);
	                	
	                }
	            });
	            alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                    // Canceled.
	                }
	            });


	            alert.show();
			return true;
			}
		  });
		  
		  

		  listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE)
					MainActivity.this.touch = true;
				else
					MainActivity.this.touch = false;
					

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
				if(!MainActivity.this.touch){
					
					MainActivity.this.visibleItemCount = visibleItemCount;
					MainActivity.this.functionOfDrawing(view);
				}				
				
			}		  
		  
		  });
		  
		  
		  //end of onCreate
		  };
		  
 
		  private void functionOfDrawing(AbsListView view){
			  
			  MainActivity.this.view = view;
			  MainActivity.this.visibleItemCount = 7;
			  MainActivity.this.view.getLocationOnScreen(screenlocation);
			  
			  runOnUiThread(animationTask);
			  
		  }
		  
		  
	

	  @Override
	  protected void onSaveInstanceState(Bundle outState)
	  {
	      super.onSaveInstanceState(outState);
	      outState.putStringArray(LABELSKEY, values);
	      outState.putIntArray(COUNTERSKEY, counters);
	  }
	  	  
	 
	  
	  class AnimationTask implements Runnable{

		@Override
		public void run() {
    			
				if(screenlocation[0] != 0 && screenlocation[1] != 0){
					
					
					if(MainActivity.this.visibleItemCount == 6){
						
						for(int i=0; i<MainActivity.this.visibleItemCount; i++){
							View middleWantedView = listview.getChildAt(i);
							if(middleWantedView == null)
								continue;
							if(MainActivity.flag){
	
							    MainActivity.height = middleWantedView.getHeight();
							    MainActivity.width = middleWantedView.getWidth()/2;
							    MainActivity.flag = false;
							    
							}
							
							scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, MainActivity.width*2, MainActivity.height);
							scaleAnimation.setFillAfter(false);
							
							scaleAnimation.setDuration(0);
							
							middleWantedView.setAnimation(scaleAnimation);
							middleWantedView.startAnimation(scaleAnimation);
							
						}
					}
					
					
									
					if(MainActivity.this.visibleItemCount == 7){
						
						for(int i=1; i<MainActivity.this.visibleItemCount-1; i++){
							View middleWantedView = listview.getChildAt(i);
							if(middleWantedView == null)
								continue;
							if(MainActivity.flag){
	
							    MainActivity.height = middleWantedView.getHeight();
							    MainActivity.width = middleWantedView.getWidth()/2;
							    MainActivity.flag = false;
							    
							}
							
							scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, MainActivity.width*2, MainActivity.height);
							scaleAnimation.setFillAfter(false);
							
							scaleAnimation.setDuration(0);
							
							middleWantedView.setAnimation(scaleAnimation);
							middleWantedView.startAnimation(scaleAnimation);
							
						}
						
						int [] location = {-1,-1};
						
						View wantedView = listview.getChildAt(0);
						
						wantedView.getLocationOnScreen(location);
						
						MainActivity.height = wantedView.getHeight();
					    MainActivity.width = wantedView.getWidth()/2;
												
						float scaling = (float) (((float)location[1] - (screenlocation[1]-height))/100.0);
						
						scaleAnimation = new ScaleAnimation(scaling, scaling, scaling, scaling, MainActivity.width, MainActivity.height);
						
						scaleAnimation.setDuration(0);
						scaleAnimation.setFillAfter(true);
						wantedView.setAnimation(scaleAnimation);
						wantedView.startAnimation(scaleAnimation);
						
						
						View secondWantedView = listview.getChildAt(MainActivity.this.visibleItemCount-1);
						
						if(secondWantedView != null){
														
							scaleAnimation = new ScaleAnimation((float) (1.0 - scaling), (float) (1.0 - scaling), (float) (1.0 - scaling), (float) (1.0 - scaling), MainActivity.width, 0);
							scaleAnimation.setDuration(0);

							scaleAnimation.setFillAfter(true);
							secondWantedView.setAnimation(scaleAnimation);
							secondWantedView.startAnimation(scaleAnimation);
		
						}	
					}
				}
		}  
	  }
	    
}