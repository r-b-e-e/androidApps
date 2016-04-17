package com.example.inclass5a;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Rakesh Balan on 9/28/2015.
 */
public class WeatherUtil {




    static public class WeatherSAXParser extends DefaultHandler {
        ArrayList<Weather> weatherList;
        StringBuilder sb;
        Weather weather;
        public ArrayList<Weather> getWeatherList() {
            return weatherList;
        }

        static ArrayList<Weather> weatherParser(InputStream in) throws IOException, SAXException {
            WeatherSAXParser parser = new WeatherSAXParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);
            return parser.getWeatherList();

        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            weatherList= new ArrayList<Weather>();
            sb=new StringBuilder();

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("time")) {
                weather = new Weather();
            }
            if (localName.equals("temperature")) {
                weather.setTemperature(attributes.getValue("value"));
            }
            if (localName.equals("humidity")) {
                weather.setHumidity(attributes.getValue("value") + attributes.getValue("unit"));
            }
            if (localName.equals("pressure")) {
                weather.setPressure(attributes.getValue("value") + attributes.getValue("unit"));
            }
            if (localName.equals("windSpeed")) {
                weather.setWindSpeed(attributes.getValue("name"));
            }
            if (localName.equals("windDirection")) {
                weather.setWindDirection(attributes.getValue("name"));
            }
            if (localName.equals("clouds")) {
                weather.setClouds(attributes.getValue("value"));
            }
            if (localName.equals("precipitation")) {
                String test="";
                if(attributes.getValue("value")!=null)
                {
                  test="Yes";
                }else{
                     test="No";
                }
                weather.setPrecipitation(test);
            }
            if (localName.equals("symbol")) {
                weather.setSymbol(attributes.getValue("var"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("time")) {
                weatherList.add(weather);}

        }

    }
}
