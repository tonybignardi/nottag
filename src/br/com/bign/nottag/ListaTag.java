package br.com.bign.nottag;

import java.util.List;

import br.bign.com.nottag.R;

import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaTag extends ListActivity {

		NottagDAO cDao;
		List<Nottag> tags;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listatag);
		cDao = new NottagDAO(this);
		cDao.open();
		tags = cDao.getAll();
		if(tags.isEmpty())
		{
			Toast.makeText(ListaTag.this, "CARREGANDO TAGS...", Toast.LENGTH_SHORT).show();	
			Nuvem n = new Nuvem();
			n.tagsQueSigo(this);
		
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tags = cDao.getAll();
		
		
					
		
		
		
		
		
		ArrayAdapter<Nottag> adapter = new AdapterLinha(this, tags);
		setListAdapter ( adapter );
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Nottag c =(Nottag) l.getItemAtPosition(position); 
	
		Intent i = new Intent(ListaTag.this,ListaMsg.class);
		i.putExtra("nottag", c.getSegueNot());
		startActivity(i);
		
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.adiciona)
	{
		Intent i = new Intent(ListaTag.this,SegueTag.class);
		startActivity(i);
	}
	
	
	return super.onOptionsItemSelected(item);
}

}
