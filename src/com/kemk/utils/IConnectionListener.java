package com.kemk.utils;

public interface IConnectionListener extends IListener {
	@Override
	default void Callback() {
		 DisConnectionEvent();
	}
	public void DisConnectionEvent();
}
