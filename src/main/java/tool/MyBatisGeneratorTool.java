package tool;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行后会自动生成我们需要的代码和xml。只需一个mybatis-generator.xml文件即可
 * 生成bean、mapper、mapper的xml映射文件
 * User、UserExample、Blog、BlogExample、UserMapper、BlogMapper 类和接口以及
 * UserMapper.xml、BlogMapper.xml 都是自动生成的。
 *
 * 这种方案有两个问题：
 * 问题1：XML是以追加的形式保存到文件中；如果要重新生成，先删除之前的XML。
 * 问题2：如果删除了生成的mapper，而业务代码里已经引用他们了。那么在IDEA中再运行这里的代码，会因为其他代码有问题而报错。
 *
 * 推荐使用maven插件，效果相同，见pom文件
 */
public class MyBatisGeneratorTool {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File(MyBatisGeneratorTool.class.getResource("/mybatis-generator.xml").getPath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);

        // 虽然overwrite为true，但只针对java代码。
        // XML是以追加的形式保存到文件中；如果要重新生成，先删除之前的XML
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
