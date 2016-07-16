package org.nenita.user;

import javax.validation.constraints.NotNull;

public class FollowCompanyInput {

	@NotNull
	private String companyUuid;

	public String getCompanyUuid() {
		return companyUuid;
	}

	public void setCompanyUuid(String companyUuid) {
		this.companyUuid = companyUuid;
	}
}
