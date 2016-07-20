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
	
	public List<User> findRecommendedFriends(String userUuid) {
		List<User> recommendedFriends = new ArrayList<User>();
		
		List<Map<String,List<User>>> resIterator = userRepo.findCommonConnection(userUuid);
		for (Map<String, List<User>> items: resIterator){
			for (String mapKey: items.keySet()) {
				// First row = the user itself
				// Second row = the common friend
				// Third row = the recommendation based on the common friend
				recommendedFriends.add(items.get(mapKey).get(2));
			}
		}
		return recommendedFriends;
	}
}
