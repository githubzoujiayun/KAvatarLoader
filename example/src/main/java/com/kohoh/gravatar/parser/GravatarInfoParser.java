package com.kohoh.gravatar.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kohoh on 14-8-16.
 */
public class GravatarInfoParser {
    static public List<GravatarInfo> parse(String path_name) throws ParserConfigurationException, IOException, SAXException, GravatarParserException {
        DocumentBuilder builder = getDocumentBuilder();
        File gravatar_file = new File(path_name);

        if (path_name == null) {
            throw new GravatarParserException("path_name is null");
        }

        if (!gravatar_file.exists()) {
            throw new FileNotFoundException(path_name + " not found");
        }

        return parse(builder.parse(gravatar_file));
    }

    static public List<GravatarInfo> parse(InputStream input_stream) throws ParserConfigurationException, GravatarParserException, IOException, SAXException {
        DocumentBuilder builder = getDocumentBuilder();

        if (input_stream == null) {
            throw new GravatarParserException("InputStream is null");
        }

        return parse(builder.parse(input_stream));
    }

    static private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder;
    }

    static private List<GravatarInfo> parse(Document document) throws GravatarParserException {
        if (document == null) {
            throw new GravatarParserException("doucument is null");
        }

        List<GravatarInfo> gravatar_info_list = new ArrayList<GravatarInfo>();
        NodeList gravatar_list = document.getElementsByTagName("Gravatar");
        for (int i = 0; i < gravatar_list.getLength(); i++) {
            Element gravatar_element = (Element) gravatar_list.item(i);
            String email = getEmail(gravatar_element.getElementsByTagName("Email"));
            String hash_code = getHashCode(gravatar_element.getElementsByTagName("HashCode"));
            String url = getUrl(gravatar_element.getElementsByTagName("Url"));
            gravatar_info_list.add(new GravatarInfo(email, hash_code, url));
        }
        return gravatar_info_list;
    }

    static private String getEmail(NodeList email_list) throws GravatarParserException {
        return getText(email_list, "Email");
    }

    static private String getHashCode(NodeList hash_code_list) throws GravatarParserException {
        return getText(hash_code_list, "HashCode");
    }

    static private String getUrl(NodeList url_list) throws GravatarParserException {
        return getText(url_list, "Url");
    }

    static private String getText(NodeList node_list, String tag_name) throws GravatarParserException {
        String text = null;

        if (node_list != null && node_list.getLength() > 0) {
            Node node_element = node_list.item(0);
            if (node_element.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node_element;
                if (tag_name.equals(element.getTagName())) {
                    Node node_text = element.getFirstChild();
                    if (node_text != null && node_text.getNodeType() == Node.TEXT_NODE) {
                        text = node_text.getNodeValue();
                    } else {
                        throw new GravatarParserException("not text node or node is null");
                    }
                } else {
                    throw new GravatarParserException("tag_name wrong. expect tag = " + tag_name + ", actural tag = "
                            + element.getTagName() + ".");
                }
            } else {
                throw new GravatarParserException("not element node or node is null");
            }
        } else {
            throw new GravatarParserException("node list is null or node_list has not node");
        }

        return text;
    }

    static public void printGravatarInfoList(List<GravatarInfo> info_list) {
        if (info_list != null) {
            System.out.println("------PRINT GravatarInfo List---------");
            System.out.println("length is " + info_list.size());
            Iterator<GravatarInfo> iterator = info_list.iterator();
            while (iterator.hasNext()) {
                GravatarInfo info = iterator.next();
                printGravatarInfo(info);
                System.out.println();
            }
            System.out.println("------PRINT END---------");
        }
    }

    static public void printGravatarInfo(GravatarInfo info) {
        if (info != null) {
            System.out.println("email = " + info.getEmail());
            System.out.println("hash_code = " + info.getHashCode());
            System.out.println("url = " + info.getUrl());
        }
    }
}
