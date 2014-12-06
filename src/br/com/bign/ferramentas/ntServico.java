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

import org.json.JSONException;
import org.json.JSONObject;

import br.bign.com.nottag.R;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.nottag.NOTActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
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
		 
		DetectaConexao conexao = new DetectaConexao(this);
		
		if(!conexao.existeConexao())
			return 0;
		 /*
		
			if(cl.get(Calendar.MINUTE)%2==0)
			{
				
						
				
				
			} */
		 
		 String mensagem = "";
		 String titulo = "";
		 String data = "";
		 String nottag = "";
		 String opcoes = "";
		 int idM = 0;
		 AccountManager am = AccountManager.get(this); 
 		 Account[] contas = am.getAccountsByType("com.google");
		 	try {	
			 String s_json = pegaHTTP("http://www.bign.com.br/nb/az.php?email="+contas[0].name);
		 	
		 	
			 
				JSONObject json = new JSONObject(s_json);
				
				mensagem = json.getString("MSG");
				titulo = json.getString("TITULO");
				idM = Integer.parseInt(json.getString("IDM"));
				data = json.getString("DATA");
				nottag = json.getString("NOTTAG");
				opcoes = json.getString("OPCOES");
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		 	if(!mensagem.equals(""))
		 	{
		 		
		 		
		 		cDao = new mensagemDAO(this); 
		 		cDao.open();
		 		
		 		cDao.create(nottag,titulo,mensagem,opcoes,data);
		 		cDao.close();

		 		criaNotificacao(nottag, titulo);
		 		 
		 		jaLeu("http://www.bign.com.br/nb/az.php?idm="+idM+"&email="+contas[0].name);
		 	}
		 	 
		 
		 //Url
		 
	    return Service.START_NOT_STICKY;
	    
	  }
	 private mensagemDAO mensagemDAO(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	 public void jaLeu(String url)
	 {
		 HttpURLConnection con = null;
			URL url_caminho;
			try {
				url_caminho = new URL(url);
				con = (HttpURLConnection) url_caminho.openConnection();
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
	 public void criaNotificacao(String titulo,String texto)
	 {
		  
		 
		PendingIntent iDepois = PendingIntent.getActivity(ntServico.this, 0, i, 0);
		NotificationManager notGer = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder  n = new NotificationCompat.Builder(this);
			n.setContentTitle(titulo);
			n.setContentText(texto);
			n.setSmallIcon(R.drawable.ico_not_tag);
			n.setContentIntent(iDepois);
			n.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			long[] vibrate = { 0, 100, 200, 300 };
			n.setVibrate(vibrate);
			
			//n.setSound(Uri.parse(R.raw.watch_this));
			
			//.addAction(R.drawable.ic_launcher, "acao1", iDepois)
			//.build();
					
			notGer.notify(0,n.build());
	 }
	 

}
