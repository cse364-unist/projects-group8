# Use Ubuntu 22.04 as base image
FROM ubuntu:22.04

# Install prerequisites
RUN apt-get update \
    && apt-get install -y wget gnupg2 \
    && rm -rf /var/lib/apt/lists/*

# Update package list and install MongoDB
RUN apt-get update \
    #
    && apt-get install -y sudo \
    && apt-get install -y mysql-server \
    && wget https://downloads.apache.org/tomcat/tomcat-9/v9.0.53/bin/apache-tomcat-9.0.53.tar.gz \
    && tar -xzf apache-tomcat-9.0.53.tar.gz \
    && mkdir tomcat \
    && mv apache-tomcat-9.0.53/* tomcat/ \
    && apt-get clean \
    #
    && rm -rf /var/lib/apt/lists/*

# Create necessary directories
RUN mkdir -p /data/db /data/configdb
RUN sudo apt-get install mysql-server


# Add your stuff below:
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y \
    vim \
    openjdk-17-jdk \
    maven\
    curl\
    git

WORKDIR /root/project
COPY movinProject-0.0.1-SNAPSHOT-with-dependencies.war tomcat/webapps/
# ADD milestone2 /root/project/milestone2
# ADD run.sh /root/project/run.sh

CMD ["sh", "run.sh"]