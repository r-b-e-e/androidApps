package com.example.group10b_hw05;

import android.util.Xml;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class XMLParser {

    public static class ParseFeeds extends DefaultHandler {
        TedShow podcast;
        ArrayList<TedShow> tedShowsList;
        StringBuilder sb;

        public ArrayList<TedShow> getTedShowsList() {
            return tedShowsList;
        }

        static public ArrayList<TedShow> parsePodcasts(InputStream in) throws IOException, SAXException {
            ParseFeeds parser = new ParseFeeds();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);
            return parser.getTedShowsList();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            tedShowsList = new ArrayList<TedShow>();
            sb = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if (localName.equals("item") || localName.equals("channel")) {
                podcast = new TedShow();
            } else if (localName.equals("image")) {
                podcast.setImage(attributes.getValue("href"));
            } else if (localName.equals("enclosure")) {
                podcast.setAudio(attributes.getValue("url"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            if (localName.equals("item")) {
                tedShowsList.add(podcast);
            } else if (localName.equals("title")) {
                podcast.setTitle(sb.toString().trim());
            } else if (localName.equals("description")) {
                podcast.setDescription(sb.toString().trim());
            } else if (localName.equals("pubDate")) {
                String[] lTempPubDate = sb.toString().trim().split("[0-9]{2}:[0-9]{2}:[0-9]{2}");
                podcast.setPublicationDate(lTempPubDate[0]);
            } else if (localName.equals("duration")) {
                podcast.setDuration(sb.toString().trim());
            }

            sb.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            sb.append(ch, start, length);
            if (sb.equals("itunes:image"))
                podcast.setImage("Hello");
        }

    }

}
