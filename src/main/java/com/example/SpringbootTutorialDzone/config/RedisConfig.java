package com.example.SpringbootTutorialDzone.config;

import com.example.SpringbootTutorialDzone.queue.MessagePublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.example.SpringbootTutorialDzone.queue.MessagePublisher;
import com.example.SpringbootTutorialDzone.queue.MessagePublisherImpl;
import com.example.SpringbootTutorialDzone.queue.MessageSubscriber;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@ComponentScan("com.example.SpringbootTutorialDzone")
public class RedisConfig {
    
    @Bean
    JedisConnectionFactory JedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer redisContainer(){
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(JedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;


    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        final RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(JedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }

    @Bean
    MessageListenerAdapter messageListener(){
        return new MessageListenerAdapter(new MessageSubscriber());
    }

    
}