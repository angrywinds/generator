/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;

/**
 * @author lukefeng
 * 
 */
public class JavaMapperExtendGenerator extends AbstractJavaClientGenerator {

    public JavaMapperExtendGenerator() {
        super(false);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        String typeFullName = introspectedTable.getMyBatis3JavaMapperType();
        if (typeFullName == null || typeFullName == "") {
            return new ArrayList<CompilationUnit>();
        }
        int idx = typeFullName.lastIndexOf('.');
        if (idx == -1) {
            return new ArrayList<CompilationUnit>();
        }
        String extendTypeFullName = typeFullName.substring(0, idx) + ".extend"
            + typeFullName.substring(idx) + "Extend";
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(extendTypeFullName);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        FullyQualifiedJavaType baseType = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaMapperType());
        interfaze.addSuperInterface(baseType);
        interfaze.addImportedType(baseType);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
            answer.add(interfaze);
        }
        
        return answer;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return null;
    }

}