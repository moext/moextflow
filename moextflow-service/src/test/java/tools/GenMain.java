package tools;

/**
 * 生产数据操作层代码
 * @author peng
 */
public class GenMain {
	public static void main(String[] args) {
		String configFile = "/generatorConfig.xml";
		try {
			String[] tableNames = new String[] {"t_todo_task","t_task_comment"};
			GenMybatisFiles.gen(configFile, tableNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
