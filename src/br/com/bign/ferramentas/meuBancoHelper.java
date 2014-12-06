package br.com.bign.ferramentas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class meuBancoHelper extends SQLiteOpenHelper {

	public static final String TABELA_NOT = "nottag";
	public static final String TABELA_NOT_ID = "nottagId";
	
	public static final String TABELA_MSG = "mensagem";
	public static final String TABELA_MSG_ID = "mId";
	
	public static final String TABELA_MTAG = "mtag";
	public static final String TABELA_MTAG_ID = "mtagId";
		
	private static final String BANCO_NOME = "nt.db";
	private static final int BANCO_VERSAO = 1;
	
	
	
	
	public meuBancoHelper(Context context) {
		
		super(context,BANCO_NOME,null, BANCO_VERSAO );
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String BANCO_SQL = "create table "+ TABELA_NOT+"("+TABELA_NOT_ID+" integer primary key autoincrement,";
		BANCO_SQL +="notSegue text not null);";
		db.execSQL(BANCO_SQL);
		
		BANCO_SQL = "create table "+ TABELA_MSG+"("+TABELA_MSG_ID+" integer primary key autoincrement,";
		BANCO_SQL +="nottag text not null,titulo text not null, msg text not null,opcoes text not null, data text not null);";
		db.execSQL(BANCO_SQL);
		
		BANCO_SQL = "create table "+ TABELA_MTAG+"("+TABELA_MTAG_ID+" integer primary key autoincrement,";
		BANCO_SQL +="mtag text not null);";
		db.execSQL(BANCO_SQL);
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int antigaVersao, int novaVersao) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_NOT);
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_MSG);
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_MTAG);
		
		onCreate(db);
		
	}

}
