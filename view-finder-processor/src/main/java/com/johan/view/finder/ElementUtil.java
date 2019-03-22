package com.johan.view.finder;

import java.io.File;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

/**
 * Created by Johan on 2018/9/9.
 */

public class ElementUtil {

    /**
     * 解析 Element
     * @param processingEnvironment
     * @param element
     * @return
     */
    public static ElementResult parseElement(ProcessingEnvironment processingEnvironment, Element element) {
        ElementResult result = new ElementResult();
        // 类名
        result.className = ElementUtil.getClassName(element);
        // 包名
        result.packageName = getPackageName(processingEnvironment.getElementUtils(), element);
        // 布局文件名
        String layout = LayoutHelper.classNameToLayoutName(result.className);
        if (result.className.contains("Activity")) {
            layout = layout.replace("_activity", "");
            layout = "activity_" + layout;
        } else if (result.className.contains("Fragment")) {
            layout = layout.replace("_fragment", "");
            layout = "fragment_" + layout;
        } else if (result.className.contains("Window")) {
            layout = layout.replace("_window", "");
            layout = "window_" + layout;
        } else if (result.className.contains("Dialog")) {
            layout = layout.replace("_dialog", "");
            layout = "dialog_" + layout;
        }
        // 解析注解
        AutoFind annotation = element.getAnnotation(AutoFind.class);
        String value = annotation.value();
        if (!value.equals("")) {
            layout = value;
        }
        layout = layout + ".xml";
        // 查找布局文件
        File layoutFile = FileUtil.findLayout(layout);
        if (layoutFile == null) return null;
        // 解析布局文件 xml
        List<ElementResult.ViewField> fieldList = LayoutHelper.parseLayout(layoutFile);
        result.fieldList = fieldList;
        return result;
    }

    /**
     * 获取 Element 类名
     * @param element
     * @return
     */
    private static String getClassName(Element element) {
        return element.getSimpleName().toString();
    }

    /**
     * 获取 Element 包名
     * @param elementUtils
     * @param element
     * @return
     */
    private static String getPackageName(Elements elementUtils, Element element) {
        return elementUtils.getPackageOf(element.getEnclosingElement()).asType().toString();
    }

}
