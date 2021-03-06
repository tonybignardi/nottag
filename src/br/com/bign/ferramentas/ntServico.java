package br.com.bign.ferramentas;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import br.bign.com.nottag.R;
import br.com.bign.dao.ConfigDAO;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.listas.ListaMsg;
import br.com.bign.model.Config;
import br.com.bign.nottag.NOTActivity;
import br.com.bign.nottag.VerMsg;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class ntServico extends Service {

	private Intent i;
	private mensagemDAO cDao;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	} 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		i = new Intent(ntServico.this,NOTActivity.class);

			
		
		Timer mTimer = new Timer();
		 
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            	//
            	Random r = new Random();
            	onStartCommand(i, r.nextInt(9999999), r.nextInt(999999));
            }

        },0, 5000);
    
        
    	
        
		
		
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//Toast.makeText(this, "SERVICO DESTRUIDO", Toast.LENGTH_LONG).show();
	}
	 @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		 
		
		 /*
		
			if(cl.get(Calendar.MINUTE)%2==0)
			{
				
						
				
				
			} */
		 
		 String mensagem = "";
		 String titulo = "";
		 String data = "";
		 String nottag = "";
		 String opcoes = "";
		 String idnot="";
		 String temfoto="";
		 String dagenda="";
		 String subtag="";
		 String certa="";
		 int idM = 0;
		 
		 ConfigDAO cdao = new ConfigDAO(this);
		 cdao.open();
		 Config config = cdao.get("email");
		 cdao.close();
 		if(!config.getValor().equals(""))
 		{
		 
 		
 		 Log.i("x","SERVICO "+System.currentTimeMillis());
		 	try {	
		 		
		 		DetectaConexao conexao = new DetectaConexao(this);
				
				if(!conexao.existeConexao())
					return 0;
				
				String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?email="+config.getValor());
		 	
		 	
			 
				JSONObject json = new JSONObject(s_json);
				
				
				mensagem = json.getString("MSG"); 
				titulo = json.getString("TITULO");
				idM = Integer.parseInt(json.getString("IDM"));
				data = json.getString("DATA");
				nottag = json.getString("NOTTAG");
				opcoes = json.getString("OPCOES");
				idnot = json.getString("IDNOT");
				temfoto = json.getString("TEMFOTO");
				certa = json.getString("CERTA");
				subtag = json.getString("SUBTAG");
				dagenda = json.getString("DAGENDA");
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return 0;
			}catch (NullPointerException e) {
				// TODO: handle exception
				return 0;
			}
		 	
		 	if(!mensagem.equals(""))
		 	{
		 		
		 		
		 		cDao = new mensagemDAO(this); 
		 		cDao.open();
		 		
		 		
		 		cDao.create(nottag,titulo,mensagem,opcoes,data,idnot,temfoto,certa,subtag,dagenda);
		 		

		 		cDao.close();

		 		criaNotificacao("#"+nottag, titulo,dagenda,mensagem,idnot,temfoto,subtag);
		 		 
		 		jaLeu("http://www.bign.com.br/nb/az.php?idm="+idM+"&email="+config.getValor());
		 	}
		 	 
		 
 		}
		 
	    return Service.START_STICKY;
	    
	  }
	 
	 public static void jaLeu(String url)
	 {
		 HttpURLConnection con = null;
			URL url_caminho;
			try {
				url_caminho = new URL(url);
				con = (HttpURLConnection) url_caminho.openConnection();
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				readStream(con.getInputStream());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			 //return resposta;
		 
	 }
	 public String pegaHTTP(String url)
	 {
		HttpURLConnection con = null;
		URL url_caminho;
		String resposta = null;
		try {
			url_caminho = new URL(url);
			con = (HttpURLConnection) url_caminho.openConnection();
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			resposta = readStream(con.getInputStream());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 return resposta;
		 
	 }
	 public static String readStream(InputStream is)
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
	 public void criaNotificacao(String nottag,String titulo,String data,String msg,String idm,String temfoto,String subtag)
	 {
		  
			
		 
		Intent iVer = new Intent(ntServico.this,ListaMsg.class); 
		iVer.putExtra("nottag",nottag.replace("#", ""));
		
		iVer.putExtra("subtag", subtag);
		if(!subtag.equals(""))
		subtag=" #"+subtag;
		
		PendingIntent iDepois = PendingIntent.getActivity(ntServico.this, 0, iVer, PendingIntent.FLAG_UPDATE_CURRENT);
		
		
		NotificationManager notGer = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder  n = new NotificationCompat.Builder(this);
			
			
			
			n.setContentTitle(titulo);
			n.setContentText(nottag+subtag);
			n.setAutoCancel(true);
			
			
			n.setSmallIcon(R.drawable.ico_not_tag);
			if(temfoto.equals("S"))
			{
				try {
					n.setLargeIcon(
					new DownloadIconTask(idm+".jpg",100).execute("http://bign.com.br/b/dothumb.php?img=arquivos/"+idm+".jpg&w=100&h=100").get() 
					);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			
			n.setContentIntent(iDepois);
			n.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			long[] vibrate = { 200, 200,200, 200 };
			n.setVibrate(vibrate);
			
			//n.setSound(Uri.parse(R.raw.watch_this));
			
			//n.addAction(R.drawable.hash, "Ler", iDepois);
			//.build();
			
			Random r = new Random();		
			notGer.notify(r.nextInt(999999999),n.build());
			
	 }
	 
	 public Bitmap getBitmapFromURL(String strURL) {
		    try {
		        URL url = new URL(strURL);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        Bitmap myBitmap = BitmapFactory.decodeStream(input);
		        return myBitmap;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		}
	 

}
