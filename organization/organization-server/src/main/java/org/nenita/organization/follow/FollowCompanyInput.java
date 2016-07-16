package org.nenita.organization.follow;

import javax.validation.constraints.NotNull;

public class FollowCompanyInput {

	/* Once you have a login mechanism, the person UUID can be retrieved 
	 * from the session and passed on here
	 */
	@NotNull
	private String personUuid;

	public String getPersonUuid() {
		return personUuid;
	}

	public void setPersonUuid(String personUuid) {
		this.personUuid = personUuid;
	}
}
