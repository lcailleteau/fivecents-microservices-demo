package com.fivecents.enterprise.events;

/**
 * Enterprise event.
 * 
 * @author Laurent CAILLETEAU
 */
public class EnterpriseEvent {
	// Event types.
	public enum EventType {
		ALERT ("alert"),
		INFO ("info"),
		WARNING ("warning");
		   
		private String eventType = "";
		EventType(String eventType) {
			this.eventType = eventType;
		}
		public String toString(){
			return eventType;
		}
	}
	
	private String eventMessage;
	private EventType eventType;

	public EnterpriseEvent(EventType eventType, String eventMessage) {
		this.eventType = eventType;
		this.eventMessage = eventMessage;
	}
	
	
	public String getEventMessage() {
		return eventMessage;
	}
	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
