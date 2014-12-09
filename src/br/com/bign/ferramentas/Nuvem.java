package br.com.bign.ferramentas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.bign.dao.MTagDAO;
import br.com.bign.dao.MinhaMensagemDAO;
import br.com.bign.dao.NottagDAO;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.nottag.VerNot;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Nuvem  {

	 
	private String porque;
	private long ultimoId;
	private String ultimaData;
	private int totalresp;
	public Nuvem()
	{
		
			
	}
	 public boolean podeSeguirTag(String not,Context c)
	 {
		 
		 
		 AccountManager am = AccountManager.get(c); 
 		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		
		 		not=not.replace("#", "");
		 		String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?segue="+contas[0].name+"&nottag="+not);
		 	
	
				JSONObject json = new JSONObject(s_json);
				porque=json.getString("PODE");
				if(json.get("PODE").equals("1"))
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		 	return false;
		 
			 
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
	public void tagsQueCriei(Context c) {
		// TODO Auto-generated method stub
		
		 AccountManager am = AccountManager.get(c); 
 		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?tags=criei&email="+contas[0].name);
		 	
	
				JSONObject json = new JSONObject(s_json);
				JSONArray todos = json.getJSONArray("tags");
				MTagDAO cDao = new MTagDAO(c);
				cDao.open();
				
				
				for (int i = 0; i < todos.length(); i++) {
				
					cDao.create(todos.getString(i));

				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		
	}
	public boolean  podeInserirTag(String tag, Context c) {
		
		/// O REST TAMBEM INSERE SE PUDER
		 AccountManager am = AccountManager.get(c); 
 		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		
		 		tag=tag.replace("#", "");
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?op=pode&nottag="+tag+"&email="+contas[0].name);
		 	
	
				JSONObject json = new JSONObject(s_json);
				if(json.get("PODE").equals("1"))
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		 	return false;

		
	}
	public String getPorqueNao() {
		
		// TODO Auto-generated method stub
		if(porque.equals("2"))
		return "JASEGUE";
		
		return "";
	}
	public void notsQueCriei(Context c,String nottag) {
		// TODO Auto-generated method stub
		
		 AccountManager am = AccountManager.get(c); 
 		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?tags=msg&email="+contas[0].name+"&nottag="+nottag);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				
				JSONArray nottags = json.getJSONArray("nottags");
				JSONArray titulos = json.getJSONArray("titulos");
				JSONArray mensagens = json.getJSONArray("mensagens");
				JSONArray idms = json.getJSONArray("idms");
				JSONArray opcoes = json.getJSONArray("opcoes");
				JSONArray datas = json.getJSONArray("datas");
				MinhaMensagemDAO cDao = new MinhaMensagemDAO(c);
				cDao.open();
				
				
				for (int i = 0; i < idms.length(); i++) {
				
					cDao.create(nottags.getString(i),titulos.getString(i),mensagens.getString(i),
							opcoes.getString(i),datas.getString(i),idms.getLong(i));

				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public boolean podeCriarNotificacao(String nottag, String titulo,
			String msg, String opcoes,String foto, Context c) {
		// TODO Auto-generated method stub
		
		AccountManager am = AccountManager.get(c); 
		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		
		 		titulo=titulo.replace(" ", "+");
		 		msg=msg.replace(" ", "+");
		 		opcoes = opcoes.replace(" ", "+");
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?op=crianot&nottag="+nottag+"&email="+contas[0].name
					 +"&titulo="+titulo+"&msg="+msg+"&opcoes="+opcoes+"&foto="+foto);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				this.ultimaData= json.getString("DATA");
				this.ultimoId = json.getLong("IDM");
				
				if(ultimoId!=0)
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		return false;
	}
	public long getUltimoId() {
		// TODO Auto-generated method stub
		return ultimoId;
	}
	public String getUltimaData() {
		// TODO Auto-generated method stub
		return ultimaData;
	}
	public CharSequence getMsgNaoPodeResponder() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean respondeNot(String resp, String idm,long idMensagem,Context c) {
		// TODO Auto-generated method stub
		AccountManager am = AccountManager.get(c); 
		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		

			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?responder="+resp+"&idnot="+idm+"&email="+contas[0].name);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				this.ultimaData= json.getString("DATA");
				this.ultimoId = json.getLong("IDM");
				
				if(ultimoId!=0)
				{
					mensagemDAO mdao = new mensagemDAO(c);
					mdao.open();
					mdao.alteraResposta(idMensagem,resp,getUltimaData());
					
					return true;
				
				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		return false;
	}
	public String contaSeguidores(String nottag,Context c) {
		// TODO Auto-generated method stub
		
		AccountManager am = AccountManager.get(c); 
		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		

			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?contar="+nottag+"&email="+contas[0].name);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				return json.getString("TOTAL");
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		return "##";
		
	}
	public int[] contaRespostas(long idm, Context c) {
		// TODO Auto-generated method stub
		
		String resps[] = null;
		AccountManager am = AccountManager.get(c); 
		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	

			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?contaresp="+idm+"&email="+contas[0].name);
		 	
	
				JSONObject json = new JSONObject(s_json);
				JSONArray todos = json.getJSONArray("respostas");
				
				totalresp = json.getInt("total");
				
				
				
				//Toast.makeText(c, "tam "+todos.length(), Toast.LENGTH_LONG).show();
				//Toast.makeText(c, "jso "+json.getString("respostas"), Toast.LENGTH_LONG).show();
				resps = new String[todos.length()];
				
				for(int i = 0;i<todos.length();i++)
					resps[i]=todos.getString(i);
				
				int r[] = new int[todos.length()];
				
				for(int i = 0;i<todos.length();i++)
					r[i]=Integer.parseInt(resps[i]);
				
				return r;
				
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		return null;
		
	}
	public int getTotalresp() {
		// TODO Auto-generated method stub
		return totalresp;
	}
	
	


}
