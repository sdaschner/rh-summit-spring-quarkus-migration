FROM ibm-semeru-runtimes:open-17-jre

COPY target/quarkus-app/lib/ /deployments/lib/
COPY target/quarkus-app/*.jar /deployments/
COPY target/quarkus-app/quarkus/ /deployments/quarkus/
COPY target/quarkus-app/app/ /deployments/app/

CMD ["java", "-jar", "-Dquarkus.http.host=0.0.0.0", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "/deployments/quarkus-run.jar"]
