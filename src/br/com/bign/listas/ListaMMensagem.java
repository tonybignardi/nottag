package br.com.bign.listas;

import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.adapters.AdapterLinhaMMsg;
import br.com.bign.dao.MinhaMensagemDAO;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.Nuvem;
import br.com.bign.model.MTag;
import br.com.bign.model.MinhaMensagem;
import br.com.bign.nottag.CriaNot;
import br.com.bign.nottag.VerNot;
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

public class ListaMMensagem extends ListActivity {

		MinhaMensagemDAO cDao;
		List<MinhaMensagem> tags;
		private String nottag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listaminhasmensagens);
		
			Bundle extras = getIntent().getExtras();
			try
			{
				if(!extras.isEmpty())
				{
					nottag = extras.getString("nottag");
					TextView tv = (TextView) findViewById(R.id.lMNot);
					tv.setText("#"+nottag);
					
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			
			cDao = new MinhaMensagemDAO(this);
		cDao.open();
		tags = cDao.getAllWhereTag(nottag);
		
		
		
		DetectaConexao d = new DetectaConexao(this);
		if(tags.isEmpty())
		{
			
			
		
			if(d.existeConexao())
			{
			Toast.makeText(ListaMMensagem.this, "CARREGANDO MINHAS #NOTS...", Toast.LENGTH_SHORT).show();
			 Nuvem n = new Nuvem();
			n.notsQueCriei(this,nottag);
			}
			else
			{
				Toast.makeText(ListaMMensagem.this, "SEM CONEXAO..", Toast.LENGTH_SHORT).show();
			}
				
		
		}
		
		if(d.existeConexao())
		{
			Nuvem nu = new Nuvem();
			String segui = nu.contaSeguidores(nottag,ListaMMensagem.this);
			TextView tvcont = (TextView) findViewById(R.id.lMMcontador);
			tvcont.setText("Voc� possui "+segui+" seguidores");
			
			
		}
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tags = cDao.getAllWhereTag(nottag);
		
		
					
		
		
		
		
		
		ArrayAdapter<MinhaMensagem> adapter = new AdapterLinhaMMsg(this, tags);
		setListAdapter ( adapter );
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		MinhaMensagem c =(MinhaMensagem) l.getItemAtPosition(position); 
	
		Intent i = new Intent(ListaMMensagem.this,VerNot.class);
		
		i.putExtra("nottag", c.getNottag());
		i.putExtra("titulo", c.getTitulo());
		i.putExtra("data",c.getData());
		i.putExtra("msg", c.getMsg());
		i.putExtra("opcoes", c.getOpcoes());
		i.putExtra("idm",c.getIdm());
		
		startActivity(i);
		
		
		
		
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menuminhamsg, menu);
		return true;
	}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.criaminhanot)
	{
		Intent i = new Intent(ListaMMensagem.this,CriaNot.class);
		i.putExtra("nottag", nottag);
		startActivity(i);
	}
	
	
	return super.onOptionsItemSelected(item);
}

}
