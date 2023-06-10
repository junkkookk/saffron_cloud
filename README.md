## 项目简介

saffron 是一套基于Java 技术栈 Spring Boot + React 开发的前后端分离**学习型**项目
## 开发环境
- MySQL
- Redis
- RabbitMQ
- JDK 11
- Maven
- IntelliJ IDEA
- Node
## maven命令
~~~
mvn clean package -Dmaven.test.skip=true
~~~
## Docker命令

~~~
docker build -t saffron:v1 .
docker run -p 8871:8080 -p 465:465 --name saffron --restart always --network saffron-net saffron:v1 
~~~