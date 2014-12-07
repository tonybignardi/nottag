package br.com.bign.listas;

import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.adapters.AdapterLinha;
import br.com.bign.adapters.AdapterLinhaMinha;
import br.com.bign.dao.MTagDAO;
import br.com.bign.dao.NottagDAO;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.Nuvem;
import br.com.bign.model.MTag;
import br.com.bign.model.Nottag;
import br.com.bign.nottag.CriaTag;
import br.com.bign.nottag.SegueTag;

import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaMinhas extends ListActivity {

		MTagDAO cDao;
		List<MTag> tags;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listaminhas);
		cDao = new MTagDAO(this);
		cDao.open();
		tags = cDao.getAll();
		if(tags.isEmpty())
		{
			
			
			DetectaConexao d = new DetectaConexao(this);
			if(d.existeConexao())
			{
			Toast.makeText(ListaMinhas.this, "CARREGANDO MINHAS TAGS...", Toast.LENGTH_SHORT).show();
			 Nuvem n = new Nuvem();
			n.tagsQueCriei(this);
			}
			else
			{
				Toast.makeText(ListaMinhas.this, "SEM CONEXAO..", Toast.LENGTH_SHORT).show();
			}
				
		
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tags = cDao.getAll();
		
		
					
		
		
		
		
		
		ArrayAdapter<MTag> adapter = new AdapterLinhaMinha(this, tags);
		setListAdapter ( adapter );
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		MTag c =(MTag) l.getItemAtPosition(position); 
	
		Intent i = new Intent(ListaMinhas.this,ListaMMensagem.class);
		i.putExtra("nottag", c.getMtag());
		startActivity(i);
		
		
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menuminha, menu);
		return true;
	}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.criatag)
	{
		Intent i = new Intent(ListaMinhas.this,CriaTag.class);
		startActivity(i);
	}
	
	
	return super.onOptionsItemSelected(item);
}

}
