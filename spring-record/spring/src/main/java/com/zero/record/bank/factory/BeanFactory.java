package com.zero.record.bank.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    private static Map<String, Object> map = new HashMap<>();

    static {
        final InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        final Document document;
        try {
            document = saxReader.read(resourceAsStream);
            final Element rootElement = document.getRootElement();
            final List<Element> beanList = rootElement.selectNodes("//bean");
            for (Element element : beanList) {
                final String id = element.attributeValue("id");
                final String clazz = element.attributeValue("class");
                final Class<?> aClass = Class.forName(clazz);
                final Object o = aClass.newInstance();
                map.put(id, o);
            }

            // 有property自元素的bean就有传值需求
            final List<Element> propertyList = rootElement.selectNodes("//property");
            for (Element element : propertyList) {
                final String name = element.attributeValue("name");
                final String ref = element.attributeValue("ref");

                // 找到当前被需要处理依赖关系的bean
                final Element parent = element.getParent();

                // 调用父元素对象的反射功能
                final String parentId = parent.attributeValue("id");
                final Object parentObj = map.get(parentId);
                // 遍历父对象中的所有方法
                final Method[] methods = parentObj.getClass().getMethods();
                for (Method method : methods) {
                    if (method.getName().equals("set" + name)) {
                        method.invoke(parentObj, map.get(ref));
                    }
                }
                map.put(parentId, parentObj);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Object getBean(String id) {
        return map.get(id);
    }
}
