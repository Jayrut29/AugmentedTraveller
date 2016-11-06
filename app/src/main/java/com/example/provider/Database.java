package com.example.provider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class Database extends SQLiteOpenHelper
{

	private static String DB_PATH = Environment.getDataDirectory() + "/data/com.example.agt/databases/";
	private static String DB_NAME = "travelller.sqlite";
	private SQLiteDatabase myDataBase = null;
	private final Context myContext;
	@SuppressWarnings("unused")
	private final int MAXSCORECOUNT = 50;

	public Database(Context context)
	{
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		try
		{
			createDataBase();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		if (myDataBase != null)
		{
			myDataBase.close();
		}
		super.finalize();
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}

	public void createDataBase() throws IOException
	{
		boolean dbExist = checkDataBase();

		if (!dbExist)
		{
			this.getReadableDatabase().close();

			try
			{
				copyDataBase();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private boolean checkDataBase()
	{
		boolean fileExist = false;
		try
		{
			String myPath = DB_PATH + DB_NAME;
			File file = new File(myPath);
			fileExist = file.exists();
		}
		catch (SQLiteException e)
		{
		}

		return fileExist;
	}

	private void copyDataBase() throws IOException
	{
		// Open your local db as the input stream
		InputStream myInput = myContext.getResources().getAssets().open(DB_NAME, AssetManager.ACCESS_BUFFER);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		byte[] buffer = new byte[128];
		int length;
		while ((length = myInput.read(buffer)) > 0)
		{
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException
	{
		if (myDataBase != null)
			return;
		try
		{
			createDataBase();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	}

	public synchronized void close()
	{
		if (myDataBase != null)
		{
			myDataBase.close();
			myDataBase = null;
		}
		super.close();
	}
}
