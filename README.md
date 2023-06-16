## Mybatis plus CodeGenerator代码生成器
> 本教程参考官网重新定义编写，能够快速搭建项目脚手架
### 代码生成说明
当有一个新的业务实现时，对接口的功能实现上，我们通常来说需要构建下面的信息：

- PO类

  数据库表和实体类的映射 Java Bean。

- DAO层

  需要编写接口 Mapper ，接口 Mapper 需要去继承 MP 中的 BaseMapper 接口。

- Service层

  编写 Service 层接口和实现类。业务接口需要去继承 MP 中的 IService，业务实现类需要继承 MP 中的 ServiceImpl 和 实现业务接口。

- Controller层

  编写 Controller 并标注 Spring MVC 中的相关注解。

​       从上面的各类代码中可以放下，代码都是模板性的，如果用手工copy、修改的方式来实现，太烦人也没效率，而这时就是代码生成器小展身手的时候，**使用代码生成器生成模板性的代码，减少手工操作的繁琐，集中精力在业务开发上，提升开发效率。**

​	AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Mapper接口、Entity实体类、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。

### 具体使用步骤
1. 打开 CodeGenerator 类
2. 修改对应的参数即可
   相关参数介绍：
   ```yaml
    DB_TYPE: 数据库类型， 默认是 MySQL
    DB_NAME: 数据库名，用户修改为自己的数据库
    HOST_NAME: 数据库IP， 默认 localhost
    JDBC_USERNAME: 数据库用户名， 默认：root
    JDBC_PASSWORD: 数据库密码，默认：root
    TABLES: 需要生成代码的表， 数组
    PACKAGE_PARENT_NAME: 代码生成的包结构
    IS_DTO: 是否生成DTO， 默认：false
    AUTHOR: 作者名称， 默认：itheima
   
3. 运行main方法执行生成代码
4. 拷贝到自己的项目中即可

优化：
1. 新增：可以不用用户输入数据库表名，自动扫描数据库下的所有的表，完成代码生成
   添加方法：getDbAllTables(), 如果用户想单独生成某一个表，在参数TABLES 数组定义并且注释该方法即可

以后在项目中使用，在这里生成后，可以把代码拷贝到对应的目录里使用，在整个黑马头条项目开发阶段，使用了当前生成的mapper和实体类。
