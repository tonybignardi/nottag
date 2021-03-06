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

import br.com.bign.dao.ConfigDAO;
import br.com.bign.dao.MTagDAO;
import br.com.bign.dao.MinhaMensagemDAO;
import br.com.bign.dao.NottagDAO;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.model.Config;
import br.com.bign.nottag.NOTActivity;
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
	private String meuEmail;
	public Nuvem(Context c)
	{
		 ConfigDAO cdao = new ConfigDAO(c);
		 cdao.open();
		 Config config = cdao.get("email");
		 meuEmail = config.getValor();
		 cdao.close();
		
 		 
	}
	 public boolean podeSeguirTag(String not,Context c)
	 {
		
		 
		 try {	
		 		
		 		not=not.replace("#", "");
		 		String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?segue="+meuEmail+"&nottag="+not);
		 	
	
				JSONObject json = new JSONObject(s_json);
				porque=json.getString("PODE");
				if(json.get("PODE").equals("1"))
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		 	return false;
		 
			 
	 }
	
	 public void tagsQueSigo(Context c)
	 {
		 //TODO 
		 // BAIXAR DA NUVEM AS TAGS QUE O CARA SEGUE E SALVAR NO BANCO
	
		 	try {	
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?tags=sigo&email="+meuEmail);
		 	
	
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
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
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
			con.setRequestProperty("Accept-Charset", "UTF-8");
			con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
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
		
		 	try {	
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?tags=criei&email="+meuEmail);
		 	
	
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
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		
	}
	public boolean  podeInserirTag(String tag, Context c) {
		
		/// O REST TAMBEM INSERE SE PUDER
		 
		 	try {	
		 		
		 		tag=tag.replace("#", "");
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?op=pode&nottag="+tag+"&email="+meuEmail);
		 	
	
				JSONObject json = new JSONObject(s_json);
				if(json.get("PODE").equals("1"))
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				// TODO: handle exception
				
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
		
		 
		 	try {	
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?tags=msg&email="+meuEmail+"&nottag="+nottag);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				
				JSONArray nottags = json.getJSONArray("nottags");
				JSONArray titulos = json.getJSONArray("titulos");
				JSONArray mensagens = json.getJSONArray("mensagens");
				JSONArray idms = json.getJSONArray("idms");
				JSONArray opcoes = json.getJSONArray("opcoes");
				JSONArray datas = json.getJSONArray("datas");
				JSONArray certas = json.getJSONArray("certas");
				JSONArray dagendas = json.getJSONArray("dagendas");
				JSONArray subtags = json.getJSONArray("subtags");
				JSONArray qresps = json.getJSONArray("qresps");
				JSONArray temfoto = json.getJSONArray("temfoto");
				MinhaMensagemDAO cDao = new MinhaMensagemDAO(c);
				cDao.open();
				
				
				for (int i = 0; i < idms.length(); i++) {
				
					cDao.create(nottags.getString(i),titulos.getString(i),mensagens.getString(i),
							opcoes.getString(i),datas.getString(i),idms.getLong(i),certas.getString(i),subtags.getString(i),
							dagendas.getString(i),qresps.getInt(i),temfoto.getString(i));

				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		
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
		
		 	try {	
		 		

			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?responder="+resp+"&idnot="+idm+"&email="+meuEmail);
		 	
	
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
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		return false;
	}
	public String contaSeguidores(String nottag,Context c) {
		// TODO Auto-generated method stub
		
		AccountManager am = AccountManager.get(c); 
		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
		 		

			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?contar="+nottag+"&email="+meuEmail);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				return json.getString("TOTAL");
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		return "##";
		
	}
	public int[] contaRespostas(long idm, Context c) {
		// TODO Auto-generated method stub
		
		String resps[] = null;
		
		 	try {	

			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?contaresp="+idm+"&email="+meuEmail);
		 	
	
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
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		return null;
		
	}
	public int getTotalresp() {
		// TODO Auto-generated method stub
		return totalresp;
	}
	public boolean podeCriarNotificacao(String nottag, String titulo,
			String msg, String opcoes, String foto, String certa,
			String subtag, String dagenda, int qresp,
			Context c) {
			
			try {	
			 		
			 		titulo=titulo.replace(" ", "+");
			 		msg=msg.replace(" ", "+");
			 		opcoes = opcoes.replace(" ", "+");
			 		dagenda = dagenda.replace(" ", "+");
			 		
			 		
				 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?op=crianot&nottag="+nottag+"&email="+meuEmail
						 +"&titulo="+titulo+"&msg="+msg+"&opcoes="+opcoes+"&foto="+foto+"&certa="+certa+"&subtag="+subtag+"&dagenda="+dagenda+"&qresp="+qresp);
			 	
		
					JSONObject json = new JSONObject(s_json);
					
					this.ultimaData= json.getString("DATA");
					this.ultimoId = json.getLong("IDM");
					
					if(ultimoId!=0)
						return true;
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	catch (NullPointerException e) {
					// TODO: handle exception
					
				}
			 	
			return false;
		}
	public boolean podeDeixarTag(String segueNot) {
		// TODO Auto-generated method stub
		
		
		try {	
		 		
		 		
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?op=deixartag&nottag="+segueNot+"&email="+meuEmail);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				String pode= json.getString("PODE");
				
				if(pode.equals("1"))
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		return false;
		
	}
	
	public boolean podeExcluirTag(String segueNot) {
		// TODO Auto-generated method stub
		
		
		try {	
		 		
		 		
		 		
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?op=excluirtag&nottag="+segueNot+"&email="+meuEmail);
		 	
	
				JSONObject json = new JSONObject(s_json);
				
				String pode= json.getString("PODE");
				
				if(pode.equals("1"))
					return true;
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	catch (NullPointerException e) {
				// TODO: handle exception
				
			}
		 	
		return false;
		
	}
	}
	
	



