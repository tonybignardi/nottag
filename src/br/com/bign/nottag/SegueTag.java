package br.com.bign.nottag;

import br.bign.com.nottag.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class SegueTag extends Activity {

	private NottagDAO novoNotDAO;
	public boolean podeInserir=false;
	public Context _contexto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nottag);
		
		
		novoNotDAO = new NottagDAO(this);
		
		final EditText tn = (EditText) findViewById(R.id.campoNot);
		
		tn.setEnabled(true);
		DetectaConexao dc = new DetectaConexao(this);
		if(dc.existeConexao())
			podeInserir=true;
		_contexto=this;
		
		
		Button botao = (Button) findViewById(R.id.botaoSalvar);
		botao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				if(podeInserir)
				{
				
					if(!tn.getEditableText().toString().equals(""))
					{
						tn.setEnabled(false);
						Toast.makeText(SegueTag.this, "SALVANDO...", Toast.LENGTH_LONG).show();
						Nuvem n = new Nuvem();
						n.insereTag(tn.getEditableText().toString(),_contexto);
						
						novoNotDAO.open();
						novoNotDAO.create(tn.getEditableText().toString());
						novoNotDAO.close();						
						finish();
					}
					else
					{
						Toast.makeText(SegueTag.this, "CAMPO VAZIO", Toast.LENGTH_LONG).show();
					}
				
				}
				else
				{
					Toast.makeText(SegueTag.this, "PROBLEMA DE CONEXÃO.. TENTE MAIS TARDE", Toast.LENGTH_LONG).show();
					
				}
				
			
				
			}
		});
	}

	

}

