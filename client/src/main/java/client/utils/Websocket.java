package client.utils;

import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import commons.WebsocketActions;
import org.springframework.lang.NonNull;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class Websocket {
    private StompSession stompSession;
    private final StompSessionHandler sessionHandler;
    private final WebSocketStompClient stompClient;
    private final String url;
    private final EnumMap<WebsocketActions, Set<Consumer<Object>>> functions;

    /**
     * Websocket client constructor
     *
     * @param config config for url of the websocket address
     */
    @Inject
    public Websocket(UserConfig config) {
        // Initialize the enum map with all enum values
        functions = new EnumMap<>(WebsocketActions.class);
        resetAllActions();

        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        List<MessageConverter> converterList = List.of(new MappingJackson2MessageConverter(),
                new StringMessageConverter());
        stompClient.setMessageConverter(new CompositeMessageConverter(converterList));

        this.url = "ws:" + config.getUrl() + "ws";
        sessionHandler = new MyStompSessionHandler();
    }

    /**
     * Subscribe to updates of a particular event
     *
     * @param eventID event id
     */
    public void connect(String eventID) {
        try {
            stompSession = stompClient.connectAsync(url, sessionHandler).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Could not connect to server", e);
        }
        // Subscribe to specific event channel
        stompSession.subscribe("/event/" + eventID, sessionHandler);
    }

    /**
     * Disconnect the websocket from the server
     */
    public void disconnect() {
        stompSession.disconnect();
    }

    /**
     * Sets the function for provided name
     * <pre>
     * available functions:
     * titleChange(String)
     * deleteEvent()
     * addParticipant(Participant)
     * updateParticipant(Participant)
     * removeParticipant(id)
     * addExpense(Expense)
     * updateExpense(Expense)
     * removeExpense(id)
     * </pre>
     * @param action enum name of the function
     * @param consumer function that consumes type of payload and payload in that order
     */
    public void on(WebsocketActions action, Consumer<Object> consumer) {
        functions.get(action).add(consumer);
    }

    /**
     * Removes all listeners set for a particular action
     *
     * @param action websocket action to reset all listeners for
     */
    public void resetAction(WebsocketActions action) {
        functions.put(action, new HashSet<>());
    }

    /**
     * Resets all action listeners
     */
    public void resetAllActions() {
        EnumSet.allOf(WebsocketActions.class).forEach(this::resetAction);
    }

    private class MyStompSessionHandler extends StompSessionHandlerAdapter {

        private static final Map<String, Type> typeMap = new HashMap<>(Map.of(
                "commons.Event", Event.class,
                "commons.Participant", Participant.class,
                "commons.Expense", Expense.class,
                "java.lang.String", String.class,
                "java.lang.Long", Long.class));

        /**
         * Executes after successfully connecting to the server
         *
         * @param session stomp session
         * @param connectedHeaders headers of the message
         */
        @Override
        public void afterConnected(@NonNull StompSession session,
                                   @NonNull StompHeaders connectedHeaders) {
            System.out.println("WS connected");
        }

        @Override
        @NonNull
        public Type getPayloadType(StompHeaders headers) {
            return typeMap.get(headers.get("type").getFirst());
        }

        /**
         * Executes when client receives a message from the server
         *
         * @param headers headers
         * @param payload message body
         */
        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            try {
                WebsocketActions action = WebsocketActions
                        .valueOf(headers.get("action").getFirst());
                functions.get(action).forEach(consumer -> consumer.accept(payload));
            } catch (IllegalArgumentException e) {
                System.out.println("Server sent an unknown action");
            }
        }

        @Override
        public void handleException(@NonNull StompSession session, StompCommand command,
                                    @NonNull StompHeaders headers, @NonNull byte[] payload,
                                    @NonNull Throwable exception) {
            super.handleException(session, command, headers, payload, exception);
        }
    }
}

