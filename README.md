##这个项目是spring boot微服务项目，主要功能点展示
1，集成了mybatis 
2，generator，一键生成mapper文件
3，定时任务
4，接口服务发布
5，restTemplate接口调用集成http/https 默认是http,访问https时注意证书类型。暂不考虑Netflix Ribbon相关
6，filter统一鉴权
7，一些常用工具，缓存（单例模式做全局缓存），emoji表情过滤
8,多数据源集成
9，分页插件
10，自定义@CostTime注解，监控方法耗时，方便定位问题
11，lombook,commons-lang3，fastJson等工具包集成
12,自定义日志管理策略，配置目前放在config文件夹下面，移动到resouces下面自动生效
13，自定义run.sh脚本,根据liunx环境，需要修改jar路径和jdk路径
14,json数据透出管理
15,统一的消息处理方法。
##运行所需要的系统环境配置
JDK1.8
MySQL5.5+
Maven3.0+
项目下载到本地可以直接mvn clean install


