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

public class ListaMinhas extends ListActivity {

		MTagDAO cDao;
		List<MTag> tags;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.listaminhas);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		cDao = new MTagDAO(this);
		cDao.open();
		tags = cDao.getAll();
		if(tags.isEmpty())
		{
			
			
			DetectaConexao d = new DetectaConexao(this);
			if(d.existeConexao())
			{
			Toast.makeText(ListaMinhas.this, "CARREGANDO MINHAS TAGS...", Toast.LENGTH_SHORT).show();
			 Nuvem n = new Nuvem(ListaMinhas.this);
			n.tagsQueCriei(this);
			}
			else
			{
				Toast.makeText(ListaMinhas.this, "SEM CONEXAO..", Toast.LENGTH_SHORT).show();
			}
				
		
		}
		
		ListView lv = getListView();
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				
				  
				AlertDialog.Builder b = new AlertDialog.Builder(
                         ListaMinhas.this);
			     final MTag c = (MTag) tags.get(pos);
			    
                 b.setTitle("Atenção - Pense Bem!");
                 b.setMessage("Excluir #NotTag\n Você perderá o direito sobre ela!");
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
		//getMenuInflater().inflate(R.menu.menuminha, menu);
		getMenuInflater().inflate(R.menu.barracriatag, menu);
		
		return true;
	}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	/*if(item.getItemId()==R.id.criatag)
	{
		Intent i = new Intent(ListaMinhas.this,CriaTag.class);
		startActivity(i);
	}
	*/
	
	if(item.getItemId()==R.id.barraCriaTag)
	{
		Intent i = new Intent(ListaMinhas.this,CriaTag.class);
		startActivity(i);
	}
	
	
	return super.onOptionsItemSelected(item);
}

}
