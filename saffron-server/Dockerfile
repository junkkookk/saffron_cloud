FROM tomcat:10.1.7-jre17
MAINTAINER w
ARG WAR_FILE
ADD /target/${WAR_FILE} /usr/local/tomcat/webapps/
ENV TZ Asia/Shanghai
RUN ln -fs /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo ${TZ} > /etc/timezone
ENTRYPOINT /usr/local/tomcat/bin/startup.sh && tail -f /usr/local/tomcat/logs/catalina.out
EXPOSE 465
CMD exec bin/bash -c "top -b"