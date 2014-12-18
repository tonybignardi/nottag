package br.com.bign.nottag;



import java.io.File;

import br.bign.com.nottag.R;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.DownloadImageTask;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VerMsg extends Activity {
	private boolean podeInserir=false;
	private String certa="";

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
				certa = extras.getString("certa");
				String temfoto = extras.getString("temfoto");
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
				
				// Toast.makeText(VerMsg.this, temfoto, Toast.LENGTH_LONG).show();
				if(temfoto.equals("S"))
				{
					ImageView iv = (ImageView) findViewById(R.id.imagemNot);
					iv.setVisibility(ImageView.VISIBLE);
					new DownloadImageTask(iv,idm+".jpg",400).execute("" +
							"http://bign.com.br/b/dothumb.php?img=arquivos/"+idm+".jpg&w=400");
					
					iv.setOnClickListener(new OnClickListener() {
						
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							String s ="/sdcard/"+Environment.DIRECTORY_PICTURES+"/nottag/400"+idm+".jpg";
							
						        
						        File sourceFile = new File(s);
						        if(sourceFile.isFile())
						        {
								Intent intent = new Intent(Intent.ACTION_DEFAULT); 
								
								intent.setDataAndType(Uri.fromFile(sourceFile),"image/*");
								
								startActivity(intent);
						        }
						        else
						        	Toast.makeText(VerMsg.this, "nao tem arquivo", Toast.LENGTH_LONG).show();
						        	
							
						}
					});
					
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
				                            	 Nuvem n = new Nuvem(VerMsg.this);
					                            	 if(n.respondeNot(ops[iMagico],idm,idmensagem,VerMsg.this))
					                            	 {
					                            		 
					                            		 btnAcessado.setBackgroundColor(Color.RED);
					                            		 
					                            		 if(ops[iMagico].equals(certa) && !certa.equals(""))
					                            	     Toast.makeText(VerMsg.this, ":) - LEGAL, VOCÊ ACERTOU", Toast.LENGTH_LONG).show();
					                            		 
					                            		 if(!ops[iMagico].equals(certa) && !certa.equals(""))
					                            		 Toast.makeText(VerMsg.this, ":( -  VOCÊ ERROU", Toast.LENGTH_LONG).show();
					                            		 
					                            		 if(certa.equals(""))
					                            		 Toast.makeText(VerMsg.this, "RESPOSTA ("+ops[iMagico]+") ENVIADA... OBRIGADO", Toast.LENGTH_LONG).show();
					                            		 
					                            		 finish();
					                            		 
					                            		 //
					                            		 
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
						else
						{
							 if(ops[i].equals(certa))
									btn.setBackgroundColor(Color.parseColor("#00AA00"));
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
