package org.nenita.test.domain;

import org.nenita.domain.UUIDable;

public class SampleDomain implements UUIDable {

	private String uuid;
	
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getUuid() {
		return uuid;
	}
}
