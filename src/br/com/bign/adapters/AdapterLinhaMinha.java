package br.com.bign.adapters;

import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.model.MTag;
import br.com.bign.model.Nottag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterLinhaMinha extends ArrayAdapter <MTag> {

	private Context contexto;
	private List<MTag> dados;
	public AdapterLinhaMinha(Context context, List  <MTag> cs) {
		
		super(context, R.layout.linhaminhas,cs);
		contexto=context;
		dados = cs;
		// TODO Auto-generated constructor stub
	}
	@Override

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		 View visao = inflater.inflate(R.layout.linhaminhas, parent, false);
		 TextView tvNot = (TextView) visao.findViewById(R.id.linhaMinha);
		 
		 tvNot.setText(dados.get(position).getMtag());
		
		 return visao;
		
	}

	

}
