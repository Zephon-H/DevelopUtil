public class MyBatisPlusDemoGenerator {
    public static void main(String[] args) {
        //test为自定义的文件生成路径
        String path = "/Users/zephon/IdeaProjects/mall-demo-app/src/main/";
        //父包名，test为最终生成的包位置，替换成自己的即可
        String parentPackage = "com.zephon.mall.cms";
        //mapper.xml生成位置，test变更为自己的即可
        String mapperXmlPath = "resources/mapper/";
        //作者名字
        String author = "Zephon";

        //数据库连接信息
        String url = "jdbc:mysql://localhost:3306/mall?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String username = "root";
        String password = "123456";

        //表名集合
        List<String> tables = new ArrayList<>();
        tables.add("tb_user");
        tables.add("tb_category");
        tables.add("tb_brand");

        FastAutoGenerator.create(url, username, password)
                //全局配置
                .globalConfig(builder -> {
                    builder
                            .fileOverride()  //开启覆盖之前生成的文件
                            .disableOpenDir()  //禁止打开输出目录
                            .outputDir(path + "java")   //指定输出目录
                            .author(author)   //作者名
//                            .enableSwagger()     //开启 swagger 模式
                            .dateType(DateType.ONLY_DATE)   //时间策略
                            .commentDate("yyyy-MM-dd HH:mm:ss");   //注释日期
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent(parentPackage)     //父包名
                            .entity("entity")               //Entity 包名
                            .service("service")             //Service 包名
                            .serviceImpl("service.impl")    //Service Impl 包名
                            .mapper("mapper")               //Mapper 包名
                            .xml("mapper.xml")              //Mapper XML 包名
                            .controller("controller")       //Controller 包名
//                            .other("config")                //自定义文件包名	输出自定义文件时所用到的包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, path + mapperXmlPath));//指定xml位置
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
//                            .addTablePrefix("app_")//表名前缀，配置后生成的代码不会有此前缀，建议表名的驼峰格式命名，方便寻找对应的文件资料
                            .serviceBuilder()
                            .formatServiceFileName("%sService")//服务层接口名后缀
                            .formatServiceImplFileName("%sServiceImpl")//服务层实现类名后缀
                            .entityBuilder()
                            .enableLombok()//实体类使用lombok,需要自己引入依赖
                            .addTableFills(new Column("gmt_create", FieldFill.INSERT))
                            .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE))
                            //.logicDeleteColumnName("status")//逻辑删除字段，使用delete方法删除数据时会将status设置为1。调用update方法时并不会将该字段放入修改字段中，而是在条件字段中
                            .enableTableFieldAnnotation()//加上字段注解@TableField
                            .controllerBuilder()
                            .formatFileName("%sController")//控制类名称后缀
                            .enableHyphenStyle()
                            .enableRestStyle()
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .execute();
    }
}
