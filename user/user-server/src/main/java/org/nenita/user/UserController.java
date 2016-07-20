package org.nenita.user;

import java.util.List;

import org.nenita.user.svc.Recommendation;
import org.nenita.user.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserSvc userSvc;
	
	@RequestMapping(path = "/api/user/{uuid}/recommend-friends", method = RequestMethod.GET)
	public ResponseEntity<?> add(@PathVariable String uuid) {

		HttpHeaders httpHeaders = new HttpHeaders();
		List<Recommendation> recommendations = userSvc.findRecommendedFriends(uuid);
		return new ResponseEntity<>(recommendations, httpHeaders, HttpStatus.OK);
	}
}
