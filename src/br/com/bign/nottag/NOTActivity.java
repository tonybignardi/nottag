package br.com.bign.nottag;



import java.util.Calendar;
import java.util.regex.Pattern;


import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdRequest; 

import android.accounts.Account;

import br.bign.com.nottag.R;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.ferramentas.ntServico;
import br.com.bign.listas.ListaMinhas;
import br.com.bign.listas.ListaTag;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.view.View.OnClickListener;

public class NOTActivity extends Activity {
    /** Called when the activity is first created. */
	
	private WebView myWebView;
	
	 public mensagemDAO cDao;
	  /* Your ad unit id. Replace with your actual ad unit id. */
	 
	  private static final String AD_UNIT_ID = "ca-app-pub-5762417695769838/2855282502";
	  private AdView adView;
      private RelativeLayout rl;
	  
	  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        /// inicio anuncios
        
        adView = new AdView(this);
	    adView.setAdUnitId(AD_UNIT_ID);
	    adView.setAdSize(AdSize.BANNER);
	    
	    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("0123456789ABCDEF")
        .build();
	    
	    adView.loadAd(adRequest);
	

	    rl = new RelativeLayout(this);
	    rl.addView(adView);
	    rl.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
	    rl.bringToFront();
	    rl.setVisibility(RelativeLayout.INVISIBLE);
	    
	    this.addContentView(rl, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rl.setVisibility(RelativeLayout.VISIBLE);
		
		//fim anuncios
        
        
        
        
        Button btCria = (Button) findViewById(R.id.botaoCriarHome);
        btCria.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(NOTActivity.this,ListaMinhas.class);
				startActivity(i);
				
				
			}
		}) ;
        
        Button btSegue = (Button) findViewById(R.id.botaoSeguirHome);
        btSegue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(NOTActivity.this,ListaTag.class);
				startActivity(i);
				
				
			}
		}) ;
       
        /* myWebView = (WebView) findViewById(R.id.webview);
        

        
        WebSettings settings = myWebView.getSettings();
        //Faz os links abrirem na mesma webview
        settings.setLoadWithOverviewMode(true);
        //ignora o view port da pagina
        settings.setUseWideViewPort(false);
        //ativa o uso de javascript
        settings.setJavaScriptEnabled(true);
        //desabilita os controles de zoom
        settings.setBuiltInZoomControls(false);
        //desabilita o zoom de dois taps e o zoom de caixas de texto
        settings.setSupportZoom(false);
        //deixa o zoom inicial como longe
        //settings.setDefaultZoom(ZoomDensity.FAR);
        
        myWebView.loadUrl("http://bign.com.br/nb/");
        
        myWebView.setWebViewClient(new WebViewClient() {
        	 
        	   @Override
        	   public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	    view.loadUrl(url);
        	    return true;
        	   }
        	   //Em caso de erro recebido exibimos um html interno 
        	   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        		   myWebView.loadUrl("file:///android_asset/erro.html");
        	    //ouPodemos simplesmente ocultar a wv
        	    //wv.setVisibility(View.GONE);
        	   }
        	  });
        
        
        */
    
	   
		
	    /// SERVICO
	    
	    Intent intent = new Intent(NOTActivity.this, ntServico.class);
		//PendingIntent pintent = PendingIntent.getService(CXMActivity.this, 0, intent, 0);
		//AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

		//Calendar cal = Calendar.getInstance();
		
		//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 5000, pintent); 
	    // SERVICO
	   
		
        
		  //  Toast.makeText(CXMActivity.this, "CONSTRUIU", Toast.LENGTH_LONG).show();
		
		  
	    startService(intent);
	    
	  
	    
		   
    }
    
     @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    }
     @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu); 
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	
    	
    	if(item.getItemId()==R.id.sair)
    	{

    		
    		Intent intent = new Intent(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
    		
    	}
    	
    	if(item.getItemId()==R.id.parar)
    	{
    		

    		Intent intent = new Intent(NOTActivity.this, ntServico.class);
			// PendingIntent pintent = PendingIntent.getService(CXMActivity.this, 0, intent, 0);
			
			//AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			//alarm.cancel(pintent);
			Toast.makeText(NOTActivity.this, "VOCE NÃO RECEBERÁ MAIS NOTIFICAÇÕES", Toast.LENGTH_LONG).show();
			stopService(intent);
			
    		
    		
    		
    	}
    	
    	if(item.getItemId()==R.id.notificacoes)
    	{

    		
    		 Intent intent = new Intent(NOTActivity.this, ntServico.class);
    			//PendingIntent pintent = PendingIntent.getService(CXMActivity.this, 0, intent, 0);
    			AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

    			Toast.makeText(NOTActivity.this, "AGORA VOCE ESTÁ RECEBENDO NOTIFICAÇÕES", Toast.LENGTH_LONG).show();
    			
    			  
    		    startService(intent);
    		
    		
    		
    	}
    	
      	if(item.getItemId()==R.id.seguir)
    	{

    		
      		Intent intent = new Intent(NOTActivity.this, ListaTag.class);

			startActivity(intent);
    		
    		
    		
    	}
      	
    	if(item.getItemId()==R.id.criar)
    	{

    		
      		Intent intent = new Intent(NOTActivity.this, ListaMinhas.class);

			startActivity(intent);
    		
    		
    		
    	}
    	
    	
    	
    	
    	if(item.getItemId()==R.id.email)
    	{

    		Intent intent = new Intent(NOTActivity.this, Email.class);

			startActivity(intent);
			
    		
    	}
    	
    	
    	
    	return super.onOptionsItemSelected(item);
    }




}