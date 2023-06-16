import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 代码生成工具
 */
public class CodeGenerator {

    // ================= 自定义配置  =================

    private static final DbType DB_TYPE = DbType.MYSQL;
    private static final String DB_NAME = "leadnews_article";
    private static final String HOST_NAME = "localhost";
    private static final String JDBC_URL = "jdbc:mysql://"+HOST_NAME+":3306/"+DB_NAME+"?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";

    /**
     * 需要生成代码的数据库表名(可以自主添加)
     */
    private static String[] TABLES = new String[]{};
    /**
     * 生成文件根目录及包名
     */
    private static final String ROOT_DIR =System.getProperty("user.dir")+ "/src/main/java";
    //java文件夹下路径
    private static final String PACKAGE_PARENT_NAME = "com.heima.article";

    private static final String PACKAGE_CONTROLLER_NAME = "controller";
    private static final String PACKAGE_SERVICE_NAME = "service";
    private static final String PACKAGE_SERVICEIMPL_NAME = "service.impl";
    //entity
    private static final String PACKAGE_ENTITY_NAME = "pojo";
    // 一般情况下要先生成 DTO类 然后修改此参数再生成 PO 类。
    private static final Boolean IS_DTO = false;

    private static final String PACKAGE_MAPPER_NAME = "mapper";
    private static final String RESOURCES_DIR =System.getProperty("user.dir")+  "/src/main/resources";
    private static final String MAPPER_XML_PATH = "mapper";
    /**
     * 生成代码的@author
     */
    private static final String AUTHOR = "itheima";

    /**
     * 数据库表名前缀
     */
    private static final String[] TABLE_PREFIXS = new String[]{"tb_"};


    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        mpg.setDataSource(getDataSourceConfig());
        // 封装需要生成的数据库的所有的表， 如果想单独指定表，可以注释
        getDbAllTables(getDataSourceConfig());

        mpg.setGlobalConfig(getGlobalConfig());
        mpg.setStrategy(getStrategyConfig());
        mpg.setPackageInfo(getPackageConfig());
        mpg.setCfg(getInjectionConfig());
        // 选择 freemarker 引擎，默认 Velocity
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setTemplate(getTemplateConfig());
        // 执行生成
        mpg.execute();
    }

    /**
     * 获取数据库中的所有的表，封装到数组，待生成代码
     * @param dataSourceConfig
     */
    private static void getDbAllTables(DataSourceConfig dataSourceConfig) {

        List<String> dbTables = new ArrayList<>();

        Connection conn = dataSourceConfig.getConn();
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (resultSet.next()) {
                dbTables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dbTables.size() == 0) {
            throw new RuntimeException("当前数据库下无表");
        }

        TABLES = dbTables.toArray(new String[dbTables.size()]);
        System.out.println("数据库表：");
        System.out.println(dbTables);
    }

    /**
     * 数据源配置
     * @return
     */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DB_TYPE);
        dsc.setDriverName(JDBC_DRIVER);
        dsc.setUrl(JDBC_URL);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        return dsc;
    }

    /**
     * 全局配置
     *
     * @return
     */
    private static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        //生成后是否打开资源管理器
        gc.setOpen(false);
        gc.setOutputDir(ROOT_DIR);
        //重新生成时文件是否覆盖
        gc.setFileOverride(true);
        //AR 模式
        gc.setActiveRecord(false);
        //主键策略
        gc.setIdType(IdType.AUTO);
        //定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        //开启Swagger2模式
        gc.setSwagger2(true);
        if (IS_DTO) {
            gc.setEntityName("%sDTO");
        }

        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor(AUTHOR);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        return gc;
    }

    /**
     * 生成策略配置
     *
     * @return
     */
    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix(TABLE_PREFIXS);
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(TABLES);
        // 构建者模型
        strategy.setChainModel(true);

        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);
        //restful api风格控制器
        strategy.setRestControllerStyle(true);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        return strategy;
    }

    /**
     * 生成包名设置
     *
     * @return
     */
    private static PackageConfig getPackageConfig() {
        // 4.生成文件所在包配置：
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(DB_NAME);
        pc.setParent(PACKAGE_PARENT_NAME);
        pc.setController(PACKAGE_CONTROLLER_NAME);
        pc.setService(PACKAGE_SERVICE_NAME);
        pc.setServiceImpl(PACKAGE_SERVICEIMPL_NAME);
        pc.setEntity(PACKAGE_ENTITY_NAME);
        pc.setMapper(PACKAGE_MAPPER_NAME);
        return pc;
    }

    /**
     * xml文件配置
     *
     * @return
     */
    private static InjectionConfig getInjectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                //map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-rb");
                this.setMap(map);
            }
        };
        //xml生成路径
        List<FileOutConfig> focList = new ArrayList<>();
        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return RESOURCES_DIR + "/" + MAPPER_XML_PATH + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        cfg.setFileOutConfigList(focList);

        return cfg;
    }

    /**
     * 关闭默认 xml 生成
     *
     * @return
     */
    private static TemplateConfig getTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
        return tc;
    }
}

