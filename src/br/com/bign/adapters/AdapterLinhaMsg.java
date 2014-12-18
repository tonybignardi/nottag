package br.com.bign.adapters;

import java.util.List;

import br.bign.com.nottag.R;
import br.com.bign.ferramentas.DownloadImageTask;
import br.com.bign.model.mensagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterLinhaMsg extends ArrayAdapter <mensagem> {

	private Context contexto;
	private List<mensagem> dados;
	public AdapterLinhaMsg(Context context, List  <mensagem> cs) {
		
		super(context, R.layout.linhamsgnova,cs);
		contexto=context;
		dados = cs;
		// TODO Auto-generated constructor stub
	}
	@Override

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		 View visao = inflater.inflate(R.layout.linhamsgnova, parent, false);
		 TextView tvtit = (TextView) visao.findViewById(R.id.linhaMsgTitulo);
		 TextView tvmsg = (TextView) visao.findViewById(R.id.linhaMsgMsg);
		 
		 tvtit.setText(dados.get(position).getTitulo());
		 tvmsg.setText(dados.get(position).getMsg());
		 
		 if(dados.get(position).getTemFoto().equals("S"))
		 {
		 ImageView im = (ImageView) visao.findViewById(R.id.iconemsg);
		 new DownloadImageTask(im,dados.get(position).getIdm()+".jpg",200).execute("" +
					"http://bign.com.br/b/dothumb.php?img=arquivos/"+dados.get(position).getIdm()+".jpg&w=200");
		 }		
		
		 return visao;
		
	}

	

}
