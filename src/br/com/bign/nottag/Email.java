package br.com.bign.nottag;


import java.util.ArrayList;

import br.bign.com.nottag.R;
import br.com.bign.dao.ConfigDAO;
import br.com.bign.ferramentas.meuBancoHelper;
import br.com.bign.listas.ListaTag;
import br.com.bign.model.Config;
import br.com.bign.model.Nottag;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;


public class Email extends Activity {

	private AlertDialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
       
        ConfigDAO cdao = new ConfigDAO(Email.this);
		 cdao.open();
		 Config config = cdao.get("email");
		 cdao.close();
		
		TextView tv = (TextView) findViewById(R.id.emailtv);
		
		tv.setText(config.getValor());
		Button bt = (Button) findViewById(R.id.emailAlterar);
		bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				
				ArrayList<String> itens = new ArrayList<String>(); 
	        	 

	        	 AccountManager am = AccountManager.get(Email.this); 
	     		 final Account[] contas = am.getAccountsByType("com.google");
	     		 for(int i=0;i<contas.length;i++)
	     		 {
	     			itens.add(contas[i].name);
	     		 }
	     		 
	     		ArrayAdapter adapter = new ArrayAdapter(Email.this, R.layout.linhaemails, itens);
	     		
	     		AlertDialog.Builder builder = new AlertDialog.Builder(Email.this); 
	     		builder.setTitle("Escolha o E-mail para acesso!"); 
	     		
	     		//define o diálogo como uma lista, passa o adapter.
	     		builder.setSingleChoiceItems(adapter, 0, 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int pos) {
								
								final int posicaEscolhida = pos;
								AlertDialog.Builder b = new AlertDialog.Builder(
				                         Email.this);

							    
				                 b.setTitle("Atenção");
				                 b.setMessage("Isso excluirá as mensagens recebidas!!");
				                 b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {

										
										
									}
								});
								b.setPositiveButton("Sim",
				                         new DialogInterface.OnClickListener() {
				                             public void onClick(DialogInterface dialog, int which) {
				                                 /// FAZ A ExCLUSAO
				                            	 ConfigDAO c = new ConfigDAO(Email.this);
				 								c.open();
				 								c.trocaConfig("email",contas[posicaEscolhida].name);
				 								c.trocaUsuario();
				 								c.close();
				 								alerta.dismiss();
				 								finish();
				                             }
				                         });
				                 b.show();
								
								
								
								
							}
						});
				alerta = builder.create();
				alerta.show();
				
				
				
			}
		});
		        
		    
		
        
        
    }


    
    
}

