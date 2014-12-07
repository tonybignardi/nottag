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

public class VerMsg extends Activity {
	private boolean podeInserir=false;

	@SuppressLint("InlinedApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.vermsg);
		
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
				String dataresp = extras.getString("dataresposta");
				String resp = extras.getString("resposta");
				final String idm = extras.getString("idm");
				final long idmensagem = extras.getLong("idmensagem");
				
				TextView tvn = (TextView) findViewById(R.id.verNot);
				tvn.setText("#"+nottag);

				TextView tvt = (TextView) findViewById(R.id.verTitulo);
				tvt.setText(titulo);
				
				TextView tvd = (TextView) findViewById(R.id.verData);
				tvd.setText(data);
				
				TextView tvm = (TextView) findViewById(R.id.verMensagem);
				tvm.setText(msg);
				
				if(!dataresp.equals(""))
				{
					TextView tdr = (TextView) findViewById(R.id.verDataResp);
					tdr.setText("Respondido em "+dataresp);
				}
				if(!opcoes.equals(""))
				{
					
					final String[] ops = opcoes.split(" ");
					
					LinearLayout ll = (LinearLayout) findViewById(R.id.addBotoes);
					
					
					
					for (int i=0;i<ops.length;i++)
					{
						 Button btn=new Button(this);
						 final int iMagico = i;
						  
						 btn.setId(i);
						 btn.setText(ops[i]);
					
						
						if(ops[i].equals(resp))
						{
						 btn.setBackgroundColor(Color.parseColor("#FF0000"));
						}
						else
						{
							btn.setBackgroundColor(Color.parseColor("#333399"));
						}
						
						
						 
						 btn.setTextColor(Color.WHITE);
						 btn.setWidth(ll.getWidth());
						 
						 final Button btnAcessado =btn;
						 
						 TextView tv = new TextView(this);
						 tv.setHeight(20);
						 
						if(resp.equals(""))
						{
						 btn.setOnClickListener(new OnClickListener() {
							
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								
								
								AlertDialog.Builder b = new AlertDialog.Builder(
				                         VerMsg.this);
							    
				                 b.setMessage("Escolher "+ops[iMagico]+"?");
				                 b.setTitle("Atenção");
				                 b.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
										
										
									}
								});
								b.setPositiveButton("OK",
				                         new DialogInterface.OnClickListener() {
				                             public void onClick(DialogInterface dialog, int which) {
				                                 /// FAZ A ExCLUSAO
				                            	 
				                            	 if(podeInserir)
				                            	 {
				                            	 Nuvem n = new Nuvem();
					                            	 if(n.respondeNot(ops[iMagico],idm,idmensagem,VerMsg.this))
					                            	 {
					                            		 
					                            		 btnAcessado.setBackgroundColor(Color.RED);
					                            		 Toast.makeText(VerMsg.this, "RESPOSTA ENVIADA... OBRIGADO", Toast.LENGTH_LONG).show();
					                            		 finish();
					                            		 
					                            	 }
					                            	 else
					                            	 {
					                            		 
					                            		 Toast.makeText(VerMsg.this, n.getMsgNaoPodeResponder(), Toast.LENGTH_LONG).show();
					                            	 }
				                            	 }
				                            	 else
				                            	 {
				                            		 Toast.makeText(VerMsg.this, "PROBLEMA DE CONEXÃO.. TENTE MAIS TARDE", Toast.LENGTH_LONG).show();
				                            	 }
				                            	 
				                          
				                             }
				                         });
				                 b.show(); 

								
								
								
								
							}
						});
						 
						}
						 
						 
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
