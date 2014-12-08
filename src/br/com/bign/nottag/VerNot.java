package br.com.bign.nottag;



import br.bign.com.nottag.R;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.Nuvem;
import android.view.View.OnClickListener;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VerNot extends Activity {
	private boolean podeInserir=false;

	@SuppressLint("InlinedApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.vernot);
		
		DetectaConexao dc = new DetectaConexao(this);
		if(dc.existeConexao())
			podeInserir=true;
		
		
		Bundle extras = getIntent().getExtras();
		try
		{
			if(!extras.isEmpty())
			{
				
				String nottag = extras.getString("nottag");
				String titulo = extras.getString("titulo");
				String data = extras.getString("data");
				String msg = extras.getString("msg");
				String opcoes = extras.getString("opcoes");
				long idm = extras.getLong("idm");
				int totalResp=0;
		
				
				DetectaConexao d = new DetectaConexao(this);
				int contResp[]=null;
				if(d.existeConexao())
				{
				
					Nuvem n = new Nuvem();
					contResp = n.contaRespostas(idm,this);
					totalResp=n.getTotalresp();
					
					
				}
				else
				{
					Toast.makeText(VerNot.this, "PROBLEMAS NA CONEXÃO.. TENTE DENOVO ", Toast.LENGTH_LONG).show();
					finish();
					
				}
				Toast.makeText(VerNot.this, "tot "+totalResp, Toast.LENGTH_LONG).show();
				
				TextView tvn = (TextView) findViewById(R.id.vernotNot);
				tvn.setText("#"+nottag);

				TextView tvt = (TextView) findViewById(R.id.vernotTitulo);
				tvt.setText(titulo);
				
				TextView tvd = (TextView) findViewById(R.id.vernotData);
				tvd.setText(data);
				
				TextView tvm = (TextView) findViewById(R.id.vernotMensagem);
				tvm.setText(msg);
				


				
				if(!opcoes.equals(""))
				{
					
					 
					final String[] ops = opcoes.split(" ");
					
					
					LinearLayout ll = (LinearLayout) findViewById(R.id.vernotaddBotoes);
					
					
					

					for (int i=0;i<ops.length;i++)
					{
						 Button btn=new Button(this);
						
						 
						 int p=0;
						 if(totalResp!=0)
							 p=(contResp[i]/totalResp)*100;
						 

						 int porc = 20;	
						 if(p!=0)
						 porc = p*(4);
						 
					
						 
						 
						 btn.setId(i);
					//	 if
						 btn.setText(ops[i]+" ("+contResp[i]+") - "+p+"%");
					
						
						
						btn.setBackgroundColor(Color.parseColor("#333399"));
						
						
						
						
						 
						 btn.setTextColor(Color.WHITE);
						 
						 
						
						 //btn.setWidth(ll.getWidth());
						 
						btn.setWidth(porc);
						Toast.makeText(VerNot.this, "p"+i+"-"+porc, Toast.LENGTH_LONG).show();
						 
						 
						 TextView tv = new TextView(this);
						 tv.setHeight(20);
						 
												 
						 
					     ll.addView(btn);
						 ll.addView(tv);
						 
					}
					
				}
				
				
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
	}

}
