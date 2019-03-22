package com.johan.view.finder;

/**
 * Created by Johan on 2018/9/9.
 */

public class CodeHelper {

    /**
     * 生成代码
     * @param result
     * @return
     */
    public static String createCode(ElementResult result) {
        StringBuilder builder = new StringBuilder();
        // 包名
        builder.append("package ").append(result.packageName).append(";").append("\n\n");
        // 导入包
        builder.append("import android.widget.*;").append("\n");
        builder.append("import android.webkit.*;").append("\n");
        builder.append("import com.johan.view.finder.ViewFinder;").append("\n");
        builder.append("import android.content.Context;").append("\n");
        builder.append("import android.app.Activity;").append("\n");
        builder.append("import android.view.*;").append("\n\n");
        // 开始
        builder.append("public class ").append(toViewFinderClassName(result.className)).append(" implements ViewFinder {").append("\n\n");
        // 字段
        for (ElementResult.ViewField viewField : result.fieldList) {
            builder.append("\t").append("public ").append(viewField.type).append(" ").append(viewField.name).append(";").append("\n");
        }
        builder.append("\n");
        // find context 方法
        builder.append("\t").append("@Override").append("\n");
        builder.append("\t").append("public void find(Activity activity) {").append("\n");
        for (ElementResult.ViewField viewField : result.fieldList) {
            builder.append("\t\t").append(viewField.name).append(" = (").append(viewField.type).append(") activity.findViewById(activity.getResources().getIdentifier(\"").append(viewField.id).append("\", \"id\", activity.getPackageName()));").append("\n");
        }
        builder.append("\t").append("}").append("\n\n");
        // find view 方法
        builder.append("\t").append("@Override").append("\n");
        builder.append("\t").append("public void find(View view) {").append("\n");
        builder.append("\t\t").append("Context context = view.getContext();").append("\n");
        for (ElementResult.ViewField viewField : result.fieldList) {
            builder.append("\t\t").append(viewField.name).append(" = (").append(viewField.type).append(") view.findViewById(context.getResources().getIdentifier(\"").append(viewField.id).append("\", \"id\", context.getPackageName()));").append("\n");
        }
        builder.append("\t").append("}").append("\n\n");
        // 结束
        builder.append("}");
        return builder.toString();
    }

    /**
     * 转成 ViewFinder 类名
     * @param className
     * @return
     */
    public static String toViewFinderClassName(String className) {
        className = className.replace("Activity", "");
        className = className.replace("Fragment", "");
        className = className.replace("Window", "");
        className = className.replace("Dialog", "");
        className += "ViewFinder";
        return className;
    }

}
