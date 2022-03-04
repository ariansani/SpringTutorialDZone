package com.example.SpringbootTutorialDzone.queue;

public interface MessagePublisher {
    void publish(final String message);
}
