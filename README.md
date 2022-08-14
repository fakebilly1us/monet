# Monet
**Monet**

## 项目结构
![image](images/monet.png)

## draw-archetypes
- **项目原型**
- ```draw-service```: 创建纯后端 ```Dubbo``` 应用
  ```
  mvn archetype:generate  \
    -DgroupId=com.fakebilly.monet \
    -DartifactId=basic-service \
    -Dversion=1.0.0 \
    -Dpackage=com.fakebilly.monet.business \
    -DarchetypeArtifactId=draw-service \
    -DarchetypeGroupId=com.fakebilly.monet \
    -DarchetypeVersion=1.0.0
  ```
- ```draw-web```: 创建 ```WEB``` 应用
  ```
  mvn archetype:generate  \
    -DgroupId=com.fakebilly.monet \
    -DartifactId=basic-service \
    -Dversion=1.0.0 \
    -Dpackage=com.fakebilly.monet.business \
    -DarchetypeArtifactId=draw-web \
    -DarchetypeGroupId=com.fakebilly.monet \
    -DarchetypeVersion=1.0.0
  ```

## monet-core
- **核心类**
- 定义顶层对象/属性

## monet-idworder
- **ID 发号器**
- 生成雪花 ID

## monet-mq
- **RocketMQ 客户端**
    - ```rocketmq-client version``` 4.9.4
- 定制开发 ```RocketMQ Client```

## monet-redis
- **Redis 客户端 Redisson**
  - ```redisson version``` 3.15.6
- 定制开发 ```Redisson```

## monet-es
- **Elasticsearch 客户端**
  - ```elasticsearch version``` 7.16.3
- 定制开发 ```Elasticsearch Java Client```

## monet-prometheus
- **Prometheus Metric 采集**
- 定制开发 ```Prometheus``` 指标
  - ```micrometer version``` 1.5.9
- 自定义指标采集
- 扩展 ```Tomcat``` 指标
- 新增 ```Undertow``` 指标
- 新增 ```Dubbo``` 指标
- 扩展 ```HTTP``` 指标
- 适配 ```Prometheus.yaml``` 自动发现: ```文件```

## monet-log
- **log 链路/日志扩展**
- 基于 ```SkyWalking``` 插桩 自定义插件 ```monet-log-plugin```
  - ```skywalking version``` 8.9.0
- 适配 ```Logback``` 链路
- 适配 ```Log4j2``` 链路
- 适配 ```ThreadPoolExecutor.execute``` Lambda 表达式
- 适配 ```ThreadPoolExecutor.submit``` Lambda 表达式
- ```skywalking-agent``` agent 编译包
> https://github.com/fakebilly-dev/skywalking

## basic-service
- **示例服务: 基础服务**
- 服务 组件 集成示例 详见 ```dev``` 分支
  - ``` git checkout dev ```

## business-service
- **示例服务: 业务服务**
- 服务 组件 集成示例 详见 ```dev``` 分支
  - ``` git checkout dev ```