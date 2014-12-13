package br.com.bign.nottag;



import br.bign.com.nottag.R;

import br.com.bign.dao.mensagemDAO;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.DownloadImageTask;
import br.com.bign.ferramentas.Nuvem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebViewClient;

public class VerNot extends Activity {
	private boolean podeInserir=false;
	private WebView myWebView;
	private mensagemDAO mdao;

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
				
				 mdao = new mensagemDAO(VerNot.this);
				 
			/*	if(mdao.temFoto(idm).equals("S"))
				{
					ImageView iv = (ImageView) findViewById(R.id.imagemVerNot);
					iv.setVisibility(ImageView.VISIBLE);
					new DownloadImageTask(iv).execute("" +
							"http://bign.com.br/b/dothumb.php?img=arquivos/"+idm+".jpg&w=400");
					
				}
				*/
				
				
		
				
	//new DownloadImageTask((ImageView) findViewById(R.id.imgTeste)).execute("http://bign.com.br/nb/img/bign_pequeno.png");
	
				
				
				
				
				
				 myWebView = (WebView) findViewById(R.id.webvernot);
		        

		        
		        WebSettings settings = myWebView.getSettings();
		        //Faz os links abrirem na mesma webview
		        settings.setLoadWithOverviewMode(true);
		        //ignora o view port da pagina
		        settings.setUseWideViewPort(false);
		        //ativa o uso de javascript
		        settings.setJavaScriptEnabled(true);
		        //desabilita os controles de zoom
		        settings.setBuiltInZoomControls(false);
		        //desabilita o zoom de dois taps e o zoom de caixas de texto
		        settings.setSupportZoom(false);
		        //deixa o zoom inicial como longe
		        //settings.setDefaultZoom(ZoomDensity.FAR);
		       
		    
		        
		        
		    
				
				DetectaConexao d = new DetectaConexao(this);
				int contResp[]=null;
				if(d.existeConexao())
				{
				
					Nuvem n = new Nuvem(VerNot.this);
					contResp = n.contaRespostas(idm,this);
					totalResp=n.getTotalresp();
					
					 myWebView.loadUrl("http://bign.com.br/nb/grafico.php?contaresp="+idm);
				        
				        myWebView.setWebViewClient(new WebViewClient() {
				        	 
				        	   @Override
				        	   public boolean shouldOverrideUrlLoading(WebView view, String url) {
				        	    view.loadUrl(url);
				        	    return true;
				        	   }
				        	   //Em caso de erro recebido exibimos um html interno 
				        	   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				        		   myWebView.loadUrl("file:///android_asset/erro.html");
				        	    //ouPodemos simplesmente ocultar a wv
				        	    //wv.setVisibility(View.GONE);
				        	   }
				        	  });
					
				   
			        
					
					
				}
				else
				{
					Toast.makeText(VerNot.this, "PROBLEMAS NA CONEXÃO.. TENTE DENOVO ", Toast.LENGTH_LONG).show();
					finish();
					
				}

				
				TextView tvn = (TextView) findViewById(R.id.vernotNot);
				tvn.setText("#"+nottag);

				TextView tvt = (TextView) findViewById(R.id.vernotTitulo);
				tvt.setText(titulo);
				
				TextView tvd = (TextView) findViewById(R.id.vernotData);
				tvd.setText(data);
				
				TextView tvm = (TextView) findViewById(R.id.vernotMensagem);
				tvm.setText(msg);
				


				
								
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
	}

}
