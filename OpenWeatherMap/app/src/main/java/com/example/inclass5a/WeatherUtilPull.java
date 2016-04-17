package com.example.inclass5a;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Rakesh Balan on 9/28/2015.
 */
public class WeatherUtilPull {

    static class PullParser {

        static ArrayList<Weather> pullParserMethod(InputStream in) throws XmlPullParserException, IOException

        {
            Weather weather = null;
            ArrayList<Weather> weatherList = new ArrayList<Weather>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            int event = parser.getEventType();
            parser.setInput(in, "UTF-8");

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("time")) {
                            weather = new Weather();
                        } else if (parser.getName().equals("temperature")) {
                            weather.setTemperature(parser.getAttributeValue(null, "value"));
                        } else if (parser.getName().equals("humidity")) {
                            weather.setHumidity(parser.getAttributeValue(null, "value") + parser.getAttributeValue(null, "unit"));

                        } else if (parser.getName().equals("pressure")) {
                            weather.setPressure(parser.getAttributeValue(null, "value") + parser.getAttributeValue(null, "unit"));
                        }
                        else if (parser.getName().equals("windSpeed")) {
                            weather.setWindSpeed(parser.getAttributeValue(null, "name"));
                        }
                        else if (parser.getName().equals("windDirection")) {
                            weather.setWindDirection(parser.getAttributeValue(null, "name"));
                        }
                        else if (parser.getName().equals("clouds")) {
                            weather.setClouds(parser.getAttributeValue(null, "value"));
                        }
                        else if (parser.getName().equals("precipitation")) {
                            String test="";
                            if(parser.getAttributeValue(null, "type")!=null)
                            {
                                test="Yes";
                            }else{
                                test="No";
                            }
                            weather.setPrecipitation(test);
                        }
                        else if (parser.getName().equals("symbol")) {
                            weather.setSymbol(parser.getAttributeValue(null, "var"));
                        }





                        break;
                    case XmlPullParser.END_TAG:

                        if (parser.getName().equals("time")) {
                            weatherList.add(weather);
                            break;

                        }


                }

                event = parser.next();
            }


            return weatherList;


        }
    }
}
