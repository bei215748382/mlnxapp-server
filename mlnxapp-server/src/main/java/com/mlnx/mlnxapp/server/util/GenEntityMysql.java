package com.mlnx.mlnxapp.server.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mlnx.mlnxapp.server.model.Comment;
import com.mlnx.mlnxapp.server.model.Doctor;

public class GenEntityMysql {

	private String modelPackageOutPath = "com.mlnx.mlnxapp.server.model";// 指定实体生成所在包的路径
	private String servicePackageOutPath = "com.mlnx.mlnxapp.server.service";//指定添加类所在包的路径
	private String dataPackageOutPath = "com.mlnx.mlnxapp.server.data";//指定仓库类所在包的路径
	private String restPackageOutPath = "com.mlnx.mlnxapp.server.rest";//指定rest类所在包的路径
	private String authorName = "GenEntityMysql工具类生成";// 作者名字
	private String tablename = "doctor";// 表名
	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组
	private int[] colNulls; // 列是否為空，0是非空、1是可以為空
	private boolean f_util = false; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*

	// 数据库连接
	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private static final String NAME = "root";
	private static final String PASS = "123456";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	/*
	 * 构造函数
	 */
	public GenEntityMysql() {
		// 创建连接
		Connection con;
		// 查要生成实体类的表
		String sql = "select table_name from information_schema.tables where table_schema = 'test';";

		PreparedStatement pStemt = null;
		try {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			con = DriverManager.getConnection(URL, NAME, PASS);
			// 获取所有表名
			pStemt = con.prepareStatement(sql);
			ResultSet rs = pStemt.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("table_name").equals("hibernate_sequence")) {
					continue;
				}
				tablename = rs.getString("table_name");
System.out.println(tablename);
				//生成实体类
				String sqlTable = "select * from " + tablename;
				pStemt = con.prepareStatement(sqlTable);
				ResultSetMetaData rsmd = pStemt.getMetaData();
				int size = rsmd.getColumnCount(); // 统计列
				colnames = new String[size];
				colTypes = new String[size];
				colSizes = new int[size];
				colNulls = new int[size];
				for (int i = 0; i < size; i++) {
					colnames[i] = rsmd.getColumnName(i + 1);
					colTypes[i] = rsmd.getColumnTypeName(i + 1);
					colNulls[i] = rsmd.isNullable(i + 1);
					if (colTypes[i].equalsIgnoreCase("datetime")) {
						f_util = true;
					}
					if (colTypes[i].equalsIgnoreCase("image")
							|| colTypes[i].equalsIgnoreCase("text")) {
						f_sql = true;
					}
					colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
				}

				String content = parse(colnames, colTypes, colSizes);

				try {
					File directory = new File("");
					// System.out.println("绝对路径："+directory.getAbsolutePath());
					// System.out.println("相对路径："+directory.getCanonicalPath());
					String path = this.getClass().getResource("").getPath();

					System.out.println(path);
					System.out.println("src/?/"
							+ path.substring(path.lastIndexOf("/com/",
									path.length())));
					// String outputPath = directory.getAbsolutePath()+
					// "/src/"+path.substring(path.lastIndexOf("/com/",
					// path.length()), path.length()) + initcap(tablename) +
					// ".java";
					String outputPath = directory.getAbsolutePath()
							+ "/src/main/java/"
							+ this.modelPackageOutPath.replace(".", "/") + "/"
							+ initcap(tablename) + ".java";
					FileWriter fw = new FileWriter(outputPath);
					PrintWriter pw = new PrintWriter(fw);
					pw.println(content);
					pw.flush();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// 生成类名的注册类
				registration(tablename);
				
				
				//生成Repository类
				repository(tablename);
				
				//生成Rest类
				rest(tablename);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// try {
			// con.close();
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
	}

	/**
	 * 功能：生成service里面注册\删除的代码
	 * 
	 * @param tablename
	 */
	private void registration(String tablename) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + this.servicePackageOutPath + ";\r\n");
		sb.append("\r\n");
		//import
		sb.append("import java.io.Serializable;\r\nimport java.util.logging.Logger;\r\nimport javax.ejb.Stateless;\r\nimport javax.enterprise.event.Event;\r\nimport javax.inject.Inject;\r\nimport javax.persistence.EntityManager;\r\nimport javax.ws.rs.core.Response;\r\n");
		//导入实体类
		sb.append("import "+this.modelPackageOutPath+"."+initcap(tablename)+";\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("* " + tablename + " 服务类\r\n");
		sb.append("* " + new Date() + " " + this.authorName + "\r\n");
		sb.append("*/ \r\n");
		// 实体部分
		sb.append("@SuppressWarnings(\"serial\")\r\n@Stateless\r\npublic class "
				+ initcap(tablename)+"Registration" + " implements Serializable {\r\n");
		sb.append("\r\n\t@Inject\r\n\tprivate Logger log;\r\n");
		sb.append("\r\n\t@Inject\r\n\tprivate EntityManager em;\r\n");	
		sb.append("\r\n\t@Inject\r\n\tprivate Event<"+initcap(tablename)+"> "+tablename+"EventSrc;\r\n");
		//方法、注册
		sb.append("\r\n\tpublic Response register("+initcap(tablename)+" "+tablename+") throws Exception {\r\n");
		sb.append("\r\n\t\tResponse.ResponseBuilder builder = Response.ok();\r\n");
		sb.append("\t\tlog.info(\"Registering "+tablename+" for id:\"+ "+tablename+".getId());\r\n");	
		sb.append("\t\tem.persist("+tablename+");\r\n");		
		sb.append("\t\t"+tablename+"EventSrc.fire("+tablename+");\r\n");	
		sb.append("\t\treturn builder.build();\r\n");
		sb.append("\t}\r\n");		
		
		//方法、删除
		sb.append("\r\n\tpublic Response delete(int id) throws Exception {\r\n");
		sb.append("\r\n\t\tResponse.ResponseBuilder builder = Response.ok();\r\n");
		sb.append("\t\tlog.info(\"Delete "+tablename+" for id: \"+ id);\r\n");	
		sb.append("\t\t"+initcap(tablename)+" "+tablename+" = em.find("+initcap(tablename)+".class,id);\r\n");	
		sb.append("\t\tem.remove("+tablename+");\r\n");
		sb.append("\t\treturn builder.build();\r\n");
		sb.append("\t}\r\n");		
		sb.append("}\r\n");	
		try {
			File directory = new File("");
			String outputPath = directory.getAbsolutePath()
					+ "/src/main/java/"
					+ this.servicePackageOutPath.replace(".", "/") + "/"
					+ initcap(tablename)+"Registration" + ".java";
			FileWriter fw = new FileWriter(outputPath);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(sb.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：生成data里面通用的代码
	 * 
	 * @param tablename
	 */
	private void repository(String tablename){
		StringBuffer sb = new StringBuffer();
		sb.append("package " + this.dataPackageOutPath + ";\r\n");
		sb.append("\r\n");
		//import
		sb.append("import java.util.List;\r\nimport java.util.logging.Logger;\r\nimport javax.enterprise.context.ApplicationScoped;\r\nimport javax.inject.Inject;\r\nimport javax.persistence.EntityManager;\r\nimport javax.persistence.criteria.CriteriaBuilder;\r\nimport javax.persistence.criteria.CriteriaQuery;\r\n");
		//导入实体类
		sb.append("import "+this.modelPackageOutPath+"."+initcap(tablename)+";\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("* " + tablename + " 仓库类\r\n");
		sb.append("* " + new Date() + " " + this.authorName + "\r\n");
		sb.append("*/ \r\n");
		// 实体部分
		sb.append("@ApplicationScoped\r\npublic class "
				+ initcap(tablename)+"Repository" + " {\r\n");
		sb.append("\r\n\t@Inject\r\n\tprivate Logger log;\r\n");
		sb.append("\r\n\t@Inject\r\n\tprivate EntityManager em;\r\n");	
		//方法
		sb.append("\r\n\tpublic "+initcap(tablename)+" findById(int id) {\r\n");
		sb.append("\r\n\t\tlog.info(String.format(\"Find "+tablename+" for id %d.\", id));\r\n");
		sb.append("\t\treturn em.find("+initcap(tablename)+".class, id);\r\n");
		sb.append("\t}\r\n");		
		
		sb.append("\r\n\tpublic List<"+initcap(tablename)+"> findAll() {\r\n");
		sb.append("\r\n\t\tCriteriaBuilder cb = em.getCriteriaBuilder();\r\n");
		sb.append("\t\tCriteriaQuery<"+initcap(tablename)+"> criteria = cb.createQuery("+initcap(tablename)+".class);\r\n");
		sb.append("\t\tcriteria.from("+initcap(tablename)+".class);\r\n");
		sb.append("\t\tlog.info(\"Find all "+tablename+"s.\");\r\n");
		sb.append("\t\treturn em.createQuery(criteria).getResultList();\r\n");
		sb.append("\t}\r\n");		
		sb.append("}\r\n");	

		try {
			File directory = new File("");
			String outputPath = directory.getAbsolutePath()
					+ "/src/main/java/"
					+ this.dataPackageOutPath.replace(".", "/") + "/"
					+ initcap(tablename)+"Repository" + ".java";
			FileWriter fw = new FileWriter(outputPath);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(sb.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：生成rest里面通用的代码
	 * 
	 * @param tablename
	 */
	private void rest(String tablename){
		StringBuffer sb = new StringBuffer();
		sb.append("package " + this.restPackageOutPath + ";\r\n");
		sb.append("\r\n");
		//import
		sb.append("import java.util.HashMap;\r\nimport java.util.HashSet;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport java.util.Set;\r\nimport java.util.logging.Logger;\r\nimport javax.enterprise.context.RequestScoped;\r\nimport javax.inject.Inject;\r\nimport javax.validation.ConstraintViolation;\r\nimport javax.validation.ConstraintViolationException;\r\nimport javax.validation.ValidationException;\r\nimport javax.validation.Validator;\r\nimport javax.ws.rs.Consumes;\r\nimport javax.ws.rs.GET;\r\nimport javax.ws.rs.POST;\r\nimport javax.ws.rs.Path;\r\nimport javax.ws.rs.Produces;\r\nimport javax.ws.rs.WebApplicationException;\r\nimport javax.ws.rs.core.MediaType;\r\nimport javax.ws.rs.core.Response;\r\n");
		//导入实体类
		sb.append("import "+this.modelPackageOutPath+"."+initcap(tablename)+";\r\n");
		sb.append("import "+this.dataPackageOutPath+"."+initcap(tablename)+"Repository;\r\n");
		sb.append("import "+this.servicePackageOutPath+"."+initcap(tablename)+"Registration;\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("* " + tablename + " rest类\r\n");
		sb.append("* " + new Date() + " " + this.authorName + "\r\n");
		sb.append("*/ \r\n");
		// 实体部分
		sb.append("@Path(\"/"+tablename+"s\")\r\n@RequestScoped\r\npublic class "
				+ initcap(tablename)+"RestService" + " {\r\n");
		sb.append("\r\n\t@Inject\r\n\tprivate Logger log;\r\n");
		sb.append("\r\n\t@Inject\r\n\tprivate Validator validator;\r\n");	
		sb.append("\r\n\t@Inject\r\n\tprivate "+initcap(tablename)+"Repository repository;\r\n");	
		sb.append("\r\n\t@Inject\r\n\tprivate "+initcap(tablename)+"Registration registration;\r\n");	
		//方法1
		sb.append("\r\n\t@GET\r\n\t@Path(\"/all\")\r\n\t@Produces(MediaType.APPLICATION_JSON)\r\n\tpublic List<"+initcap(tablename)+"> findAll() {\r\n");
		sb.append("\r\n\t\tList<"+initcap(tablename)+"> "+tablename+"s = repository.findAll();\r\n");
		sb.append("\t\tif ("+tablename+"s == null || "+tablename+"s.size() == 0) {\r\n");
		sb.append("\t\t\tthrow new WebApplicationException(Response.Status.NOT_FOUND);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn "+tablename+"s;\r\n");
		sb.append("\t}\r\n");		
		//方法2
		sb.append("\r\n\t@POST\r\n\t@Consumes(MediaType.APPLICATION_JSON)\r\n\t@Produces(MediaType.APPLICATION_JSON)\r\n\tpublic Response create("+initcap(tablename)+" "+tablename+") {\r\n");
		sb.append("\r\n\t\tResponse.ResponseBuilder builder = null;\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\tvalidate("+tablename+");\r\n");
		sb.append("\t\t\tregistration.register("+tablename+");\r\n");
		sb.append("\t\t\tbuilder = Response.ok();\r\n");
		sb.append("\t\t} catch (ConstraintViolationException ce) {\r\n");		
		sb.append("\t\t\tbuilder = createViolationResponse(ce.getConstraintViolations());\r\n");	
		sb.append("\t\t} catch (Exception e) {\r\n");
		sb.append("\t\t\tMap<String, String> responseObj = new HashMap<String, String>();\r\n");
		sb.append("\t\t\tresponseObj.put(\"error\", e.getMessage());\r\n");
		sb.append("\t\t\tbuilder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn builder.build();\r\n");
		sb.append("\t}\r\n");
		//方法3
		sb.append("\r\n\tprivate void validate("+initcap(tablename)+" "+tablename+") throws ConstraintViolationException, ValidationException {\r\n");
		sb.append("\r\n\t\tSet<ConstraintViolation<"+initcap(tablename)+">> violations = validator.validate("+tablename+");\r\n");
		sb.append("\t\tif (!violations.isEmpty()) {\r\n");
		sb.append("\t\t\tthrow new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t}\r\n");
		//方法4
		sb.append("\r\n\tprivate Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {\r\n");
		sb.append("\r\n\t\tlog.fine(\"Validation completed. violations found: \" + violations.size());\r\n");
		sb.append("\t\tMap<String, String> responseObj = new HashMap<String, String>();\r\n");
		sb.append("\t\tfor (ConstraintViolation<?> violation : violations) {\r\n");
		sb.append("\t\t\tresponseObj.put(violation.getPropertyPath().toString(),violation.getMessage());\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn Response.status(Response.Status.BAD_REQUEST).entity(responseObj);\r\n");
		sb.append("\t}\r\n");
		//方法结束
		sb.append("}");
		
		

		try {
			File directory = new File("");
			String outputPath = directory.getAbsolutePath()
					+ "/src/main/java/"
					+ this.restPackageOutPath.replace(".", "/") + "/"
					+ initcap(tablename)+"RestService" + ".java";
			FileWriter fw = new FileWriter(outputPath);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(sb.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();

		// 判断是否导入工具包
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("package " + this.modelPackageOutPath + ";\r\n");
		sb.append("\r\n");
		// import部分
		sb.append("import java.io.Serializable;\r\nimport javax.persistence.Entity;\r\nimport javax.persistence.GeneratedValue;\r\nimport javax.persistence.Id;\r\nimport javax.validation.constraints.NotNull;\r\nimport javax.xml.bind.annotation.XmlRootElement;\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("* " + tablename + " 实体类\r\n");
		sb.append("* " + new Date() + " " + this.authorName + "\r\n");
		sb.append("*/ \r\n");
		// 实体部分
		sb.append("@SuppressWarnings(\"serial\")\r\n@Entity\r\n@XmlRootElement\r\npublic class "
				+ initcap(tablename) + " implements Serializable {\r\n");
		processAllAttrs(sb);// 属性
		processAllMethod(sb);// get set方法
		sb.append("}\r\n");

		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			if (colnames[i].equalsIgnoreCase("id")) {
				sb.append("\r\n\t@Id\r\n\t@GeneratedValue");
			} else if (colNulls[i] == 0) {
				sb.append("\r\n\t@NotNull");
			}
			sb.append("\r\n\tprivate " + sqlType2JavaType(colTypes[i]) + " "
					+ colnames[i] + ";\r\n");
		}

	}

	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			sb.append("\r\n\tpublic void set" + initcap(colnames[i]) + "("
					+ sqlType2JavaType(colTypes[i]) + " " + colnames[i]
					+ "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\r\n\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}

	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}

		return new String(ch);
	}

	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")
				|| sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")
				|| sqlType.equalsIgnoreCase("timestamp")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}

		return null;
	}

	/**
	 * 出口 TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		new GenEntityMysql();

	}

}
