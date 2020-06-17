## 使用说明

### 项目 build.gradle 文件

```groovy
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://github.com/johanmans/view-finder/raw/master" }
        maven { url "https://github.com/johanmans/view-finder-annotation/raw/master" }
        maven { url "https://github.com/johanmans/view-finder-processor/raw/master" }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

### app模块 build.gradle 文件

```groovy
// dependencies 添加依赖
dependencies {
    compile 'com.johan:view-finder:1.12'
    compile 'com.johan:view-finder-annotation:1.12'
    annotationProcessor 'com.johan:view-finder-processor:1.12'
}
```

