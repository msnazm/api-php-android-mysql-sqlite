package com.portal.full.appmozoou.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "connect";

	// Table Name
	public static final String TABLE_NAME_BOOKMARK = "bookmark";
	public static final String C_ID = "BookMarkID";
	public static final String C_NAME = "TitleBookMark";

	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}




	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME_BOOKMARK +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"BookMarkID INTEGER," +
				"TitleBookMark TEXT(200), " +
				"ContentBookMark TEXT(20000));");


		Log.d("CREATE TABLE", "Create Table Successfully.");
		// TODO Auto-generated method stub

	}

	public long InsertDataBookMark(String strBookMarkID,String strTitleBookMark,String strContentBookMark) {

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			ContentValues Val = new ContentValues();
			Val.put("BookMarkID", strBookMarkID);
			Val.put("TitleBookMark", strTitleBookMark);
			Val.put("ContentBookMark", strContentBookMark);

			long rows = db.insert(TABLE_NAME_BOOKMARK, null, Val);




			db.close();
			return rows; // return rows inserted.


		} catch (Exception e) {
			return -1;
		}

	}
	public long DeleteDataBookMarks(String strBookID) {
		// TODO Auto-generated method stub

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			/**
			 * for API 11 and above
			 SQLiteStatement insertCmd;
			 String strSQL = "DELETE FROM " + TABLE_MEMBER
			 + " WHERE MemberID = ? ";


			 insertCmd = db.compileStatement(strSQL);
			 insertCmd.bindString(1, strMemberID);

			 return insertCmd.executeUpdateDelete();
			 *



			 */







			long rows = db.delete(TABLE_NAME_BOOKMARK, "BookMarkID = ?",
					new String[]{String.valueOf(strBookID)});

			db.close();


			return rows; // return rows deleted.


		} catch (Exception e) {
			return -1;
		}

	}
	//----------------------------------------------------------


	//----------------------------------------------------------------------
	// Delete Data
	public long DeleteDataBookMark(String[] ids) {
		// TODO Auto-generated method stub

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data


			long rows = db.delete(TABLE_NAME_BOOKMARK, C_ID+" IN (" + new String(new char[ids.length-1]).replace("\0", "?,") + "?)", ids);


			db.close();


			return rows; // return rows deleted.


		} catch (Exception e) {
			return -1;
		}

	}

	public long DeleteAllDataBookMark() {
		// TODO Auto-generated method stub

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			/**
			 * for API 11 and above
			 SQLiteStatement insertCmd;
			 String strSQL = "DELETE FROM " + TABLE_MEMBER
			 + " WHERE MemberID = ? ";


			 insertCmd = db.compileStatement(strSQL);
			 insertCmd.bindString(1, strMemberID);

			 return insertCmd.executeUpdateDelete();
			 *
			 */

			long rows = db.delete(TABLE_NAME_BOOKMARK, null,null);


			db.close();


			return rows; // return rows deleted.


		} catch (Exception e) {
			return -1;
		}

	}
	//----------------------------------------------------------------------------------------



	//-------------------------------------------------------------------------------
	// Update Data
	public long UpdateDataStudent(String strStudentUPID,String strNameStudentUP) {
		// TODO Auto-generated method stub

		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase(); // Write Data

			/**
			 *  for API 11 and above
			 SQLiteStatement insertCmd;
			 String strSQL = "UPDATE " + TABLE_MEMBER
			 + " SET Name = ? "
			 + " , Tel = ? "
			 + " WHERE MemberID = ? ";

			 insertCmd = db.compileStatement(strSQL);
			 insertCmd.bindString(1, strName);
			 insertCmd.bindString(2, strTel);
			 insertCmd.bindString(3, strMemberID);

			 return insertCmd.executeUpdateDelete();
			 *
			 */
			ContentValues Val = new ContentValues();
			Val.put("TitleBookmark", strNameStudentUP);


			long rows = db.update(TABLE_NAME_BOOKMARK, Val, " BookmarkID = ?",
					new String[] { String.valueOf(strStudentUPID) });

			db.close();
			return rows; // return rows updated.

		} catch (Exception e) {
			return -1;
		}

	}


	//-------------------------------------------------------------
	public String[] SelectDataBookMark(String strBookmarkID) {
		// TODO Auto-generated method stub

		try {
			String arrData[] = null;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			Cursor cursor = db.query(TABLE_NAME_BOOKMARK, new String[] { "*" },
					"BookmarkID=?",
					new String[] {String.valueOf(strBookmarkID) }, null, null, null, null);

			if(cursor != null)
			{
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					/***
					 *  0 = MemberID
					 *  1 = Name
					 *  2 = Tel
					 */
					arrData[0] = cursor.getString(0);
					arrData[1] = cursor.getString(1);
					arrData[2] = cursor.getString(2);
					arrData[3] = cursor.getString(3);
				}
			}
			assert cursor != null;
			cursor.close();
			db.close();
			return arrData;

		} catch (Exception e) {
			return null;
		}

	}


	//------------------------------------------------------------
	public ArrayList<HashMap<String, String>> SelectAllDataBookMark() {
		// TODO Auto-generated method stub

		try {

			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_NAME_BOOKMARK;
			Cursor cursor = db.rawQuery(strSQL, null);

			if(cursor != null)
			{
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();
						map.put("BookMarkID", cursor.getString(1));
						map.put("TitleBookMark", cursor.getString(2));
					//	map.put("ContentBookMark", cursor.getString(2));
						MyArrList.add(map);
					} while (cursor.moveToNext());
				}
			}
			assert cursor != null;
			cursor.close();
			db.close();
			return MyArrList;

		} catch (Exception e) {
			return null;
		}

	}
	//---------------------------------------------------------------------------


	//---------------------------------------------------------------
	public List<String> getAllBookMark(){
		List<String> NameStudent = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME_BOOKMARK;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				NameStudent.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return NameStudent;
	}
	//-----------------------------------------------------------


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BOOKMARK);

		// Re Create on method  onCreate
		onCreate(db);

	}

}

