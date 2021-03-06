package br.com.bign.dao;


import java.util.ArrayList;
import java.util.List;

import br.com.bign.ferramentas.meuBancoHelper;
import br.com.bign.model.MinhaMensagem;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class MinhaMensagemDAO {
	
	private SQLiteDatabase banco;
	private String[] colunas={meuBancoHelper.TABELA_MMSG_ID,"nottag","titulo","msg","opcoes","data","idm","certa","subtag","dagenda","qresp","temfoto"};
	private meuBancoHelper BancoHelper;

	public MinhaMensagemDAO(Context contexto){
		
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
	public void create(String not,String titulo, String msg, String opcoes,String data,long idm, 
			String certa, String sub, String dagenda, int respostas,String temfoto){
		
		
		
		
		ContentValues valores=new ContentValues();
		valores.put("nottag",not);
		valores.put("titulo",titulo);
		valores.put("msg",msg);
		valores.put("opcoes",opcoes);
		valores.put("data", data);
		valores.put("idm",idm);
		valores.put("certa",certa);
		valores.put("subtag",sub);
		valores.put("dagenda",dagenda);
		valores.put("qresp",respostas);
		valores.put("temfoto",temfoto);
		banco.insert(meuBancoHelper.TABELA_MMSG,null,valores);
		
		
		
	}
	public void delete(MinhaMensagem c){
		long id=c.getmId();
		banco.delete(meuBancoHelper.TABELA_MMSG,meuBancoHelper.TABELA_MMSG_ID+"="+id,null);
		
		}
	
	
	public List <MinhaMensagem> getAll () {
		 List <MinhaMensagem > notes = new ArrayList <MinhaMensagem >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MMSG , colunas , null , null , null , null , "mId DESC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			 MinhaMensagem c = new MinhaMensagem();
			 c.setmId(Integer.parseInt(cursor.getString (0)));
			 c.setNottag(cursor.getString (1) );
			 c.setTitulo(cursor.getString(2));
			 c.setMsg(cursor.getString(3));
			 c.setOpcoes(cursor.getString(4));
			 c.setData(cursor.getString(5));
			 c.setIdm(Integer.parseInt(cursor.getString (6)));
			 c.setCerta(cursor.getString(7));
			 c.setSubtag(cursor.getString(8));
			 c.setDagenda(cursor.getString(9));
			 c.setQresp(cursor.getInt(10));
			 c.setTemfoto(cursor.getString(11));
			 
			 
				
			 
		 
		 
			 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
		 }
	public List<MinhaMensagem> getAllWhereTag(String nottag) {
		// TODO Auto-generated method stub
		
		 List <MinhaMensagem > notes = new ArrayList <MinhaMensagem >() ;

		 Cursor cursor = banco.query( meuBancoHelper .TABELA_MMSG , colunas , "nottag='"+nottag+"'" , null , null , null , "mId DESC",null);
		 
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
			 
			 MinhaMensagem c = new MinhaMensagem();
			 c.setmId(Integer.parseInt(cursor.getString (0)));
			 c.setNottag(cursor.getString (1) );
			 c.setTitulo(cursor.getString(2));
			 c.setMsg(cursor.getString(3));
			 c.setOpcoes(cursor.getString(4));
			 c.setData(cursor.getString(5));
			 c.setIdm(Integer.parseInt(cursor.getString (6)));
			 c.setCerta(cursor.getString(7));
			 c.setSubtag(cursor.getString(8));
			 c.setDagenda(cursor.getString(9));
			 c.setQresp(cursor.getInt(10));
			 c.setTemfoto(cursor.getString(11));
		 
		 
			 notes.add(c);
		 cursor.moveToNext ();
		 }
		 cursor.close ();
		 
		 
		 return notes;
	}
	public void deleteAll() {
		// TODO Auto-generated method stub
		try{	
		banco.delete(meuBancoHelper.TABELA_MMSG,null,null);
		}
		catch(NullPointerException e)
		{
			
		}
	}
			
	
	
}


