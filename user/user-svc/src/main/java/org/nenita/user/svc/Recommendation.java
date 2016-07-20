package org.nenita.user.svc;

public class Recommendation {

	private User recommendedUser;
	private User connectedThru;
	
	public Recommendation(User recommendedUser, User connectedThru) {
		this.recommendedUser = recommendedUser;
		this.connectedThru = connectedThru;
	}
	
	public User getRecommendedUser() {
		return recommendedUser;
	}
	
	public User getConnectedThru() {
		return connectedThru;
	}
	
	public static class User {
		private String name;
		private String id;
		
		public User(String name, String id) {
			this.name = name;
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public String getId() {
			return id;
		}
	}
}
