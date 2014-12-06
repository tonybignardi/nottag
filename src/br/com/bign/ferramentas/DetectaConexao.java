package br.com.bign.ferramentas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DetectaConexao {
    private Context _context;
    
    public DetectaConexao(Context context){
        this._context = context;
    }
 
    public boolean existeConexao(){
        ConnectivityManager connectivity = (ConnectivityManager)
         _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) 
          {
              NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
              
              // Se n�o existe nenhum tipo de conex�o retorna false
              if (netInfo == null) {
                return false;
              }
              
              int netType = netInfo.getType();

              // Verifica se a conex�o � do tipo WiFi ou Mobile e 
              // retorna true se estiver conectado ou false em
              // caso contr�rio
              if (netType == ConnectivityManager.TYPE_WIFI || 
                    netType == ConnectivityManager.TYPE_MOBILE) {
                  return netInfo.isConnected();

              } else {
                  return false;
              }
          }else{
            return false;
          }
    }
    
}