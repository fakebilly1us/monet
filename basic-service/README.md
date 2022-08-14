# Monet

## basic-service
- **示例服务: 基础服务**
- 启动时添加命令
  - 
  ``` 
  -ea -Dmonet.nacos.config.server.addr=[YOUR_NACOS_SERVER_ADDR]  -Dmonet.nacos.config.namespace=[YOUR_NACOS_NAMESPACE] -javaagent:[YOUR_SKYWALKING_JAVA_AGENT_ABSOLUTE_PATH] -Dskywalking.agent.service_name=[YOUR_APPLICATION_NAME] -Dskywalking.collector.backend_service=[YOUR_SKYWALKING_BACKEND_HOST_PORT] -Dskywalking.plugin.jdkthreading.threading_class_prefixes=[YOUR_APPLICATION_ROOT_PACKAGE]
  # eg
  -ea -Dmonet.nacos.config.server.addr=http://127.0.0.1:8848  -Dmonet.nacos.config.namespace=4edf5456-t6h2-352v-dfre-5ga31rt431e4 -javaagent:/usr/local/skywalking/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=basic-service -Dskywalking.collector.backend_service=127.0.0.1:11800 -Dskywalking.plugin.jdkthreading.threading_class_prefixes=com.fakebilly
  ```
