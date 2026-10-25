FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17
# 设置环境变量TZ
ENV TZ=Asia/Shanghai
# 配置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN alias ll='ls -l'
ADD target/zkmall-app-enterprise /home/zkmall-app
ADD cert/ /home/zkmall/cert
COPY font/puhui/ /usr/share/fonts/ttf-dejavu
WORKDIR /home/zkmall-app
ENTRYPOINT ["java","-jar","zkmall-app-enterprise.jar"]
