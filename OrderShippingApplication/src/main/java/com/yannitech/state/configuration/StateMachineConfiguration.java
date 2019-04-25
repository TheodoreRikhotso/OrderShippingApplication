package com.yannitech.state.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import com.yannitech.state.model.OrderEvents;
import com.yannitech.state.model.OrderStates;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents>{

	@Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states
            .withStates()
                .initial(OrderStates.WAIT_NEW_ORDER)
                .state(OrderStates.RECEIVE_ORDER)
                .choice(OrderStates.CHOICE)
                .fork(OrderStates.FORK)
                .state(OrderStates.HANDLE_ORDER)
                .join(OrderStates.JOIN)
                .state(OrderStates.SHIP_ORDER)
                .and()
            .withStates()
            	.parent(OrderStates.HANDLE_ORDER)
            	.initial(OrderStates.CHECK_STOCK)
            	.junction(OrderStates.JUNCTION)
            	.state(OrderStates.MAKE_PRODUCTION_PLAN)
            	.state(OrderStates.PRODUCE)
            	.state(OrderStates.FILL_ORDER)
            	.state(OrderStates.WAIT_PRODUCT)
                .and()
             .withStates()
                .parent(OrderStates.HANDLE_ORDER)
                .initial(OrderStates.SEND_BILL)
                .state(OrderStates.SEND_REMINDER)
                .state(OrderStates.WAIT_PAYMENT)
                .state(OrderStates.HANDLE_PAYMENT)
                .state(OrderStates.NOTIFY_CUSTOMER)
                .state(OrderStates.WAIT_ORDER)
                .and()
              .withStates()
                .end(OrderStates.SHIP_ORDER);
             
    }
	
}
