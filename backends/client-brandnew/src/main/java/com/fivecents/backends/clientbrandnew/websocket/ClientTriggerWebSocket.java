package com.fivecents.backends.clientbrandnew.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fivecents.backends.clientbrandnew.enterprise.events.EnterpriseEvent;
import com.fivecents.backends.clientbrandnew.enterprise.events.EnterpriseEventNotifier;

/**
 * This simple WebSocket send all calls to the Client service to registered 
 * listeners.
 */
@ServerEndpoint(value = "/trigger", encoders = {JsonEncoder.class})
public class ClientTriggerWebSocket {
	
	@Inject
	private EnterpriseEventNotifier enterpriseEventsNotifier;
	
	// Set of client sessions on our endpoint.
	private static Set<Session> allClientSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	/**
	 * This init method will be executed after CDI initialization.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
		// We can register a Consumer in case of enterprise events.
		enterpriseEventsNotifier.register(event -> {
			for (Session clientSession : allClientSessions) {
				try {
					// We can send the message to remote clients.
					// clientSession.getBasicRemote().sendText(event.getEventMessage());
					JsonObject eventAsJson = Json.createObjectBuilder()
							.add("type", event.getEventType().toString())
							.add("message", event.getEventMessage())
							.build();
					clientSession.getBasicRemote().sendObject(eventAsJson);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EncodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});
    }
 
	@OnOpen
	public void onOpen(final Session clientSession) {
		allClientSessions.add(clientSession);
		
		try {
			// clientSession.getBasicRemote().sendText("Server side - the channel is opened");
			JsonObject eventAsJson = Json.createObjectBuilder()
					.add("type", EnterpriseEvent.EventType.INFO.toString())
					.add("message", "Server side - the channel is opened")
					.build();
			clientSession.getBasicRemote().sendObject(eventAsJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(CloseReason closeReason, Session clientSession) {
		allClientSessions.remove(clientSession);
	}
	
	@OnMessage
	public void onMessage(String message, Session client) throws IOException {
		// The client does not send messages to the server, or to other clients for now.
	}
	
	@OnError
	public void onError(Throwable throwable, Session client) {
		System.err.println("ERROR from inside : " + throwable.getMessage());
	}
}
