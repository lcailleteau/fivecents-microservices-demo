package com.fivecents.backends.clientbrandnew.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.swagger.SwaggerArchive;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
*/

/**
 * Main thorntail Rest application.
 * To add a debug hook :
 * 
 * java -jar target/client-brand-new-thorntail.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=1044
 * 
 * 
 * java -jar target/client-brand-new-thorntail.jar -Xdebug -Xrunjdwp:transport=dt_socket,address=8001,server=y,suspend=n
 * 
 * Ok, working with :
 * mvn thorntail:run -Dswarm.debug.port=8001
 * 
 * @author Laurent CAILLETEAU
 *
 */
@ApplicationPath("api/v1")
public class RestApplication extends Application {

	/**
	 * This main method is not mandatory, we can use the inherited one, but we want to
	 * override it in order to give some configuration to Swagger.
	 * 
	 * Cf. https://wildfly-swarm.gitbooks.io/wildfly-swarm-users-guide/advanced/swagger.html
	 * 
	 * @param args
	 * @throws Exception
	 */
	
	/*
	public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "swagger-app.war");
       
        // deployment.addClass(TimeResource.class);
        deployment.addClass(ClientEndpoint.class);

        // Enable the swagger bits
        SwaggerArchive archive = deployment.as(SwaggerArchive.class);
        // Tell swagger where our resources are
        archive.setResourcePackages("com.fivecents");
        archive.setTitle("My Awesome Application on slow clients");

        deployment.addAllDependencies();
        swarm
                .fraction(LoggingFraction.createDefaultLoggingFraction())
                .start()
                .deploy(deployment);
    }
    */
}
