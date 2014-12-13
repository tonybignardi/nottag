package br.com.bign.nottag;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.google.android.gms.ads.AdView;

import br.bign.com.nottag.R;
import br.com.bign.dao.MTagDAO;
import br.com.bign.dao.MinhaMensagemDAO;
import br.com.bign.dao.mensagemDAO;
import br.com.bign.ferramentas.DetectaConexao;
import br.com.bign.ferramentas.Nuvem;
import br.com.bign.ferramentas.ntServico;
import br.com.bign.model.MTag;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class CriaNot extends Activity {

	private MinhaMensagemDAO novoNotDAO;
	private mensagemDAO menDAO;
	public boolean podeInserir = false;
	public Context _contexto;

	private static final String AD_UNIT_ID = "ca-app-pub-5762417695769838/2855282502";
	private AdView adView;
	private RelativeLayout rl;
	private String nottag;
	private String localArquivo = "";
	private TextView messageText;
	private String upLoadServerUri;
	private int serverResponseCode;
	private ProgressDialog dialog;
	private EditText to;
	private EditText tt;
	private EditText tm;
	private String udata;
	private long idm;
	private Button bs;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ImageView iv = (ImageView) findViewById(R.id.imgUp);
		iv.setImageURI(Uri.parse(localArquivo));
		
		

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criaminhanot);

		/************* Php script path ****************/
		upLoadServerUri = "http://www.bign.com.br/nb/upload.php";

		Bundle extras = getIntent().getExtras();
		try {
			if (!extras.isEmpty()) {
				nottag = extras.getString("nottag");
				TextView tv = (TextView) findViewById(R.id.criaNottag);
				tv.setText("#" + nottag);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		Button botaocam = (Button) findViewById(R.id.botaoCamera);
		botaocam.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri = null;
				try {
					uri = Uri.fromFile(createImageFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivity(i);

			}
		});
		// / inicio anuncios
		/*
		 * adView = new AdView(this); adView.setAdUnitId(AD_UNIT_ID);
		 * adView.setAdSize(AdSize.BANNER);
		 * 
		 * AdRequest adRequest = new AdRequest.Builder()
		 * .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		 * .addTestDevice("0123456789ABCDEF") .build();
		 * 
		 * adView.loadAd(adRequest);
		 * 
		 * 
		 * rl = new RelativeLayout(this); rl.addView(adView);
		 * rl.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		 * rl.bringToFront(); rl.setVisibility(RelativeLayout.INVISIBLE);
		 * 
		 * this.addContentView(rl, new LayoutParams(LayoutParams.MATCH_PARENT,
		 * LayoutParams.MATCH_PARENT));
		 * rl.setVisibility(RelativeLayout.VISIBLE);
		 * 
		 * //fim anuncios
		 */
		novoNotDAO = new MinhaMensagemDAO(this);

		tt = (EditText) findViewById(R.id.criaTitulo);
		tm = (EditText) findViewById(R.id.criaMsg);
		to = (EditText) findViewById(R.id.criaOpcoes);

		tt.setEnabled(true);
		tm.setEnabled(true);
		to.setEnabled(true);
		DetectaConexao dc = new DetectaConexao(this);
		if (dc.existeConexao())
			podeInserir = true;
		_contexto = this;

		Button botao = (Button) findViewById(R.id.criaSalvar);
		botao.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (podeInserir) {

					if (!tt.getEditableText().toString().equals("")
							&& !tm.getEditableText().toString().equals("")) {

						tt.setEnabled(false);
						tm.setEnabled(false);
						to.setEnabled(false);

						Toast.makeText(CriaNot.this, "VERIFICANDO...",
								Toast.LENGTH_LONG).show();
						Nuvem n = new Nuvem(CriaNot.this);
						String foto = "";
						if(!localArquivo.equals(""))
							foto ="S";
						
						if (n.podeCriarNotificacao(nottag, tt.getEditableText()
								.toString(), tm.getEditableText().toString(),
								to.getEditableText().toString(),foto, _contexto)) {
							idm = n.getUltimoId();
							udata = n.getUltimaData();

							// CARREGANDO ARQUIVO

							
							

							if(!localArquivo.equals(""))
							{
							dialog = ProgressDialog.show(CriaNot.this, "",	"Carregando arquivo...", true);
								
							new Thread(new Runnable() {
								

								public void run() {
									runOnUiThread(new Runnable() {

										public void run() {
											Toast.makeText(CriaNot.this,
													"ENVIANDO...",
													Toast.LENGTH_LONG).show();
											
											//Toast.makeText(CriaNot.this, "nome"+idm,Toast.LENGTH_LONG).show();
										}
									});

									
									
									
									uploadFile(localArquivo, idm+".jpg");

								}
							}).start();

							//
							}
							else
							{

							novoNotDAO.open();

							novoNotDAO.create(nottag, tt.getEditableText()
									.toString(), tm.getEditableText()
									.toString(), to.getEditableText()
									.toString(), udata, idm);
							novoNotDAO.close();

							finish();
							}
						} else {
							Toast.makeText(
									CriaNot.this,
									"A INSERÇÃO DESSA NOTIFICAÇÃO FOI BLOQUEADA",
									Toast.LENGTH_LONG).show();
							tt.setEnabled(true);
							tm.setEnabled(true);
							to.setEnabled(true);
							tt.setText("");
							tm.setText("");
							to.setText("");
						}

					} else {
						Toast.makeText(CriaNot.this, "CAMPOS VAZIO",
								Toast.LENGTH_LONG).show();
					}

				} else {
					Toast.makeText(CriaNot.this,
							"PROBLEMA DE CONEXÃO.. TENTE MAIS TARDE",
							Toast.LENGTH_LONG).show();

				}

			}
		});
	}

	@SuppressLint("NewApi")
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date(0));
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES
						+ "/nottag/");
		storageDir.mkdirs();
		File image = File.createTempFile("NT_" + imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		localArquivo = "/sdcard/"+Environment.DIRECTORY_PICTURES+"/nottag/"+  image.getName();
		return image;
	}

	public int uploadFile(String sourceFileUri, String nomeArquivo) {

		String fileName = sourceFileUri;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		//Toast.makeText(CriaNot.this, "su..."+sourceFileUri+"nome"+nomeArquivo,Toast.LENGTH_LONG).show();
		if (!sourceFile.isFile()) {

			//dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + localArquivo);

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(CriaNot.this,
							"ARQUIVO NAO EXISTE" + localArquivo,
							Toast.LENGTH_LONG).show();

				}
			});

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("arquivo", nomeArquivo);
				
				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=arquivo;filename=\"" + nomeArquivo + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					runOnUiThread(new Runnable() {
						public void run() {

							String msg = "Imagem Enviada!";
							Toast.makeText(CriaNot.this, msg, Toast.LENGTH_LONG).show();
							
							novoNotDAO.open();

							novoNotDAO.create(nottag, tt.getEditableText()
									.toString(), tm.getEditableText()
									.toString(), to.getEditableText()
									.toString(), udata, idm);
							novoNotDAO.close();
							
						//	menDAO  = new mensagemDAO(CriaNot.this);
						//	menDAO.open();
						//	menDAO.alteraTemFoto(idm);
						//	menDAO.close();
							

							ntServico.jaLeu("http://www.bign.com.br/nb/az.php?enviaNot="+idm);
							
							
							
							

							finish();
							
							

						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {
//
			//	dialog.dismiss();
				ex.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(CriaNot.this, "MalformedURLException",
								Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				//dialog.dismiss();
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(CriaNot.this,
								"Got Exception : see logcat ",
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			//dialog.dismiss();
			return serverResponseCode;

		} // End else block
	}

}

