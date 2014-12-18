package br.com.bign.ferramentas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class DownloadIconTask extends AsyncTask<String, Void, Bitmap> {
	private String nome;
	private int width;


    
    public DownloadIconTask(String nome,int w) {
         this.nome=nome;
         this.width=w;
    }

    @SuppressLint("NewApi")
	protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        String  src= "/sdcard/"+Environment.DIRECTORY_PICTURES+"/nottag/"+width+nome;
        int count;
        File sourceFile = new File(src);
        
		
        
        
        try {
        	
        	if (!sourceFile.isFile()) {
        		
            InputStream in = new java.net.URL(urldisplay).openStream();
            
            OutputStream output = new FileOutputStream(src);
            
            byte data[] = new byte[1024];
 
            long total = 0;
 
            
			while ((count = in.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                //publishProgress(""+(int)((total*100)/lenghtOfFile));
 
                // writing data to file
                output.write(data, 0, count);
            }
 
            // flushing output
            output.flush();
 
            // closing streams
            output.close();
            in.close();
        	}
 
            
            mIcon11 = BitmapFactory.decodeFile(src);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

  
}