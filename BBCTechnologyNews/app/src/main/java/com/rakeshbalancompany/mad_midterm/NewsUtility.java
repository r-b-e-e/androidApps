package com.rakeshbalancompany.mad_midterm;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Rakesh Balan LIngakumar
 */
public class NewsUtility {

    static public class MediaSAXParser extends DefaultHandler {
        ArrayList<News> mediaList;
        StringBuilder sb;
        News news;
        public ArrayList<News> getMediaList() {
            return mediaList;
        }

        static ArrayList<News> mediaParser(InputStream in) throws IOException, SAXException {
            MediaSAXParser parser = new MediaSAXParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);
            return parser.getMediaList();

        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            mediaList= new ArrayList<News>();
            sb=new StringBuilder();

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("item"))
            {
                news = new News();
            }


            else if (localName.equals("thumbnail") && Integer.parseInt(attributes.getValue("width"))>100) {
                news.setThumb_image(attributes.getValue("url"));
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("item")) {

                if(news!=null){   mediaList.add(news);}}
            else if(localName.equals("title")){
                if(news!=null){
                    news.setTitle(sb.toString());}
            }
            else if(localName.equals("link")){
                if(news!=null){
                    news.setNews_link(sb.toString());
                }
            }
            else if(localName.equals("pubDate")){
                if(news!=null){
                    news.setPub_date(sb.toString());
                }
            }
            sb.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            sb.append(ch,start,length);
        }
    }
}