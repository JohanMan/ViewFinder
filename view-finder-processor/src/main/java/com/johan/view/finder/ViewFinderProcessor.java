package com.johan.view.finder;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Johan on 2018/9/9.
 */

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.johan.view.finder.AutoFind"})
public class ViewFinderProcessor extends AbstractProcessor {

    private ProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AutoFind.class);
        for (Element element : elements) {
            ElementResult result = ElementUtil.parseElement(processingEnvironment, element);
            if (result == null) continue;
            writeJavaFile(result);
        }
        return true;
    }

    private void writeJavaFile(ElementResult result) {
        String javaCode = CodeHelper.createCode(result);
        try {
            JavaFileObject source = processingEnvironment.getFiler()
                    .createSourceFile(result.packageName + "." + CodeHelper.toViewFinderClassName(result.className));
            Writer writer = source.openWriter();
            writer.write(javaCode);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log("写入文件失败 : " + e.getLocalizedMessage());
        }
    }

    private void log(String info) {
        Messager messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "** Log ** \n" + info);
    }

}
