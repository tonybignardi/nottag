package br.com.bign.dao;


import java.util.ArrayList;
import java.util.List;

import br.com.bign.ferramentas.Nuvem;
import br.com.bign.ferramentas.meuBancoHelper;
import br.com.bign.model.Config;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;




public class ConfigDAO {
	
	private SQLiteDatabase banco;
	private String[] colunas={meuBancoHelper.TABELA_CONFIG_ID,"config","valor"};
	private meuBancoHelper BancoHelper;

	public ConfigDAO(Context contexto){
		
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
	public void create(String config,String valor){
		
		ContentValues valores=new ContentValues();
		
		valores.put("config",config);
		valores.put("valor",valor);
		banco.insert(meuBancoHelper.TABELA_CONFIG,null,valores);
		
		
	}
	public void delete(Config c){
		long id=c.getConfigId();
		banco.delete(meuBancoHelper.TABELA_CONFIG,meuBancoHelper.TABELA_CONFIG_ID+"="+id,null);
		
		}
	public void trocaConfig(String config,String valor)
	{
		ContentValues valores=new ContentValues();
		
		valores.put("config",config);
		valores.put("valor",valor);
		banco.update(meuBancoHelper .TABELA_CONFIG, valores, "config='"+config+"'",null);
		
	}
	
	
	public Config get (String config) {
		 List <Config > notes = new ArrayList <Config>() ;

		 	
		 Cursor cursor = banco.query( meuBancoHelper .TABELA_CONFIG , colunas , "config='"+config+"'" , null , null , null , "configId ASC",null);
		 cursor . moveToFirst ();
		 while (! cursor.isAfterLast ()) {
		 Config c = new Config();
		 c.setConfigId(Integer.parseInt(cursor.getString (0)));
		 c.setConfig( cursor.getString (1) );
		 c.setValor(cursor.getString(2));
		 
		 Log.i("point","chegou");
		 notes.add(c);
		 cursor.moveToNext ();
		 }
		 		 
		 try{
			 
		 
		 return notes.get(0);
		 
		 }catch(IndexOutOfBoundsException e)
		 {
				 
		 }
		 Config cr = new Config();
		 cr.setConfig(config);
		 cr.setValor("");
		 return cr;
	
		
	}

	public void trocaUsuario() {
		// TODO Auto-generated method stub
		
		banco.delete(meuBancoHelper.TABELA_MSG,null,null);
		banco.delete(meuBancoHelper.TABELA_MMSG,null,null);
		banco.delete(meuBancoHelper.TABELA_MTAG,null,null);
		banco.delete(meuBancoHelper.TABELA_NOT,null,null);
		
		
	}
}

