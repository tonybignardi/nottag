package br.com.bign.dao;


import java.util.ArrayList;
import java.util.List;

import br.com.bign.ferramentas.meuBancoHelper;
import br.com.bign.model.MTag;
import br.com.bign.model.Nottag;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class MTagDAO {
	
	private SQLiteDatabase banco;
	private String[] colunas={meuBancoHelper.TABELA_MTAG_ID,"mtag"};
	private meuBancoHelper BancoHelper;

	public MTagDAO(Context contexto){
		
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
		
		valores.put("mtag",not.replace("#", ""));
		banco.insert(meuBancoHelper.TABELA_MTAG,null,valores);
		
		
	}
	public void delete(MTag c){
		long id=c.getMtagId();
		banco.delete(meuBancoHelper.TABELA_MTAG,meuBancoHelper.TABELA_MTAG_ID+"="+id,null);
		
		}
	
	
	public List <MTag> getAll () {
		 List <MTag > notes = new ArrayList <MTag >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MTAG , colunas , null , null , null , null , "mtag ASC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
		 MTag c = new MTag();
		 c.setMtagId(Integer.parseInt(cursor.getString (0)));
		 c.setMtag( cursor.getString (1) );
		 
		 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
		 }
	
	
}

