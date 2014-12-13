package br.com.bign.nottag;



import java.text.ChoiceFormat;
import java.util.ArrayList;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdRequest; 

import br.bign.com.nottag.R;
import br.com.bign.dao.ConfigDAO;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.ferramentas.ntServico;
import br.com.bign.listas.ListaMinhas;
import br.com.bign.listas.ListaTag;
import br.com.bign.model.Config;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;

public class NOTActivity extends Activity {
    /** Called when the activity is first created. */
	
	private WebView myWebView;
	private AlertDialog alerta;
	
	 public mensagemDAO cDao;
	  /* Your ad unit id. Replace with your actual ad unit id. */
	 
	  private static final String AD_UNIT_ID = "ca-app-pub-5762417695769838/2855282502";
	  private AdView adView;
      private RelativeLayout rl;
	  
	  
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	ConfigDAO cdao = new ConfigDAO(this);
	cdao.open();
	Config config = cdao.get("email");
 	cdao.close();
 	TextView tv = (TextView) findViewById(R.id.meuEmail);
 	tv.setText(config.getValor());
}
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
       
	   
		
	    /// SERVICO
        ConfigDAO c = new ConfigDAO(this);
        c.open();
        if(c.get("email").getValor().equals(""))
        {
        	
        	
        	ArrayList<String> itens = new ArrayList<String>(); 
        	 

        	 AccountManager am = AccountManager.get(this); 
     		 final Account[] contas = am.getAccountsByType("com.google");
     		 for(int i=0;i<contas.length;i++)
     		 {
     			itens.add(contas[i].name);
     		 }
     		 
     		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.linhaemails, itens);
     		
     		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
     		builder.setTitle("Escolha o E-mail para acesso!"); 
     		builder.setCancelable(false);
     		
     		//define o diálogo como uma lista, passa o adapter.
     		builder.setSingleChoiceItems(adapter, 0, 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int pos) {
							
							ConfigDAO c = new ConfigDAO(NOTActivity.this);
							c.open();
							c.create("email",contas[pos].name);
							onStart();
							
							alerta.dismiss();
						}
					});
			alerta = builder.create();
			alerta.show();

     		
     		
        }
	    
	  
        Intent intent = new Intent(NOTActivity.this, ntServico.class);
		  
	    
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
    		

    		/*Intent intent = new Intent(NOTActivity.this, ntServico.class);
			// PendingIntent pintent = PendingIntent.getService(CXMActivity.this, 0, intent, 0);
			
			//AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			//alarm.cancel(pintent);
			Toast.makeText(NOTActivity.this, "VOCE NÃO RECEBERÁ MAIS NOTIFICAÇÕES", Toast.LENGTH_LONG).show();
			stopService(intent);
			*/
			
    		
    		
    		
    	}
    	
    	if(item.getItemId()==R.id.notificacoes)
    	{

    		
    	/*	 Intent intent = new Intent(NOTActivity.this, ntServico.class);
    			//PendingIntent pintent = PendingIntent.getService(CXMActivity.this, 0, intent, 0);
    			AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

    			Toast.makeText(NOTActivity.this, "AGORA VOCE ESTÁ RECEBENDO NOTIFICAÇÕES", Toast.LENGTH_LONG).show();
    			
    			  
    		    startService(intent);
    		    */
    		
    		
    		
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