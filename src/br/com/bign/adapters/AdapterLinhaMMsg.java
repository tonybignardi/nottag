package br.com.bign.adapters;

import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.model.MinhaMensagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterLinhaMMsg extends ArrayAdapter <MinhaMensagem> {

	private Context contexto;
	private List<MinhaMensagem> dados;
	public AdapterLinhaMMsg(Context context, List  <MinhaMensagem> cs) {
		
		super(context, R.layout.linhaminhasmensagens,cs);
		contexto=context;
		dados = cs;
		// TODO Auto-generated constructor stub
	}
	@Override

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		 View visao = inflater.inflate(R.layout.linhaminhasmensagens, parent, false);
		 TextView tvtit = (TextView) visao.findViewById(R.id.linhaMinhaMsgTitulo);
		 TextView tvmsg = (TextView) visao.findViewById(R.id.linhaMinhaMsgMsg);
		 
		 tvtit.setText(dados.get(position).getTitulo());
		 tvmsg.setText(dados.get(position).getMsg());
		
		 return visao;
		
	}

	

}
