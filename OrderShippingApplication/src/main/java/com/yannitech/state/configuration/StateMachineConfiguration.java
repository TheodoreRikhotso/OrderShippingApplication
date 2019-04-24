package com.yannitech.state.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;

import com.yannitech.state.model.OrderEvents;
import com.yannitech.state.model.OrderStates;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents>{

	
	
}
