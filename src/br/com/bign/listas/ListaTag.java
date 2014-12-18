package br.com.bign.listas;

import java.util.List;



import br.bign.com.nottag.R;
import br.com.bign.adapters.AdapterLinha;
import br.com.bign.dao.NottagDAO;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.Nuvem;
import br.com.bign.model.Nottag;
import br.com.bign.nottag.SegueTag;

import android.os.Bundle;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListaTag extends ListActivity {

		NottagDAO cDao;
		List<Nottag> tags;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listatag);
			
			getActionBar().setDisplayHomeAsUpEnabled(true);
		cDao = new NottagDAO(this);
		cDao.open();
		tags = cDao.getAll();
		if(tags.isEmpty())
		{
			
			DetectaConexao d = new DetectaConexao(this);
			if(d.existeConexao())
			{
			Toast.makeText(ListaTag.this, "CARREGANDO TAGS...", Toast.LENGTH_SHORT).show();	
			Nuvem n = new Nuvem(ListaTag.this);
			n.tagsQueSigo(this);
			}
			else
			{
				Toast.makeText(ListaTag.this, "SEM CONEXAO..", Toast.LENGTH_SHORT).show();
			}
		
		
		}
		ListView lv = getListView();
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				
				  
				AlertDialog.Builder b = new AlertDialog.Builder(
                         ListaTag.this);
			     final Nottag c = (Nottag) tags.get(pos);
			    
                 b.setMessage("Atenção");
                 b.setTitle("Para de Seguir #"+c.getSegueNot()+"?");
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
		i.putExtra("subtag", "");
		startActivity(i);
		
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.barralistatag, menu);
		return true;
	}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	/*if(item.getItemId()==R.id.adiciona)
	{
		Intent i = new Intent(ListaTag.this,SegueTag.class);
		startActivity(i);
	}
	*/
	if(item.getItemId()==R.id.barraSegueTag)
	{
		Intent i = new Intent(ListaTag.this,SegueTag.class);
		startActivity(i);
	}
	
	
	return super.onOptionsItemSelected(item);
}

}
