package br.com.bign.nottag;

import java.util.List;

import br.bign.com.nottag.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterLinha extends ArrayAdapter <Nottag> {

	private Context contexto;
	private List<Nottag> dados;
	public AdapterLinha(Context context, List  <Nottag> cs) {
		
		super(context, R.layout.linha,cs);
		contexto=context;
		dados = cs;
		// TODO Auto-generated constructor stub
	}
	@Override

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		 View visao = inflater.inflate(R.layout.linha, parent, false);
		 TextView tvNot = (TextView) visao.findViewById(R.id.linhaNot);
		 
		 tvNot.setText(dados.get(position).getSegueNot());
		
		 return visao;
		
	}

	

}
