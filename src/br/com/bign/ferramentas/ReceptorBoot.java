package br.com.bign.ferramentas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceptorBoot extends BroadcastReceiver {   

	@Override
	public void onReceive(Context c, Intent i) {
		
		
		
		  Intent myIntent = new Intent(c, ntServico.class);
		  c.startService(myIntent);
		
	}
}