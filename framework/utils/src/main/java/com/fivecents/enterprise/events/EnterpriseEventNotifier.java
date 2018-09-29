package com.fivecents.enterprise.events;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * This notifier registers enterprise event listeners, in order to push
 * them the enterprise events.
 * 
 * @author Laurent CAILLETEAU
 */
@Named
@ApplicationScoped
public class EnterpriseEventNotifier {
	private Set<Consumer<EnterpriseEvent>> eventConsumers;
	
	@PostConstruct
	private void init() {
		eventConsumers = new HashSet<>();
	}
	
	/**
	 * Register new consumers.
	 * @param consumer
	 */
	public void register(Consumer<EnterpriseEvent> consumer) {
		eventConsumers.add(consumer);
	}
	
	/**
	 * Notify event.
	 * @return
	 */
	public void notifyEvent(EnterpriseEvent event) {
		for (Consumer<EnterpriseEvent> eventConsumer : eventConsumers) {
			eventConsumer.accept(event);
		}
	}
}
