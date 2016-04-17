package com.example.inclass5a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rakesh Balan on 9/28/2015.
 */
public class MainActivity extends AppCompatActivity {
    ArrayList<Double> temperatures;
    EditText editWeather;
    private int counter = 0;
   String sax = "False";
    TextView location;
    TextView maxTemp;
    TextView minTemp    ;
    TextView temperature;
    TextView humidity;
    TextView pressure;
    TextView wind;
    TextView cloud;
    TextView precipitation;

    ArrayList<Weather> weatherObj= new ArrayList<Weather>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatures= new ArrayList<Double>();
        editWeather = (EditText) findViewById(R.id.textSAX);
        location = (TextView) findViewById(R.id.location);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        temperature = (TextView) findViewById(R.id.temperature);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        wind = (TextView) findViewById(R.id.wind);
        cloud = (TextView) findViewById(R.id.clouds);
        precipitation = (TextView) findViewById(R.id.precipitaion);


        final Switch toggle = (Switch) findViewById(R.id.genderSwitch);
        toggle.setChecked(false);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sax = "False";
                } else {
                    sax = "True";
                }
            }
        });



        Button btn= (Button) findViewById(R.id.go);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editWeather.getText().length()>0) {
                    new GetAsyncTaskTest().execute("http://api.openweathermap.org/data/2.5/forecast?q=" + editWeather.getText().toString() + "&mode=xml&cnt=8&units=imperial");
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please Enter a Value",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    public double getMax(ArrayList<Double> temp){
        double min = 0;
        for (int i = 0; i < temp.size(); i++){
            if(temp.get(i) > min){
                min = temp.get(i);
            }
        }
        return min;
    }

    public double getMin(ArrayList<Double> temp){
        double max = 1000;
        for (int i = 0; i < temp.size(); i++){
            if(temp.get(i) < max){
                max = temp.get(i);
            }
        }
        return max;
    }

    public void getfirstClick(View v) {
        if (counter == 0) {
            counter = weatherObj.size() - 1;
        } else {
            counter = counter - 1;
        }
        location.setText("Location:  "+editWeather.getText().toString());
        minTemp.setText("Min Temp: "+getMin(temperatures) +" Fahrenheit");
        maxTemp.setText("Max Temp:  "+getMax(temperatures) +" Fahrenheit");
        temperature.setText("Temperature:  "+weatherObj.get(counter).getTemperature() +" Fahrenheit");
        humidity.setText("Humidity:  "+weatherObj.get(counter).getHumidity());
        pressure.setText("Pressure:  " + weatherObj.get(counter).getPressure());
        wind.setText("Wind:  " + weatherObj.get(counter).getWindSpeed() + "   " + weatherObj.get(counter).getWindDirection());
        cloud.setText("Clouds:   " + weatherObj.get(counter).getClouds());
        precipitation.setText("Precipitation:  "+weatherObj.get(counter).getPrecipitation());

        new getHttpConImage().execute(weatherObj.get(counter).getSymbol());
    }

    public void getlastClick(View v) {
        if (counter == weatherObj.size() - 1) {
            counter = 0;
        } else {
            counter = counter + 1;
        }
        location.setText("Location:  "+editWeather.getText().toString());
        minTemp.setText("Min Temp: " + getMin(temperatures) +" Fahrenheit");
        maxTemp.setText("Max Temp:  " + getMax(temperatures) +" Fahrenheit");
        temperature.setText("Temperature:  "+weatherObj.get(counter).getTemperature() +" Fahrenheit");
        humidity.setText("Humidity:  "+weatherObj.get(counter).getHumidity());
        pressure.setText("Pressure:  "+weatherObj.get(counter).getPressure());
        wind.setText("Wind:  " + weatherObj.get(counter).getWindSpeed() + "   " + weatherObj.get(counter).getWindDirection() );
        cloud.setText("Clouds:   "+weatherObj.get(counter).getClouds());
        precipitation.setText("Precipitation:  "+weatherObj.get(counter).getPrecipitation());
        new getHttpConImage().execute(weatherObj.get(counter).getSymbol());
    }


    private class getHttpConImage extends AsyncTask<String,Void,Bitmap>
    {
        BufferedReader reader=null;
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url=new URL("http://openweathermap.org/img/w/"+params[0]+".png");
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image= BitmapFactory.decodeStream(con.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(reader!=null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            if(s!=null){
            ImageView iv= (ImageView) findViewById(R.id.imageView);
            iv.setImageBitmap(s);}
        }
    }

    class GetAsyncTaskTest extends AsyncTask<String,Void,ArrayList<Weather>> {
        BufferedReader bf;
        @Override
        protected ArrayList<Weather> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                Log.d("demo","terst");
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int source=con.getResponseCode();
                Log.d("demo",""+source);
                if(source==HttpURLConnection.HTTP_OK) {
                    InputStream in =con.getInputStream();
if(sax.equals("False")) {
    Log.d("demoxxxxx","saxparser"+sax);
    return WeatherUtil.WeatherSAXParser.weatherParser(in);
}
else if(sax.equals("True")) {
    Log.d("demoxxxxx","pullparser"+sax);
    return WeatherUtilPull.PullParser.pullParserMethod(in);
}

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<Weather> Weathers) {
            super.onPostExecute(Weathers);
            temperatures.clear();
            if(Weathers!=null){
                weatherObj = Weathers;

                for(int i=0;i<Weathers.size();i++)
                {
                    Double d=0.0;
                    if(weatherObj.get(counter).getTemperature()!=null){
                        d=Double.parseDouble(weatherObj.get(i).getTemperature());}
                    temperatures.add(d);
                    Log.d("demotemp",""+temperatures.toString());
                }
                Log.d("demoxx", Weathers.toString());
                location.setText("Location:  " + editWeather.getText().toString());
                minTemp.setText("Min Temp: " + getMin(temperatures)+" Fahrenheit");
                maxTemp.setText("Max Temp:  "+getMax(temperatures) +" Fahrenheit");
                temperature.setText("Temperature:  "+weatherObj.get(counter).getTemperature()+" Fahrenheit");
                humidity.setText("Humidity:  "+weatherObj.get(counter).getHumidity());
                pressure.setText("Pressure:  " + weatherObj.get(counter).getPressure());
                wind.setText("Wind:  " + weatherObj.get(counter).getWindSpeed() +"   "+weatherObj.get(counter).getWindDirection() );
                cloud.setText("Clouds:   " + weatherObj.get(counter).getClouds());
                precipitation.setText("Precipitation:  " + weatherObj.get(counter).getPrecipitation());
                new getHttpConImage().execute(weatherObj.get(counter).getSymbol());



            }}
    }



}