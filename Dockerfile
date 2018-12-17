FROM jboss/wildfly

USER root

EXPOSE 8080
EXPOSE 9990

#ADD ./standalone/standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
ADD ./target/nanoImage.war /opt/jboss/wildfly/standalone/deployments/nanoImage.war
#ADD ./packages/imageAnalysis/target/imageAnalysis.war /opt/jboss/wildfly/standalone/deployments/imageAnalysis.war

#RUN /opt/jboss/wildfly/bin/add-user.sh admin pass --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
