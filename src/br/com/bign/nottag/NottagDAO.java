package br.com.bign.nottag;


import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class NottagDAO {
	
	private SQLiteDatabase banco;
	private String[] colunas={meuBancoHelper.TABELA_NOT_ID,"notSegue"};
	private meuBancoHelper BancoHelper;

	public NottagDAO(Context contexto){
		
		BancoHelper = new meuBancoHelper(contexto);
	}
	public void open()  {
	// TODO Auto-generated method stub
		banco =BancoHelper.getWritableDatabase();
	}
	
	
	public void close() {
	// TODO Auto-generated method stub
		BancoHelper.close();
	}
	public void create(String not){
		
		ContentValues valores=new ContentValues();
		valores.put("notSegue",not);
		banco.insert(meuBancoHelper.TABELA_NOT,null,valores);
		
		
	}
	public void delete(Nottag c){
		long id=c.getNottagId();
		banco.delete(meuBancoHelper.TABELA_NOT,meuBancoHelper.TABELA_NOT_ID+"="+id,null);
		
		}
	
	
	public List <Nottag> getAll () {
		 List <Nottag > notes = new ArrayList <Nottag >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_NOT , colunas , null , null , null , null , "notSegue ASC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
		 Nottag c = new Nottag();
		 c.setNottagId(Integer.parseInt(cursor.getString (0)));
		 c.setSegueNot( cursor.getString (1) );
		 
		 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
		 }
	
	
}

