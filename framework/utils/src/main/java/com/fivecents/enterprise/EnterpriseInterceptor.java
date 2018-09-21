package com.fivecents.enterprise;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.fivecents.enterprise.events.EnterpriseEvent;
import com.fivecents.enterprise.events.EnterpriseEventNotifier;
import com.fivecents.enterprise.events.EnterpriseEvent.EventType;

/**
 * This interceptor class computes time taken by an enterprise service,
 * and send the result as an event on the enterprise bus.
 * 
 * @author Laurent CAILLETEAU
 */
public class EnterpriseInterceptor {
	public static boolean ACTIVATE_PAUSE = true;
	public static String ENV_DELAY_FROM_MS = "ENV_DELAY_FROM_MS";
	public static String ENV_DELAY_TO_MS = "ENV_DELAY_TO_MS";
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static synchronized String getFormattedDate() {
		return SDF.format(new Date());
	}
	
	@Inject
	private EnterpriseEventNotifier enterpriseEventNotifier;
	
	@AroundInvoke
	private Object enterpriseIntercept(InvocationContext context) throws Exception {
		Instant start = Instant.now();
		
		// We can make some guesses about the method.
		EventType eventType = EventType.INFO;
		String methodName = context.getMethod().getName();
		if (methodName.contains("update")||methodName.contains("create")) {
			eventType = EventType.WARNING;
		} else if (methodName.contains("remove")||methodName.contains("delete")) {
			eventType = EventType.ALERT;
		}
		
		/*
		String parameters = "";
		if (context.getParameters() != null) {
			String[] parametersArray = Arrays.copyOf(
					context.getParameters(), 
					context.getParameters().length, 
					String[].class);
			parameters = String.join(", ", parametersArray);
		}
		*/
		
		// Ok let's pause a little while.
		if (ACTIVATE_PAUSE) {
			pause();
		}
		
		// Let's call the enterprise method.
		Object returnedObject = context.proceed();

		// Compute the time taken by the call.
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
        
		// Send event.
		StringBuilder eventMessage = new StringBuilder();
		eventMessage.append(getFormattedDate()).append(" - ");
		eventMessage.append("Enterprise method triggered : '").append(methodName);
		// eventMessage.append("', with parameters : '").apend(parameters); 
		eventMessage.append("' took ").append(timeElapsed.toMillis()).append(" ms");
		enterpriseEventNotifier.notifyEvent(
				new EnterpriseEvent(eventType, eventMessage.toString()));
         
		// We return the result of the method.
		return returnedObject;
	}
	
	/**
	 * Method to simulate a pause.
	 */
	private void pause() {
		// Here, we can simulate some slowness of the called enterprise service.
		// This code is really not optimized, not reactive, making a current thread sleep,
		// but it will do the job for our needs.
		String envDelayFromString = System.getenv(ENV_DELAY_FROM_MS);
		int envDelayFrom = 0;
		if (envDelayFromString != null) {
			envDelayFrom = Integer.valueOf(envDelayFromString);
		}
		
		String envDelayToString = System.getenv(ENV_DELAY_TO_MS);
		int envDelayTo = 0;
		if (envDelayToString != null) {
			envDelayTo = Integer.valueOf(envDelayToString);
		}
		
		Random r = new Random();
		int delay = r.nextInt((envDelayTo - envDelayFrom) + 1) + envDelayFrom;
	
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
}
