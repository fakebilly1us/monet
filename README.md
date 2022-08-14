# Monet
**Monet**

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
