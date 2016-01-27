package com.example.administrator.myapplication;

import android.os.AsyncTask;

/**
 * Created by think on 2016/1/27.
 */
public class MySyncTask extends AsyncTask<String,Integer,String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        String result="";
        int myProgress=0;
        int inputLength=params[0].length();
        for(int i=1;i<=inputLength;i++){
            myProgress=i;
            result=result+params[0].charAt(inputLength-i);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){}
            publishProgress(myProgress);
        }


        return result;
    }
}
