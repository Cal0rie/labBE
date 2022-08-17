package com.youfan.common.handler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MyxmlReader extends DefaultHandler implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
    }

    java.util.Stack tags = new java.util.Stack();

    public MyxmlReader() {
        super();
    }

    public static void main(String args[]) {
        long lasting = System.currentTimeMillis();
        try {
            SAXParserFactory sf = SAXParserFactory.newInstance();
            sf.setValidating(false);
            SAXParser sp = sf.newSAXParser();

            XMLReader reader1 = XMLReaderFactory.createXMLReader();
            reader1.setEntityResolver(new MyxmlReader()); // ignore dtd

            MyxmlReader reader = new MyxmlReader();
            sp.parse(new InputSource("F:\\BaiduNetdiskDownload\\dblp.xml\\dblp.xml"), reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)
                + "毫秒");
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        String tag = (String) tags.peek();
        if (tag.equals("article")) {
            System.out.print("article：" + new String(ch, start, length));
        }
        else if (tag.equals("mdate")) {
            System.out.print("mdate：" + new String(ch, start, length));
        }
        else if (tag.equals("key")) {
            System.out.print("key：" + new String(ch, start, length));
        }
        else if (tag.equals("author")) {
            System.out.print("作者：" + new String(ch, start, length));
        }
        else if (tag.equals("title")) {
            System.out.println("标题:" + new String(ch, start, length));
        }
        else if (tag.equals("pages")) {
            System.out.println("页码:" + new String(ch, start, length));
        }
        else if (tag.equals("year")) {
            System.out.println("年份:" + new String(ch, start, length));
        }
        else if (tag.equals("volume")) {
            System.out.println("volume:" + new String(ch, start, length));
        }
        else if (tag.equals("number")) {
            System.out.println("number:" + new String(ch, start, length));
        }
        else if (tag.equals("url")) {
            System.out.println("url:" + new String(ch, start, length));
        }
        else if (tag.equals("ee")) {
            System.out.println("ee:" + new String(ch, start, length));
        }

    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attrs) {
        tags.push(qName);
    }
}
