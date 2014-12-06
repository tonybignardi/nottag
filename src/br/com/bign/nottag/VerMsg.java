package br.com.bign.nottag;

import br.bign.com.nottag.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VerMsg extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.vermsg);
		
		
		Bundle extras = getIntent().getExtras();
		try
		{
			if(!extras.isEmpty())
			{
				
			
				
				
				String nottag = extras.getString("nottag");
				String titulo = extras.getString("titulo");
				String data = extras.getString("data");
				String msg = extras.getString("msg");
				
				TextView tvn = (TextView) findViewById(R.id.verNot);
				tvn.setText(nottag);

				TextView tvt = (TextView) findViewById(R.id.verTitulo);
				tvt.setText(titulo);
				
				TextView tvd = (TextView) findViewById(R.id.verData);
				tvd.setText(data);
				
				TextView tvm = (TextView) findViewById(R.id.verMensagem);
				tvm.setText(msg);
				
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
