package org.pti.poster.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.pti.poster.TokenUtils;
import org.pti.poster.service.PersonService;
import org.pti.poster.view.TokenTransfer;
import org.pti.poster.view.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Api(value = "Providers user management of application", position = 3)
@RequestMapping("rest/user")
@RestController
@Produces(MediaType.APPLICATION_JSON)
public class UserManagementResource {

    @Autowired
    PersonService personService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    @ApiOperation(value = "Returns a user by id.")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserTransfer getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && principal.equals("anonymousUser")) {
//            throw new WebApplicationException(401); TODO
            throw new IllegalStateException("401");
        }
        UserDetails userDetails = (UserDetails) principal;

        return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails));
    }

    /**
     * @param username The name of the user.
     * @param password The password of the user.
     * @return A transfer containing the authentication token.
     */
    @ApiOperation(value = "Authenticates a user and creates an authentication token.")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public TokenTransfer authenticate(@FormParam("username") String username, @FormParam("password") String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
         * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
        UserDetails userDetails = this.personService.loadUserByUsername(username);
        return new TokenTransfer(TokenUtils.createToken(userDetails));
    }

    private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }
        return roles;
    }
    
//    @ApiOperation(value = "Creates a new user.")
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public Long addUser(@RequestBody Person person){
//        return personService.addUser(person);
//    }
//
//
//    @ApiOperation(value = "Deletes existing user.")
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public void deleteUser(@PathVariable Long id){
//        personService.remove(id);
//    }
}