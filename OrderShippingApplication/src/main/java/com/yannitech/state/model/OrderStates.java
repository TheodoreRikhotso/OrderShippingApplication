package com.yannitech.state.model;

	public enum OrderStates{
		
		WAIT_NEW_ORDER,
		RECEIVE_ORDER,
		CHECK_STOCK,
		MAKE_PRODUCTION_PLAN,
		PRODUCE,
		FILL_ORDER,
		WAIT_PRODUCT,
		SEND_BILL,
		WAIT_PAYMENT,
		SEND_REMINDER,
		HANDLE_PAYMENT,
		NOTIFY_CUSTOMER,
		WAIT_ORDER,
		SHIP_ORDER;
		
	}

