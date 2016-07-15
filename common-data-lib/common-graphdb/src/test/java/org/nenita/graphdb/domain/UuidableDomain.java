package org.nenita.graphdb.domain;

import org.nenita.graphdb.UUIDable;

public class UuidableDomain implements UUIDable {

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
