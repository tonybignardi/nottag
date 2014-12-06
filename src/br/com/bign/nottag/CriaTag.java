package br.com.bign.nottag;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import br.bign.com.nottag.R;
import br.com.bign.dao.MTagDAO;
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
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.view.View.OnClickListener;

public class CriaTag extends Activity {

	private MTagDAO novoNotDAO;
	public boolean podeInserir=false;
	public Context _contexto;
	
	  private static final String AD_UNIT_ID = "ca-app-pub-5762417695769838/2855282502";
	  private AdView adView;
      private RelativeLayout rl;
      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.minhatag);
		
  /// inicio anuncios
        
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
		
		novoNotDAO = new MTagDAO(this);
		
		final EditText tn = (EditText) findViewById(R.id.campoMinhaTag);
		
		tn.setEnabled(true);
		DetectaConexao dc = new DetectaConexao(this);
		if(dc.existeConexao())
			podeInserir=true;
		_contexto=this;
		
		
		Button botao = (Button) findViewById(R.id.botaoSalvarMinhaTag);
		botao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				if(podeInserir)
				{
				
					if(!tn.getEditableText().toString().equals(""))
					{
						tn.setEnabled(false);
						Toast.makeText(CriaTag.this, "VERIFICANDO...", Toast.LENGTH_LONG).show();
						Nuvem n = new Nuvem();
						if(n.podeInserirTag(tn.getEditableText().toString(),_contexto))
						{
						
						novoNotDAO.open();
						novoNotDAO.create(tn.getEditableText().toString());
						novoNotDAO.close();
						
						
						finish();
						}
						else
						{
							Toast.makeText(CriaTag.this, "A TAG "+tn.getEditableText().toString()+
									" JÁ POSSUI DONO..", Toast.LENGTH_LONG).show();
							tn.setEnabled(true);
							tn.setText("");
						}
							
					}
					else
					{
						Toast.makeText(CriaTag.this, "CAMPO VAZIO", Toast.LENGTH_LONG).show();
					}
				
				}
				else
				{
					Toast.makeText(CriaTag.this, "PROBLEMA DE CONEXÃO.. TENTE MAIS TARDE", Toast.LENGTH_LONG).show();
					
				}
				
			
				
			}
		});
	}

	

}

