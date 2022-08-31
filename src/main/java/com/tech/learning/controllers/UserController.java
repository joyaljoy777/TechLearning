package com.tech.learning.controllers;

import com.tech.learning.configurations.KeyCloakConfig;
import com.tech.learning.constants.KeyCloakProperties;
import com.tech.learning.dto.UserDTO;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String REALM = "SpringBootLearning";
	private static final String USER_ROLE = "user";

	@Autowired
	private KeyCloakConfig keyCloakConfig;
	@Autowired
	private KeyCloakProperties keyCloakProperties;

	@Autowired
	private Keycloak keycloak;

//    @GetMapping(path = "/")
//    public String index() {
//        return "external";
//    }
//
//    @GetMapping(path = "/customers")
//    public String customers(Principal principal, Model model) {
////        addCustomers();
////        model.addAttribute("customers", customerDAO.findAll());
////        model.addAttribute("username", principal.getName());
//        return "customers";
//    }

	@PostMapping(path = "/signin")
	public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {

		Map<String, Object> clientCredentials = new HashMap<>();
//        Since client is public
		clientCredentials.put("secret", "");
		clientCredentials.put("grant_type", "password");

		Configuration configuration = new Configuration(keyCloakProperties.AUTH_SERVER_URL, keyCloakProperties.REALM,
				keyCloakProperties.CLIENT_ID, clientCredentials, null);
		AuthzClient authzClient = AuthzClient.create(configuration);

		AccessTokenResponse response = authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<String>> getAllUsers() {
		keycloak.tokenManager().getAccessToken();
		// Get realm (Why?)
		RealmResource realmResource = keycloak.realm(REALM);
		UsersResource usersResource = realmResource.users();
		var usersRepresentation = usersResource.list();
		var users = usersRepresentation.stream().map(userRepresentation -> userRepresentation.getUsername())
				.collect(Collectors.toList());
		return ResponseEntity.ok(users);
	}

	@GetMapping("/getUser/{userId}")
	public ResponseEntity<Object> getAllUsers(@PathVariable String userId) {
		keycloak.tokenManager().getAccessToken();
		// Get realm (Why?)
		RealmResource realmResource = keycloak.realm(REALM);
		UsersResource usersResource = realmResource.users();
		// We can search with multiple parameters
		List<UserRepresentation> usersRepresentation = usersResource.search(userId);
		if (usersRepresentation.isEmpty())
			return ResponseEntity.ok("No user found with id: " + userId);
		return ResponseEntity.ok(usersRepresentation);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {

//        Separate into a bean
//        Keycloak keycloak = keyCloakConfig.getKeycloak();
		// Why this?
		// If the current token is null, it will call keycloak and get one and will set
		// currentToken
		keycloak.tokenManager().getAccessToken();

		UserRepresentation userKeyCloakRepresentation = getUserKeyCloakRepresentation(userDTO);

		// Get realm (Why?)
		RealmResource realmResource = keycloak.realm(REALM);
		// Plural
		UsersResource usersResource = realmResource.users();
		Response response = usersResource.create(userKeyCloakRepresentation);

		// Some problem here
		if (response.getStatus() == 201) {
			String userId = CreatedResponseUtil.getCreatedId(response);
			// Singular
			UserResource userResource = usersResource.get(userId);
			// Get realm role student
			RoleRepresentation realmRoleUser = realmResource.roles().get(USER_ROLE).toRepresentation();
			// Assign realm role student to user
			userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));
			return ResponseEntity.ok("Successfully Created: " + userDTO.getUserName());
		}
		return ResponseEntity.internalServerError().body("User creation failed for: " + userDTO.getUserName());
	}

	private UserRepresentation getUserKeyCloakRepresentation(UserDTO userDTO) {
		UserRepresentation userKeyCloakRepresentation = new UserRepresentation();
		userKeyCloakRepresentation.setEnabled(true);
		userKeyCloakRepresentation.setUsername(userDTO.getUserName());
		userKeyCloakRepresentation.setCredentials(Arrays.asList(getCredentials(userDTO)));
		userKeyCloakRepresentation.setFirstName(userDTO.getFirstName());
		userKeyCloakRepresentation.setLastName(userDTO.getLastName());
		userKeyCloakRepresentation.setEmail(userDTO.getEmail());
		userKeyCloakRepresentation.setRealmRoles(Arrays.asList(USER_ROLE));
		return userKeyCloakRepresentation;
	}

	// create password credential
	private CredentialRepresentation getCredentials(UserDTO userDTO) {
		CredentialRepresentation passwordCred = new CredentialRepresentation();
		passwordCred.setTemporary(false);
		passwordCred.setType(CredentialRepresentation.PASSWORD);
		passwordCred.setValue(userDTO.getPassword());
		return passwordCred;
	}

}
