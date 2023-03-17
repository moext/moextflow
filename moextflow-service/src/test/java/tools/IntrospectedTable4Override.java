package tools;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
/**
 * 生成的xml文件不合并，直接覆盖原来文件内容
 * @author peng
 */
public class IntrospectedTable4Override extends IntrospectedTableMyBatis3Impl {
	@Override
	public List<GeneratedXmlFile> getGeneratedXmlFiles() {
		boolean isMergeable = false; // 修改了这里，不合并文件内容

		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();
		if (xmlMapperGenerator != null) {
			Document document = xmlMapperGenerator.getDocument();
			GeneratedXmlFile gxf = new GeneratedXmlFile(document, getMyBatis3XmlMapperFileName(),
					getMyBatis3XmlMapperPackage(), context.getSqlMapGeneratorConfiguration().getTargetProject(),
					isMergeable, context.getXmlFormatter());
			if (context.getPlugins().sqlMapGenerated(gxf, this)) {
				answer.add(gxf);
			}
		}
		return answer;
	}
}
