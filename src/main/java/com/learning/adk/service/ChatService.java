package com.learning.adk.service;
import com.google.adk.agents.LlmAgent;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import org.springframework.stereotype.Service;
import com.google.adk.events.Event;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;

@Service
public class ChatService {

    private final InMemoryRunner runner;
    private final Session session;

    public ChatService(LlmAgent mainAgent) {
        this.runner = new InMemoryRunner(mainAgent);
        // Creating a static session for the POC
        this.session = runner.sessionService().createSession(runner.appName(), "user-1").blockingGet();
    }

    public String processMessage(String message) {
        try {
            // FIX 2: The ADK requires text to be wrapped in a Content/Part object
            Content userMsg = Content.fromParts(Part.fromText(message));

            // FIX 3: Call runAsync() passing the User ID, Session ID, and Content
            Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userMsg);

            StringBuilder responseBuilder = new StringBuilder();

            // FIX 4: Block the reactive stream and collect the agent's response events
            events.blockingForEach(event -> {
                // If it's a final response event, grab the text
                if (event.finalResponse()) {
                    responseBuilder.append(event.stringifyContent());
                }
            });

            return responseBuilder.toString();

        } catch (Exception e) {
            return "Agent Error: " + e.getMessage();
        }
    }
}