package br.com.bign.dao;


import java.util.ArrayList;
import java.util.List;

import br.com.bign.ferramentas.meuBancoHelper;
import br.com.bign.model.mensagem;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class mensagemDAO {
	
	private SQLiteDatabase banco;
	private String[] colunas={meuBancoHelper.TABELA_MSG_ID,"nottag","titulo","msg","opcoes","data","idnot","resposta","dataresposta","temfoto"};
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
	public void create(String not,String titulo, String msg, String opcoes,String data,String idnot,String temfoto){
		
		ContentValues valores=new ContentValues();
		valores.put("nottag",not);
		valores.put("titulo",titulo);
		valores.put("msg",msg);
		valores.put("opcoes",opcoes);
		valores.put("data", data);
		valores.put("idnot", idnot);
		valores.put("resposta", "");
		valores.put("dataresposta", "");
		valores.put("temfoto", temfoto);
		
		banco.insert(meuBancoHelper.TABELA_MSG,null,valores);
		
		
		
	}
	public void delete(mensagem c){
		long id=c.getmId();
		banco.delete(meuBancoHelper.TABELA_MSG,meuBancoHelper.TABELA_MSG_ID+"="+id,null);
		
		
		}
	
	
	public List <mensagem> getAll () {
		 List <mensagem > notes = new ArrayList <mensagem >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MSG , colunas , null , null , null , null , "mId DESC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			 mensagem c = new mensagem();
			 c.setmId(Integer.parseInt(cursor.getString (0)));
			 c.setNottag(cursor.getString (1) );
			 c.setTitulo(cursor.getString(2));
			 c.setMsg(cursor.getString(3));
			 c.setOpcoes(cursor.getString(4));
			 c.setData(cursor.getString(5));
			 c.setIdm(cursor.getString(6));
			 c.setResposta(cursor.getString(7));
			 c.setDataResposta(cursor.getString(8));
			 c.setTemFoto(cursor.getString(9));
		 
		 
			 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
		 }
	public List<mensagem> getAllWhereTag(String nottag) {
		// TODO Auto-generated method stub
		
		 List <mensagem > notes = new ArrayList <mensagem >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MSG , colunas , "nottag='"+nottag+"'" , null , null , null , "mId DESC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			 mensagem c = new mensagem();
			 c.setmId(Integer.parseInt(cursor.getString (0)));
			 c.setNottag(cursor.getString (1) );
			 c.setTitulo(cursor.getString(2));
			 c.setMsg(cursor.getString(3));
			 c.setOpcoes(cursor.getString(4));
			 c.setData(cursor.getString(5));
			 c.setIdm(cursor.getString(6));
			 c.setResposta(cursor.getString(7));
			 c.setDataResposta(cursor.getString(8));
			 c.setTemFoto(cursor.getString(9));
		 
		 
			 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
	}
	public void alteraResposta(long  idMensagem, String resp, String ultimaData) {
		// TODO Auto-generated method stub

		ContentValues valores=new ContentValues();
		
		valores.put("resposta",resp);
		valores.put("dataresposta", ultimaData);
		
		banco.update(meuBancoHelper.TABELA_MSG, valores, meuBancoHelper.TABELA_MSG_ID+"="+idMensagem, null);
		
	}
	public void alteraTemFoto(long idm) {
		// TODO Auto-generated method stub
		
ContentValues valores=new ContentValues();
		
		valores.put("temfoto","S");
		
		banco.update(meuBancoHelper.TABELA_MSG, valores, "idnot="+idm, null);
		
	}
	public String temFoto(long idm) {
		// TODO Auto-generated method stub

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MSG , colunas , "idnot='"+idm+"'" , null , null , null , "mId DESC",null);
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			if(cursor.getString(9).equals("S"))
				return "S";
			 
		 }
		 
		 return "";
		
	}
	
	
}

