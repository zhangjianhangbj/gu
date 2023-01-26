package manage.report;


import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class GeneratorCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		lmd();
	}

	public void genCode() {

		// 1、创建代码生成器
//        AutoGenerator mpg = null;

		// 2、全局配置
//        new GlobalConfig.Builder().;
//        String projectPath = System.getProperty("user.dir"); //获取该项目的全路径
//        gc.setOutputDir(projectPath + "/src/main/java");//设置生成后存放的路径
//        gc.setAuthor("Helen");//设置作者
//        gc.setOpen(false); //生成后是否打开资源管理器
//        gc.setServiceName("%sService");	//去掉Service接口的首字母I
//        gc.setIdType(IdType.AUTO); //主键策略 ，在生成的对象所有的id属性都会加上      @TableId(value = "id", type = IdType.AUTO)注解，主键自增
//        gc.setSwagger2(true);//开启Swagger2模式，自动生成接口文档，使用必须引入依赖下面的依赖，生成时每个属性都会有    @ApiModelProperty(value = "编号")这样的注解 会把创建表时的字段注释拿过来，这样可以清楚知道每个字段对应的意思
//        mpg.setGlobalConfig(gc);
//
//        // 3、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig.Builder();
////        dsc.setUrl("jdbc:mysql://localhost:3306/数据库库名?serverTimezone=GMT%2B8&characterEncoding=utf-8");//后面两个参数分别是设置时区和字符集
////        dsc.setDriverName("com.mysql.cj.jdbc.Driver");//设置jdbc驱动，mysql8以下是com.mysql.jdbc.Drive，mysql8以上是com.mysql.cj.jdbc.Driver
////        dsc.setUsername("root");//数据库username
////        dsc.setPassword("123456");//数据库password
////        dsc.setDbType(DbType.MYSQL);
////        mpg.setDataSource(dsc);
//
//        // 4、包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent("com.srb.core");
//        pc.setEntity("pojo.entity"); //此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
//        mpg.setPackageInfo(pc);
//
//        // 5、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略，由下划线命名改为驼峰命名
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);  //列名下划线转驼峰命名
//        strategy.setEntityLombokModel(true);    //给所有的实体类加lombok注解，不自动生成get和set，但加上了@Data注解，会在编译的时候自动生成get和set
//        strategy.setLogicDeleteFieldName("is_deleted");   //逻辑删除字段名，给逻辑删除字段加逻辑注解 @logicDelete
//        strategy.setEntityBooleanColumnRemoveIsPrefix(true); //去掉is_前缀，阿里的开发文档规定属性名不要以is开头
//        strategy.setRestControllerStyle(true);  //restful api风格，返回json的
//        mpg.setStrategy(strategy);
//
//        //6执行
//        mpg.execute();}

	}

	private static void lmd() {
		FastAutoGenerator.create("jdbc:mysql://192.168.1.149:3306/gu_db?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "ychristySL2012!#") //写需要生成的数据库的信息
				.globalConfig(builder -> {
					builder.author("zjh") // 设置作者
							.enableSwagger().author("张建航") // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.disableOpenDir()
							.dateType(DateType.ONLY_DATE)
							.outputDir("E:/git/code/gu-service-web/"); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent("org.zjh.web") // 设置父包名,就是生成后的类的包名字
							.moduleName("gp") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:/git/code/gu-service-web/")); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {
					builder.addInclude("gp_code").disableSqlFilter() // 设置需要生成的表名
							.addTablePrefix("t_", "ck_","te_","st_") // 设置过滤表前缀 ，这些不需要这是注掉
							.mapperBuilder().enableBaseResultMap().enableBaseColumnList()
							.entityBuilder().enableLombok()
//				.entityBuilder().addTableFills(Arrays.asList(
//						new Column("create_time", FieldFill.INSERT),
//						new Column("update_time", FieldFill.INSERT_UPDATE))) //设置时间自动填充
							.controllerBuilder().enableRestStyle(); //用RestController注解
				})
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.templateConfig(builder -> {builder.controller("/controller.java");})
				.templateConfig(builder -> {builder.entity("/entity.java");})
				// /template/controller.java
				.execute();
	}

}
