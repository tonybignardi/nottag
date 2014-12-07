package br.com.bign.listas;

import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.adapters.AdapterLinhaMsg;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.model.mensagem;
import br.com.bign.nottag.VerMsg;

import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaMsg extends ListActivity {

		mensagemDAO cDao;
		List<mensagem> msgs;
		String nottag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listamensagens);
		cDao = new mensagemDAO(this);
		
		Bundle extras = getIntent().getExtras();
		try
		{
			if(!extras.isEmpty())
			{
				nottag = extras.getString("nottag");
				TextView tv = (TextView) findViewById(R.id.lNot);
				tv.setText("#"+nottag);
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cDao.open();
		msgs = cDao.getAllWhereTag(nottag);
		
		
		
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
		startActivity(i);
		
		
		
	}
	

}
