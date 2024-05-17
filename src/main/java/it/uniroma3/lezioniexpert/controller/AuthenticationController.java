package it.uniroma3.lezioniexpert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.model.User;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import it.uniroma3.lezioniexpert.service.UserService;
import jakarta.validation.Valid;


@Controller
public class AuthenticationController {
	@Autowired
	private CredentialsService credentialsService;
	@Autowired UserService userService;
	
	
	/*GET DELLA HOME PAGE*/
	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "homePage.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//				return "admin/indexAdmin.html";
				return "homePage.html";
			}
		}
        return "homePage.html";
	}
	
	/*GET DELLA PAGINA DI LOG-IN CON LA FORM PER INSERIRE I DATI*/
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "logInPage.html";
	}
	
	/*GET DELLA PAGINA PER REGISTRARE I DATI E POST PER INSERIRE I DATI NEL DB*/
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerUser.html";
	}
	
	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationUserSuccessful.html";
        }
        return "registerUser.html";
    }
	
	/*GET CHE MOSTRA LA HOME PAGE (O PAGINA DEDICATA A SECONDA DEL RUOLO) DOPO LOG IN*/
	@GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//            return "admin/indexAdmin.html";
    		return "homePage.html";
        }
        return "homePage.html";
    }
	
}
