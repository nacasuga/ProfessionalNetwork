package org.nenita.user;

import javax.validation.Valid;

import org.nenita.organization.domain.Company;
import org.nenita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserRepository repo;

	@RequestMapping(path = "/api/organization", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> add(@RequestBody @Valid CompanyInput input) {

		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.setLocation(ServletUriComponentsBuilder
		//		.fromCurrentRequest().path("/{id}")
		//		.buildAndExpand(result.getId()).toUri());
		//stripePaymentSvc.pay(input.getStripeToken(), input.getAmount());
		Company co = repo.findByName(input.getName());
		System.out.println("Company: " + co);
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
}
