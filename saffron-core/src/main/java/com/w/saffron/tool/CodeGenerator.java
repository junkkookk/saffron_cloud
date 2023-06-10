package com.w.saffron.tool;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.w.saffron.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author w
 * @since 2023/4/11
 */
public class CodeGenerator {

    private static final String author = "wan";                         //作者
    private static final String packageName = "com.w.saffron.video";            //项目主路径包名(com.boot01.test01)
    private static final String dbTables = "vid_category,vid_history,vid_like_rel,vid_tag,vid_tag_rel,vid_video";                          //需要生成的表名
    private static final Boolean enableSwagger = false;

    public static void main(String[] args) {
        // 1.数据源配置
        DataSourceConfig.Builder dataSourceConfigBuilder =
                new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/saffron?useUnicode=true&useSSL=false&characterEncoding=utf8"
                        , "root", "123456");
        // 2.全局配置
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();
        // 代码生成目录
        globalConfigBuilder.outputDir("D:\\projects\\saffron\\src\\main\\java");
        // 作者
        globalConfigBuilder.author(author);
        // 结束时是否打开文件夹
        globalConfigBuilder.disableOpenDir();
        globalConfigBuilder.dateType(DateType.ONLY_DATE);
        // 实体属性Swagger2注解
        if (enableSwagger){
            globalConfigBuilder.enableSwagger();
        }
        // 3.包配置
        PackageConfig.Builder packageConfigBuilder = new PackageConfig.Builder();
        packageConfigBuilder.parent(packageName);
        packageConfigBuilder.controller("controller");
        packageConfigBuilder.service("service");
        packageConfigBuilder.entity("domain");
        packageConfigBuilder.mapper("dao");
        // 4.策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder();
        // 设置需要映射的表名  用逗号分割
        strategyConfigBuilder.addInclude(dbTables.split(","));
        strategyConfigBuilder.addTablePrefix("vid");
        // 下划线转驼峰
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().enableLombok();
        strategyConfigBuilder.entityBuilder().enableChainModel();
        strategyConfigBuilder.entityBuilder().superClass(BaseEntity.class);
        strategyConfigBuilder.entityBuilder().enableFileOverride();
        // 使用RestController
        strategyConfigBuilder.controllerBuilder().enableRestStyle();
        // 将请求地址转换为驼峰命名，如 http://localhost:8080/hello_id_2
        strategyConfigBuilder.controllerBuilder().enableHyphenStyle();
        strategyConfigBuilder.serviceBuilder().formatServiceFileName("%sService");
        strategyConfigBuilder.mapperBuilder().formatMapperFileName("%sDao");
        strategyConfigBuilder.mapperBuilder().superClass(JpaRepository.class);
        TemplateConfig.Builder templateConfigBuilder = new TemplateConfig.Builder();
        templateConfigBuilder.disable(TemplateType.XML);
        templateConfigBuilder.disable(TemplateType.SERVICE_IMPL);
        templateConfigBuilder.controller("/templates/vm/controller.java.vm");
        templateConfigBuilder.service("/templates/vm/service.java.vm");
        templateConfigBuilder.mapper("/templates/vm/dao.java.vm");
        templateConfigBuilder.entity("/templates/vm/domain.java.vm");
        // 创建代码生成器对象，加载配置   对应1.2.3.4步
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfigBuilder.build());
        autoGenerator.global(globalConfigBuilder.build());
        autoGenerator.packageInfo(packageConfigBuilder.build());
        autoGenerator.strategy(strategyConfigBuilder.build());
        autoGenerator.template(templateConfigBuilder.build());
        // 执行
        autoGenerator.execute();
    }

}
