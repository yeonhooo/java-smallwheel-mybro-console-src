package smallwheel.mybro.support.builder;

import smallwheel.mybro.common.ClassFileInfo;
import smallwheel.mybro.common.Constants;
import smallwheel.mybro.common.PropertyInfo;
import smallwheel.mybro.common.SharedInfo;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author yeonhooo@gmail.com
 */
public class DtoClassBuilder implements Builder {
	
	private final static Logger LOGGER = Logger.getLogger(DtoClassBuilder.class);
	private final SharedInfo tableInfo = SharedInfo.getInstance();
	
	/**
	 */
	@Override
	public void build() {
		
		String className;
		FileWriter writer;
		
		for (ClassFileInfo classFile : tableInfo.getClassFileInfoList()) {
			
			className = classFile.getName();

			try {
				writer = new FileWriter(Constants.Path.DTO_CLASS_DES_DIR + className + ".java");
				
				// Class 작성 시작
				writer.write("public class " + className + " {");
				
				// Property 작성
				writer.write("\n\t" + "/* properties */" + "\n");
				for (PropertyInfo property : classFile.getPropertyList()) {
					writer.write("\t" + "private " + property.getType() + " " + property.getName() + ";" + "\n");
				}
				
				// Getter, Setter 작성
				writer.write("\n\t" + "/* getter, setter */" + "\n");
				for (PropertyInfo property : classFile.getPropertyList()) {
					
					String columnName = property.getName();				
					columnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
					
					// Getter
					writer.write("\t" + "public " + property.getType() + " get" + columnName + "() {" + "\n");
					writer.write("\t\t" + "return " + property.getName() + ";" + "\n\t" + "}" + "\n");
					
					// Setter
					writer.write("\t" + "public void set" + columnName + "(" + property.getType() + " " + property.getName() + ") {" + "\n");
					writer.write("\t\t" + "this." + property.getName() + " = " + property.getName() + ";" + "\n\t" + "}" + "\n");
				}
				
				// Override toString()
				writer.write("\n\t" + "@Override" + "\n");
				writer.write("\t" + "public String toString() {" + "\n");
				writer.write("\t\t" + "return \"" + className + " [");
				for (int i = 0; i < classFile.getPropertyList().size(); i++) {
					if (0 < i) {
						writer.write(" + \", ");
					}
					writer.write(classFile.getPropertyList().get(i).getName() + "=\" + " + classFile.getPropertyList().get(i).getName());
					if (i % 4 == 0) {
						writer.write("\n\t\t\t");
					}
				}
				writer.write(" + \"]\";\n\t}");
				
				// Class 닫기
				writer.write("\n}");
				writer.close();
			} catch(IOException e){
				LOGGER.error(e.getMessage(), e);	
			}
		}
	}

}