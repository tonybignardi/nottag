package br.com.bign.nottag;


import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class mensagemDAO {
	
	private SQLiteDatabase banco;
	private String[] colunas={meuBancoHelper.TABELA_MSG_ID,"nottag","titulo","msg","opcoes","data"};
	private meuBancoHelper BancoHelper;

	public mensagemDAO(Context contexto){
		
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
	public void create(String not,String titulo, String msg, String opcoes,String data){
		
		ContentValues valores=new ContentValues();
		valores.put("nottag",not);
		valores.put("titulo",titulo);
		valores.put("msg",msg);
		valores.put("opcoes",opcoes);
		valores.put("data", data);
		banco.insert(meuBancoHelper.TABELA_MSG,null,valores);
		
		
		
	}
	public void delete(mensagem c){
		long id=c.getmId();
		banco.delete(meuBancoHelper.TABELA_MSG,meuBancoHelper.TABELA_MSG_ID+"="+id,null);
		
		}
	
	
	public List <mensagem> getAll () {
		 List <mensagem > notes = new ArrayList <mensagem >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MSG , colunas , null , null , null , null , "mId ASC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			 mensagem c = new mensagem();
			 c.setmId(Integer.parseInt(cursor.getString (0)));
			 c.setNottag(cursor.getString (1) );
			 c.setTitulo(cursor.getString(2));
			 c.setMsg(cursor.getString(3));
			 c.setOpcoes(cursor.getString(4));
			 c.setData(cursor.getString(5));
		 
		 
			 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
		 }
	public List<mensagem> getAllWhereTag(String nottag) {
		// TODO Auto-generated method stub
		
		 List <mensagem > notes = new ArrayList <mensagem >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MSG , colunas , "nottag='"+nottag+"'" , null , null , null , "mId ASC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			 mensagem c = new mensagem();
			 c.setmId(Integer.parseInt(cursor.getString (0)));
			 c.setNottag(cursor.getString (1) );
			 c.setTitulo(cursor.getString(2));
			 c.setMsg(cursor.getString(3));
			 c.setOpcoes(cursor.getString(4));
			 c.setData(cursor.getString(5));
		 
		 
			 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
	}
	
	
}

