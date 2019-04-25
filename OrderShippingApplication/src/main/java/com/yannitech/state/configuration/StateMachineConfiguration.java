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
                .choice(OrderStates.RECEIVE_ORDER)
                .and()
            .withStates()
                .parent(OrderStates.RECEIVE_ORDER)
            	.initial(OrderStates.CHECK_STOCK)
            	.fork(OrderStates.RECEIVE_ORDER)
            	.junction(OrderStates.CHECK_STOCK)
            	.state(OrderStates.MAKE_PRODUCTION_PLAN)
            	.state(OrderStates.PRODUCE)
            	.state(OrderStates.FILL_ORDER)
            	.state(OrderStates.WAIT_ORDER)
                .and()
             .withStates()
                .parent(OrderStates.RECEIVE_ORDER)
                .initial(OrderStates.SEND_BILL)
                .fork(OrderStates.RECEIVE_ORDER)
                .state(OrderStates.SEND_REMINDER)
                .state(OrderStates.WAIT_PAYMENT)
                .state(OrderStates.HANDLE_PAYMENT)
                .state(OrderStates.NOTIFY_CUSTOMER)
                .state(OrderStates.WAIT_NEW_ORDER)
                .and()
              .withStates()
                .join(OrderStates.WAIT_PRODUCT)
                .end(OrderStates.SHIP_ORDER)
                .and()
                .withStates()
                .join(OrderStates.WAIT_ORDER)
                .end(OrderStates.SHIP_ORDER);
             
    }
	
}
