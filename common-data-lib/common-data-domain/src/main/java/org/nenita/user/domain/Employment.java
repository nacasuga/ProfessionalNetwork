package org.nenita.user.domain;

import org.nenita.organization.domain.Company;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Represents the relationship between a user and a company, that is, a user's
 * employment history including the current employer.
 * 
 * Use this modeling technique if you have a rich association, that is, a
 * relationship that has attributes
 * 
 * @author nenita
 *
 */
@RelationshipEntity(type = "EMPLOYED_AT")
public class Employment {

	@GraphId
	private Long nodeId;

	@StartNode
	private User user;

	@EndNode
	private Company employer;

	private boolean featuredEmployee;
	private boolean currentEmployer;
	private String jobTitle;
	private EmploymentDate employedFrom;
	private EmploymentDate employedTo;

	public Employment() {

	}

	// Current employer
	public Employment(User user, Company employer, String jobTitle, EmploymentDate employedFrom) {
		this.user = user;
		this.employer = employer;
		this.currentEmployer = true;
		this.jobTitle = jobTitle;
		this.employedFrom = employedFrom;
	}

	// Employment history
	public Employment(User user, Company employer, String jobTitle, EmploymentDate employedFrom,
			EmploymentDate employedTo) {
		this.user = user;
		this.employer = employer;
		this.currentEmployer = false;
		this.jobTitle = jobTitle;
		this.employedFrom = employedFrom;
		this.employedTo = employedTo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getEmployer() {
		return employer;
	}

	public void setCompany(Company employer) {
		this.employer = employer;
	}

	public boolean getFeaturedEmployee() {
		return featuredEmployee;
	}

	public void setFeaturedEmployee(boolean featuredEmployee) {
		this.featuredEmployee = featuredEmployee;
	}

	public boolean getCurrentEmployer() {
		return currentEmployer;
	}

	public void setCurrentEmployer(boolean currentEmployer) {
		this.currentEmployer = currentEmployer;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public EmploymentDate getEmployedFrom() {
		return employedFrom;
	}

	public void setEmployedFrom(EmploymentDate employedFrom) {
		this.employedFrom = employedFrom;
	}

	public EmploymentDate getEmployedTo() {
		return employedTo;
	}

	public void setEmployedTo(EmploymentDate employedTo) {
		this.employedTo = employedTo;
	}

	public static class EmploymentDate {
		private Integer month;
		private Integer year;

		/*public EmploymentDate(Boolean current) {
			if (current) {
				LocalDateTime now = LocalDateTime.now();
				this.month = now.getMonthValue();
				this.year = now.getYear();
			}
		}*/

		public EmploymentDate(Integer month, Integer year) {
			this.month = month;
			this.year = year;
		}

		public Integer getMonth() {
			return month;
		}

		public Integer getYear() {
			return year;
		}
	}
}
