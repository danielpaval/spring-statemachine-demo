/*
 * Copyright 2015-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.springstatemachinedemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import com.example.springstatemachinedemo.model.Event;
import com.example.springstatemachinedemo.model.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states
                .withStates()
                .initial(State.LOCKED)
                .states(EnumSet.allOf(State.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(State.LOCKED)
                .target(State.UNLOCKED)
                .event(Event.COIN)
                .and()
                .withExternal()
                .source(State.UNLOCKED)
                .target(State.LOCKED)
                .event(Event.PUSH);
    }

}
