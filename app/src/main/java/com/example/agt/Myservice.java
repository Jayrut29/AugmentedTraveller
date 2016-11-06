package com.example.agt;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.widget.Toast;

public class Myservice extends Service {


	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startId) {
    	// For time consuming an long tasks you can launch a new thread here...
        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
        
        try
			{
				String PLACE_MAIN="PlaceMain";
				SQLiteDatabase myDb2 = openOrCreateDatabase("/"+PLACE_MAIN, Context.MODE_PRIVATE, null);

				String PLACE_DETAIL="Detail";
				myDb2.execSQL("CREATE TABLE IF NOT EXISTS " +
						PLACE_DETAIL +
					" (id INTEGER primary key autoincrement,name VARCHAR,introduction VARCHAR,address VARCHAR,phone_no VARCHAR, email VARCHAR, latitude VARCHAR, longitude VARCHAR);");

				ContentValues newValues1 = new ContentValues();

				newValues1.put("name", "abc");
				newValues1.put("introduction", "introduction");
				newValues1.put("address", "address");
				newValues1.put("phone_no", "phone_no");
				newValues1.put("email", "email");
				newValues1.put("latitude", "latitude");
				newValues1.put("longitude", "longitude");
	        myDb2.insert(PLACE_DETAIL, null, newValues1); 
			
				
				myDb2.close();
    }catch(Exception e)
		{
		
    	//Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
    
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }
}