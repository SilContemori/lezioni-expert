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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.model.Images;
import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.model.User;
import it.uniroma3.lezioniexpert.repository.ImagesRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import it.uniroma3.lezioniexpert.service.ProfessorService;
import it.uniroma3.lezioniexpert.service.UserService;

import jakarta.validation.Valid;


@Controller
public class AuthenticationController {
	@Autowired
	private CredentialsService credentialsService;
	@Autowired UserService userService;
	@Autowired ProfessorService professorService;
	@Autowired private ImagesRepository imageRepository;
	
	
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
			if (credentials.getProfessor()!=null) {
				model.addAttribute("professor", credentials.getProfessor());
				return "homePage.html";
			}else {
				if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
					model.addAttribute("admin", credentials.getUser());
				}else {
					System.out.println("passo user: "+credentials.getUser()+"-------------------------------------");
					model.addAttribute("user", credentials.getUser());
				}
			}
		}
        return "homePage.html";
	}
	
	/*GET DELLA PAGINA DI LOG-IN CON LA FORM PER INSERIRE I DATI*/
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "logInPage.html";
	}
	
	/*GET DELLA PAGINA PER REGISTRARE I DATI E POST PER INSERIRE I DATI NEL DB DI UNO USER GENERICO*/
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
            return "logInPage.html";
        }
        return "registerUser.html";
    }
	
	/*GET DELLA PAGINA PER REGISTRARE I DATI E POST PER INSERIRE I DATI NEL DB DI UN PROFESSORE*/
	@GetMapping(value = {"/registerProfessor"}) 
	public String showRegisterFormProfessor (Model model) {
		model.addAttribute("professor", new Professor());
		model.addAttribute("credentials", new Credentials() );
		return "registerProfessor.html";
	}
	
	@PostMapping(value = { "/registerProfessor" },consumes = "multipart/form-data")
    public String registerProfessor(@Valid @ModelAttribute Professor professor,@RequestPart("file") MultipartFile file,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	try {
				Images image=new Images();
				image.setImageData(file.getBytes());
				professor.setProfileImage(image);
				this.imageRepository.save(image);
			} catch (Exception e) {
				System.out.println("erroreeee");
			}
            professorService.saveProfessor(professor);
            credentials.setProfessor(professor);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("professor", professor);
            return "logInPage.html";
        }
        return "registerProfessor.html";
    }
	
	/*GET CHE MOSTRA LA HOME PAGE DOPO LOG IN*/
	@GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if(credentials.getRole()!=null) {
	    	if (credentials.getProfessor()!=null) {
	    		model.addAttribute("professor", credentials.getProfessor());
	    		return "homePage.html";
	        }else {
				if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
					model.addAttribute("admin",credentials.getRole());
				}else {
					model.addAttribute("user",credentials.getRole());
				}
			}
    	}
        return "homePage.html";
    }
	
}
