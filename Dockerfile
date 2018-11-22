FROM openjdk:8u121-jre-alpine

MAINTAINER Charalampos Chomenidis <hampos@me.com>

RUN apk --update add curl tar
RUN apk --update add ttf-dejavu

RUN mkdir -p /opt/jboss

WORKDIR /opt/jboss

ENV WILDFLY_VERSION 8.2.1.Final
ENV WILDFLY_SHA1 77161d682005f26acb9d2df5548c8623ba3a4905
ENV JBOSS_HOME /opt/jboss/wildfly

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz

RUN ln -s wildfly-$WILDFLY_VERSION wildfly

EXPOSE 8080 9990

ENV JBOSS_HOME /opt/jboss/wildfly

ADD ./standalone/standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
ADD ./target/imageAnalysis.war /opt/jboss/wildfly/standalone/deployments/nanoImage.war
#ADD ./packages/imageAnalysis/target/imageAnalysis.war /opt/jboss/wildfly/standalone/deployments/imageAnalysis.war

#RUN /opt/jboss/wildfly/bin/add-user.sh admin pass --silent

#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
