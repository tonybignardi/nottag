package br.com.bign.listas;

import java.util.ArrayList;
import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.adapters.AdapterLinhaMsg;
import br.com.bign.dao.ConfigDAO;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.model.Nottag;
import br.com.bign.model.mensagem;
import br.com.bign.nottag.VerMsg;

import android.net.Uri;
import android.os.Bundle;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListaMsg extends ListActivity {

		mensagemDAO cDao;
		List<mensagem> msgs;
		String nottag;
		private Button tv;
		private AlertDialog alerta;
		private TextView tcont;
		private String subtag;
				
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listamensagens);
			
			getActionBar().setDisplayHomeAsUpEnabled(true);
		cDao = new mensagemDAO(this);
		
		Bundle extras = getIntent().getExtras();
		try
		{
			if(!extras.isEmpty())
			{
				nottag = extras.getString("nottag");
				subtag = extras.getString("subtag");
				tv = (Button) findViewById(R.id.lNot);
				if(subtag.equals("") || !subtag.equals(nottag))
				tv.setText("#"+nottag);
				else
				{
					tv.setText("#"+nottag+" #"+subtag);
				}
				
				
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		tv.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				
				ArrayList<String> itens = new ArrayList<String>(); 
	        	 

	        	 
	     		 cDao.open();
	     		 final String[] subs = cDao.getSubTags(nottag); 
	     		 for(int i=0;i<subs.length;i++)
	     		 {
	     			if(subs[i].equals("#"))
	     				subs[i]="#"+nottag;
	     				
	     			itens.add(subs[i]);
	     		 }
	     		 
	     		ArrayAdapter adapter = new ArrayAdapter(ListaMsg.this, R.layout.linhaemails, itens);
	     		
	     		AlertDialog.Builder builder = new AlertDialog.Builder(ListaMsg.this); 
	     		builder.setTitle("Escolha uma subtag"); 
	     		
	     		//define o diálogo como uma lista, passa o adapter.
	     		builder.setSingleChoiceItems(adapter, 0, 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int pos) {
								
								cDao.open();
								
								
								tv = (Button) findViewById(R.id.lNot);
								tcont = (TextView) findViewById(R.id.contadorAcertos);
								
								subtag=subs[pos].replace("#", "");
								
								if(subs[pos].replace("#","").equals(nottag))
								{
									msgs = cDao.getAllWhereTag(nottag);
									tv.setText("#"+nottag);
									tcont.setText("Você possui "+cDao.countAllWhereTag(nottag)+" acertos");
								}
								else
								{
									tv.setText("#"+nottag+" "+subs[pos]);
									msgs = cDao.getAllWhereTagAndSubTag(nottag,subtag);
									tcont.setText("Você possui "+cDao.countAllWhereTagAndSubTag(nottag,subtag)+" acertos");
									
								}
								
								
							
								alerta.dismiss();
								
								
								
								ArrayAdapter<mensagem> adapter = new AdapterLinhaMsg(ListaMsg.this, msgs);
								setListAdapter ( adapter );
								
								
								
								
								
							}
						});
				alerta = builder.create();
				alerta.show();
				
				
				
			}
		});	
		
		
		
		
		
		
		
		ListView lv = getListView();
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				
				  
				AlertDialog.Builder b = new AlertDialog.Builder(
                         ListaMsg.this);
			     final mensagem c = (mensagem) msgs.get(pos);
			    
                 b.setMessage("Atenção");
                 b.setTitle("Exluir Mensagem");
                 b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {

						
						
					}
				});
				b.setPositiveButton("Sim",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 /// FAZ A ExCLUSAO
                            cDao.open();
                            cDao.delete(c);
                            onResume();
                        	 dialog.dismiss();
                             }
                         });
                 b.show();

				return true;
			}
			
		});

		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cDao.open();
		
		tcont = (TextView) findViewById(R.id.contadorAcertos);
		if(subtag.equals("") || !subtag.equals(nottag))
		{
				msgs = cDao.getAllWhereTag(nottag);
				tv.setText("#"+nottag);
				tcont.setText("Você possui "+cDao.countAllWhereTag(nottag)+" acertos");
				
			
		}
		else
			{
			msgs = cDao.getAllWhereTagAndSubTag(nottag,subtag);
				tv.setText("#"+nottag+" #"+subtag);
				tcont.setText("Você possui "+cDao.countAllWhereTagAndSubTag(nottag,subtag)+" acertos");
			}
		
		
		
				
		
		
		ArrayAdapter<mensagem> adapter = new AdapterLinhaMsg(this, msgs);
		setListAdapter ( adapter );
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		mensagem c =(mensagem) l.getItemAtPosition(position); 
		Intent i = new Intent(ListaMsg.this,VerMsg.class);
		i.putExtra("nottag",c.getNottag());
		i.putExtra("titulo", c.getTitulo());
		i.putExtra("data", c.getData());
		i.putExtra("msg", c.getMsg());
		i.putExtra("opcoes", c.getOpcoes());
		i.putExtra("idm",c.getIdm());
		i.putExtra("idmensagem", c.getmId());
		i.putExtra("resposta", c.getResposta());
		i.putExtra("dataresposta", c.getDataResposta());
		i.putExtra("temfoto", c.getTemFoto());
		i.putExtra("certa", c.getCerta());
		startActivity(i);
		
		
		
	}
	

}
