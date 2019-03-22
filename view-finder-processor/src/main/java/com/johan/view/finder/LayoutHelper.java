package com.johan.view.finder;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Johan on 2018/9/9.
 */

public class LayoutHelper {

    /**
     * ClassName 生成 LayoutName
     * @param className
     * @return
     */
    public static String classNameToLayoutName(String className) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                if (builder.length() == 0) {
                    builder.append(Character.toLowerCase(c));
                } else {
                    builder.append("_").append(Character.toLowerCase(c));
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * IdName 生成 FieldName
     * @param idName
     * @return
     */
    public static String idNameToFieldName(String idName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < idName.length(); i++) {
            char c = idName.charAt(i);
            if (c == '_' ) {
                if (i < idName.length() - 1) {
                    char nextC = idName.charAt(++i);
                    builder.append(Character.toUpperCase(nextC));
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 解析布局文件
     * @param layoutFile
     * @return
     */
    public static List<ElementResult.ViewField> parseLayout(File layoutFile) {
        List<ElementResult.ViewField> fieldList = new ArrayList<>();
        parseXml(layoutFile, fieldList);
        return fieldList;
    }

    /**
     * 解析 XML
     * @param layoutFile
     * @param fieldList
     */
    private static void parseXml(File layoutFile, List<ElementResult.ViewField> fieldList) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParserHandler handler = new SAXParserHandler(fieldList);
            SAXParser parser = factory.newSAXParser();
            parser.parse(layoutFile, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * XML 解析器
     */
    private static class SAXParserHandler extends DefaultHandler {

        private List<ElementResult.ViewField> fieldList;

        public SAXParserHandler(List<ElementResult.ViewField> fieldList) {
            this.fieldList = fieldList;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            // include ViewStub
            if ("include".equals(qName) || "ViewStub".equals(qName)) {
                // 获取 layout 属性值
                String layoutValue = findAttribute(attributes, "layout");
                if (layoutValue == null) return;
                String layout = filterAttribute(layoutValue);
                // 查找 layout 文件
                layout += ".xml";
                File layoutFile = FileUtil.findLayout(layout);
                if (layoutFile == null) return;
                // 解析 layout XML
                parseXml(layoutFile, fieldList);
                return;
            }
            // 找到 id 属性
            String idValue = findAttribute(attributes, "android:id");
            if (idValue == null) return;
            String id = filterAttribute(idValue);
            ElementResult.ViewField viewField = new ElementResult.ViewField();
            viewField.type = qName;
            viewField.id = id;
            viewField.name = idNameToFieldName(id);
            fieldList.add(viewField);
        }

        /**
         * 找到某个属性的值
         * @param attributes
         * @param attribute
         * @return
         */
        private String findAttribute(Attributes attributes, String attribute) {
            int count = attributes.getLength();
            for (int i = 0; i < count; i++) {
                String attributeName = attributes.getQName(i);
                if (attribute.equals(attributeName)) {
                    return attributes.getValue(i);
                }
            }
            return null;
        }

        /**
         * 过滤掉属性值前面无用的信息
         * 如 @+id/main_view --> main_view
         * @param attribute
         * @return
         */
        private String filterAttribute(String attribute) {
            int index = attribute.indexOf("/");
            if (index == -1) return attribute;
            return attribute.substring(index + 1);
        }

    }

}
