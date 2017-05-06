package com.aast;

import com.aast.exceptions.IdException;

/**
 * Created by UltimateZero on 5/6/2017.
 */
public class Server {
	private int id;

	public Server(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws IdException {
		if(id < 0) throw new IdException("Id must be > 0");
		this.id = id;
	}
}
