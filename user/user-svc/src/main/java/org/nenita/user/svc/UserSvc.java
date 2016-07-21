package org.nenita.user.svc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nenita.user.domain.User;
import org.nenita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSvc {

	@Autowired
	private UserRepository userRepo;

	public List<Recommendation> findRecommendedFriends(String userUuid) {
		List<Recommendation> recommendedFriends = new ArrayList<Recommendation>();

		List<Map<String, List<User>>> resIterator = userRepo.findCommonConnection(userUuid);
		for (Map<String, List<User>> items : resIterator) {
			for (String mapKey : items.keySet()) {
				// First row = the user itself
				// Second row = the common friend
				// Third row = the recommendation based on the common friend
				User recUser = items.get(mapKey).get(2);
				User connection = items.get(mapKey).get(1);
				Recommendation rec = new Recommendation(
						new Recommendation.User(recUser.getFirstname() + " " + recUser.getLastname(),
								recUser.getUuid()),
						new Recommendation.User(connection.getFirstname() + " " + connection.getLastname(),
								connection.getUuid()));
				recommendedFriends.add(rec);
			}
		}
		return recommendedFriends;
	}
	
	// Add method for newsfeed
	// Add method for comments
}
