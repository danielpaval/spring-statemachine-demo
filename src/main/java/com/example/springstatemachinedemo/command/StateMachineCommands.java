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
package com.example.springstatemachinedemo.command;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import reactor.core.publisher.Mono;
import com.example.springstatemachinedemo.common.AbstractStateMachineCommands;
import com.example.springstatemachinedemo.model.Event;
import com.example.springstatemachinedemo.model.State;

@ShellComponent
public class StateMachineCommands extends AbstractStateMachineCommands<State, Event> {

	@ShellMethod(key = "sm event", value = "Sends an event to a state machine")
	public String event(@ShellOption(value = { "", "event" }, help = "The event") final Event event) {
		getStateMachine()
			.sendEvent(Mono.just(MessageBuilder
				.withPayload(event).build()))
			.subscribe();
		return "Event " + event + " send";
	}

}
