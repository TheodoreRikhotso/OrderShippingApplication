package com.yannitech.state.tansition;

import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import com.yannitech.state.model.OrderEvents;
import com.yannitech.state.model.OrderStates;

public class StateMachineTransition extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {
	
	
	public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
            .withExternal()
                .source(OrderStates.WAIT_NEW_ORDER)
                .target(OrderStates.RECEIVE_ORDER)
                .event(OrderEvents.PLACE_ODRER)
                .and()
                .withChoice()
                .source(OrderStates.CHOICE_HANDLE_ORDER)
                .first(OrderStates.FORK,Guard1())
                .last(OrderStates.CUSTOMER_ERROR)
                .and()
                .withFork()
                .source(OrderStates.FORK)
                .target(OrderStates.HANDLE_ORDER)
                .and()
                .withExternal()
                .source(OrderStates.CHECK_STOCK)
                .target(OrderStates.CHOICE_PRODUCTION)
                .and()
                .withChoice()
                .source(OrderStates.CHOICE_PRODUCTION)
                .first(OrderStates.MAKE_PRODUCTION_PLAN, productionPlanGuard())
                .last(OrderStates.PRODUCE)
                .then(OrderStates.JUNCTION_STOCK, Guard1())
                .and()
                .withExternal()
                .source(OrderStates.MAKE_PRODUCTION_PLAN)
                .target(OrderStates.JUNCTION_STOCK)
                .and()
                .withJunction()
                .source(OrderStates.JUNCTION_STOCK)
                .first(OrderStates.PRODUCE, Guard1())
                .last(OrderStates.JUNCTION_ORDER)
                .then(OrderStates.FILL_ORDER, Guard1())
                .and()
                .withExternal()
                .source(OrderStates.PRODUCE)
                .target(OrderStates.JUNCTION_ORDER)
                .and()
                .withJunction()
                .source(OrderStates.JUNCTION_ORDER)
                .first(OrderStates.FILL_ORDER, Guard1())
                .and()
                .withExternal()
                .source(OrderStates.FILL_ORDER)
                .target(OrderStates.WAIT_PRODUCT)
                .and()
                .withExternal()
                .source(OrderStates.HANDLE_ORDER)
                .target(OrderStates.JOIN)
                .and()
                .withJoin()
                .source(OrderStates.JOIN)
                .target(OrderStates.SHIP_ORDER)
                .and()
                .withFork()
                .source(OrderStates.FORK)
                .target(OrderStates.SEND_BILL)
                .and()
                .withExternal()
                .source(OrderStates.SEND_BILL)
                .target(OrderStates.WAIT_PAYMENT)
                .and()
                .withExternal()
                .source(OrderStates.SEND_REMINDER)
                .target(OrderStates.WAIT_PAYMENT)
                .and()
                .withExternal()
                .source(OrderStates.WAIT_PAYMENT)
                .target(OrderStates.HANDLE_PAYMENT)
                .event(OrderEvents.RECIEVE_PAYMENT)
                .and()
                .withExternal()
                .source(OrderStates.HANDLE_PAYMENT)
                .target(OrderStates.CHOICE_PAYMENT_OK)
                .and()
                .withChoice()
                .source(OrderStates.CHOICE_PAYMENT_OK)
                .first(OrderStates.WAIT_ORDER, Guard1())
                .last(OrderStates.NOTIFY_CUSTOMER)
                .and()
                .withExternal()
                .source(OrderStates.HANDLE_ORDER)
                .target(OrderStates.JOIN)
                .and()
                .withJoin()
                .source(OrderStates.JOIN)
                .target(OrderStates.SHIP_ORDER)
                .and()
                .withExternal()
                .source(OrderStates.SHIP_ORDER)
                .target(OrderStates.ORDER_SHIPPED);
              
  
               
        
    }

	 @Bean
	    public Guard<OrderStates, OrderEvents> Guard1() {
	        return new Guard<OrderStates, OrderEvents>() {

	            @Override
	            public boolean evaluate(StateContext<OrderStates, OrderEvents> context) {
	                return true;
	            }

				
	        };
	 }
	 
	 @Bean
	    public Guard<OrderStates, OrderEvents> productionPlanGuard() {
	        return new Guard<OrderStates, OrderEvents>() {

	            @Override
	            public boolean evaluate(StateContext<OrderStates, OrderEvents> context) {
	                return false;
	            }

				
	        };
	 }
}
