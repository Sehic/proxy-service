package com.reeinvent.backend.client.synonym;

import com.reeinvent.backend.grpc.AbstractChannel;
import com.reeinvent.backend.grpc.ChannelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@PropertySource("classpath:application.properties")
public class SynonymChannel extends AbstractChannel {

    @Autowired
    private Environment env;

    @Override
    protected ChannelConfig configure() {
        int port;
        try {
            port = Objects.requireNonNull(env.getProperty("synonym.service.port", Integer.class));
        } catch (Exception e) {
            port = 8080;
        }
        System.out.println("SynonymChannel: " + port);

        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.setHost(env.getProperty("synonym.service.host"));
        channelConfig.setPort(port);

        return channelConfig;
    }
}


