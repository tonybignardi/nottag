package br.com.bign.nottag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;

public class Nuvem  {

	 
	public Nuvem()
	{
		
			
	}
	 public void insereTag(String not,Context contexto)
	 {
		 
		 AccountManager am = AccountManager.get(contexto); 
		 Account[] contas = am.getAccountsByType("com.google");
		 
		 HttpURLConnection con = null;
			URL url_caminho;
			String insere = "http://www.bign.com.br/nb/az.php?segue="+contas[0].name+"&nottag="+not;
					
			try {
				url_caminho = new URL(insere);
				con = (HttpURLConnection) url_caminho.openConnection();
				readStream(con.getInputStream());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			 //return resposta;
		 
	 }
	
	 public void tagsQueSigo(Context c)
	 {
		 //TODO 
		 // BAIXAR DA NUVEM AS TAGS QUE O CARA SEGUE E SALVAR NO BANCO
		 AccountManager am = AccountManager.get(c); 
 		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?tags=sigo&email="+contas[0].name);
		 	
	
				JSONObject json = new JSONObject(s_json);
				JSONArray todos = json.getJSONArray("tags");
				NottagDAO cDao = new NottagDAO(c);
				cDao.open();
				
				
				for (int i = 0; i < todos.length(); i++) {
				
					cDao.create(todos.getString(i));

				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
	 
		 
	 }
	 
	 public String pegaHTTP(String url)
	 {
		HttpURLConnection con = null;
		URL url_caminho;
		String resposta = null;
		try {
			url_caminho = new URL(url);
			con = (HttpURLConnection) url_caminho.openConnection();
			resposta = readStream(con.getInputStream());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 return resposta;
		 
	 }
	
	 public String readStream(InputStream is)
	 {
		 BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
	}


}
