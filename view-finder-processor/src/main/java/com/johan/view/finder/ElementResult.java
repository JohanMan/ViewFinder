package com.johan.view.finder;

import java.util.List;

/**
 * Created by Johan on 2018/9/9.
 */

public class ElementResult {

    public String packageName;
    public String className;
    public List<ViewField> fieldList;

    public static class ViewField {
        public String type;
        public String name;
        public String id;
    }

}
