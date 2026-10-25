FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17
# 设置环境变量TZ
ENV TZ=Asia/Shanghai
# 配置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN alias ll='ls -l'
ADD target/zkmall-admin-enterprise /home/zkmall-admin
ADD cert/ /home/zkmall/cert
WORKDIR /home/zkmall-admin
ENTRYPOINT ["java","-jar","zkmall-admin-enterprise.jar"]
