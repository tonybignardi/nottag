package br.com.bign.nottag;


import br.bign.com.nottag.R;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;


public class Email extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
       
        try{
        AccountManager am = AccountManager.get(this); 
		Account[] contas = am.getAccountsByType("com.google");
		
		TextView tv = (TextView) findViewById(R.id.emailtv);
		
		tv.setText(contas[0].name);
		}catch(Exception e)
		{
			Toast.makeText(Email.this, "ERRO AO BUSCAR CONTA GOOGLE", Toast.LENGTH_LONG).show();
		}
      
		        
		        
		    
		
        
        
    }


    
    
}

