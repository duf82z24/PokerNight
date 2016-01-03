package com.pokernight.di.injector;

import com.google.inject.AbstractModule;
import com.pokernight.service.PlayerCommunicationService;
import com.pokernight.service.WebSocketPlayerCommunicationService;

public class GameEngineConfig extends AbstractModule {

    @Override
    protected void configure() {
        //bind(PlayerCommunicationService.class).to(WebSocketPlayerCommunicationService.class);
    }
}
