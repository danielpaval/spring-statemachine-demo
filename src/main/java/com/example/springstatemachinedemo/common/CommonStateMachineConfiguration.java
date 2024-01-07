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
package com.example.springstatemachinedemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.event.OnStateEntryEvent;
import org.springframework.statemachine.event.OnStateExitEvent;
import org.springframework.statemachine.event.OnTransitionEvent;
import org.springframework.statemachine.event.StateMachineEvent;
import org.springframework.statemachine.transition.TransitionKind;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Slf4j
@Configuration
public class CommonStateMachineConfiguration {

    @Configuration
    static class ApplicationConfig {

        @Bean
        public LoopbackEventListener loopbackEventListener() {
            return new LoopbackEventListener();
        }

        @Bean
        public String stateChartModel() throws IOException {
            ClassPathResource model = new ClassPathResource("statechartmodel.txt");
            InputStream inputStream = model.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String content = scanner.useDelimiter("\\Z").next();
            scanner.close();
            return content;
        }

    }

    static class LoopbackEventListener implements ApplicationListener<StateMachineEvent> {

        @Override
        public void onApplicationEvent(StateMachineEvent event) {
            switch (event) {
                case OnStateEntryEvent e -> log.info("Entry state " + e.getState().getId());
                case OnStateExitEvent e -> log.info("Exit state " + e.getState().getId());
                case OnTransitionEvent e -> {
                    if (e.getTransition().getKind() == TransitionKind.INTERNAL) {
                        log.info("Internal transition source=" + e.getTransition().getSource().getId());
                    }
                }
                default -> {
                }
            }
        }

    }

}
