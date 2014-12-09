package br.com.bign.model;

public class mensagem {
	
	private long mId;
	private String nottag;
	private String titulo;
	private String msg;
	private String opcoes;
	private String data;
	private String idm;
	private String dataresp;
	private String resposta;
	private String temfoto;
	
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
	public String getIdm() {
		// TODO Auto-generated method stub
		return idm;
	}
	public void setIdm(String idm)
	{
		this.idm=idm;
	}
	public void setResposta(String resp) {
		// TODO Auto-generated method stub
		this.resposta = resp;
	}
	public void setDataResposta(String data) {
		// TODO Auto-generated method stub
		this.dataresp = data;
	
	}
	public String getResposta()
	{
		return resposta;
	}
	public String getDataResposta()
	{
		return dataresp;
	}
	public String getTemFoto()
	{
		return temfoto;
				
	}
	public void setTemFoto(String temfoto) {
		// TODO Auto-generated method stub
		this.temfoto=temfoto;
	}
	
	
	

}
