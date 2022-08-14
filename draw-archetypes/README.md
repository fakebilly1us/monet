# Monet

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
