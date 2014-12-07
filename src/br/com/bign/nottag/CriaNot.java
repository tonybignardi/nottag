package br.com.bign.nottag;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import br.bign.com.nottag.R;
import br.com.bign.dao.MTagDAO;
import br.com.bign.dao.MinhaMensagemDAO;
import br.com.bign.dao.NottagDAO;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.Nuvem;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.view.View.OnClickListener;

public class CriaNot extends Activity {

	private MinhaMensagemDAO novoNotDAO;
	public boolean podeInserir=false;
	public Context _contexto;
	
	  private static final String AD_UNIT_ID = "ca-app-pub-5762417695769838/2855282502";
	  private AdView adView;
      private RelativeLayout rl;
	private String nottag;
      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criaminhanot);

		Bundle extras = getIntent().getExtras();
		try
		{
			if(!extras.isEmpty())
			{
				nottag = extras.getString("nottag");
				TextView tv = (TextView) findViewById(R.id.criaNottag);
				tv.setText("#"+nottag);
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
  /// inicio anuncios
     /*   
        adView = new AdView(this);
	    adView.setAdUnitId(AD_UNIT_ID);
	    adView.setAdSize(AdSize.BANNER);
	    
	    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("0123456789ABCDEF")
        .build();
	    
	    adView.loadAd(adRequest);
	

	    rl = new RelativeLayout(this);
	    rl.addView(adView);
	    rl.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
	    rl.bringToFront();
	    rl.setVisibility(RelativeLayout.INVISIBLE);
	    
	    this.addContentView(rl, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		rl.setVisibility(RelativeLayout.VISIBLE);
		
		//fim anuncios
		*/
		novoNotDAO = new MinhaMensagemDAO(this);
		
		
		final EditText tt = (EditText) findViewById(R.id.criaTitulo);
		final EditText tm = (EditText) findViewById(R.id.criaMsg);
		final EditText to = (EditText) findViewById(R.id.criaOpcoes);
		
		tt.setEnabled(true);
		tm.setEnabled(true);
		to.setEnabled(true);
		DetectaConexao dc = new DetectaConexao(this);
		if(dc.existeConexao())
			podeInserir=true;
		_contexto=this;
		
		
		Button botao = (Button) findViewById(R.id.criaSalvar);
		botao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				if(podeInserir)
				{
				
					if(!tt.getEditableText().toString().equals("") && !tm.getEditableText().toString().equals(""))
					{
						
						tt.setEnabled(false);
						tm.setEnabled(false);
						to.setEnabled(false);
						
						Toast.makeText(CriaNot.this, "VERIFICANDO...", Toast.LENGTH_LONG).show();
						Nuvem n = new Nuvem();
						if(n.podeCriarNotificacao(nottag,tt.getEditableText().toString(),
								tm.getEditableText().toString(),to.getEditableText().toString(),_contexto))
						{
						long idm= n.getUltimoId();
						String udata = n.getUltimaData();
						novoNotDAO.open();
						
						novoNotDAO.create(nottag,tt.getEditableText().toString(),
								tm.getEditableText().toString(),to.getEditableText().toString(),udata,idm);
						novoNotDAO.close();
						
						
						finish();
						}
						else
						{
							Toast.makeText(CriaNot.this, "A INSERÇÃO DESSA NOTIFICAÇÃO FOI BLOQUEADA", Toast.LENGTH_LONG).show();
							tt.setEnabled(true);
							tm.setEnabled(true);
							to.setEnabled(true);
							tt.setText("");
							tm.setText("");
							to.setText("");
						}
							
					}
					else
					{
						Toast.makeText(CriaNot.this, "CAMPOS VAZIO", Toast.LENGTH_LONG).show();
					}
				
				}
				else
				{
					Toast.makeText(CriaNot.this, "PROBLEMA DE CONEXÃO.. TENTE MAIS TARDE", Toast.LENGTH_LONG).show();
					
				}
				
			
				
			}
		});
	}

	

}

