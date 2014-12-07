package br.com.bign.model;

public class MinhaMensagem {
	
	private long mId;
	private String nottag;
	private String titulo;
	private String msg;
	private String opcoes;
	private String data;
	private long idm;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public long getmId() {
		return mId;
	}
	public void setmId(long mId) {
		this.mId = mId;
	}
	public String getNottag() {
		return nottag;
	}
	public void setNottag(String nottag) {
		this.nottag = nottag;
	}
	public String getOpcoes() {
		return opcoes;
	}
	public void setOpcoes(String opcoes) {
		this.opcoes = opcoes;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getIdm()
	{
		return idm;
	}
	public void setIdm(long idm) {
		// TODO Auto-generated method stub
		this.idm = idm;
		
	}
	
	
	

}
