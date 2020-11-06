package networking.jsonDataFetcher.retrofitParsing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NetworkCaller extends AsyncTask<URL, Void, String> {
    private Context context;
    private int rc;
    private TextView textView;
    NetworkCaller(Context context, int rc, TextView textView){
        this.context = context;
        this.rc = rc;
        this.textView = textView;
    }
    @Override
    protected String doInBackground(URL... urls) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urls[0].openConnection();
            httpURLConnection.setAllowUserInteraction(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(100000);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            rc = responseCode;
            if( responseCode  == HttpURLConnection.HTTP_OK){
                textView.setText("Data Fetched Successfully");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String placeVal = "";
                while( ( placeVal = reader.readLine() ) != null ){
                    stringBuffer.append(placeVal) ;
                }
                return stringBuffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Erprp", "errrrrr");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s)  {
        super.onPostExecute(s);
        /*try {
            JSONObject jsonObject = new JSONObject(s);
            String d = jsonObject.getString("login");
            Toast.makeText(context, d, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}