# 🤖 Spring Boot Multi-Agent AI (Google ADK Proof of Concept)

This repository is a Proof of Concept (POC) demonstrating a hierarchical multi-agent AI system built with **Java 17**, **Spring Boot**, and the **Google Agent Development Kit (ADK)**. 

It showcases how a primary AI assistant can dynamically route specialized tasks to a sub-agent, which then autonomously executes standard Java methods (Tools) to fetch real-time data from external APIs before formulating a response.



## 🌟 Key Features
* **Multi-Agent Routing:** A "General Assistant" agent that intercepts all prompts and routes domain-specific queries to a "Weather & Time Specialist" sub-agent.
* **Autonomous Tool Execution:** The sub-agent is equipped with a Java Tool that triggers a Spring Cloud OpenFeign client to fetch live data from the internet (`wttr.in`).
* **Swagger UI Integration:** A polished, interactive web interface for stakeholders to test the agents.
* **Transparent "Thought" Logging:** Configured debug logs that expose the AI's internal decision-making process, tool execution, and HTTP requests in the console.

---

## 📋 Prerequisites
Before you begin, ensure you have the following installed on your machine:
* **Java 17** or higher
* **Maven 3.8+** (or use the included Maven wrapper)
* **A Google Gemini API Key:** You can get a free key from [Google AI Studio](https://aistudio.google.com/).

---

## 🚀 Getting Started

### 1. Set Environment Variables
The Google ADK requires your API key to authenticate. To use the free AI Studio tier (bypassing Google Cloud Vertex AI), you **must** set the following environment variables on your system or inside your IDE's Run Configuration before starting the app:

**Mac/Linux:**
```bash
export GOOGLE_API_KEY="your_actual_api_key_here"
export GOOGLE_GENAI_USE_VERTEXAI=FALSE

```

**Windows:**

```bash
set GOOGLE_API_KEY="your_actual_api_key_here"
set GOOGLE_GENAI_USE_VERTEXAI=FALSE
```
