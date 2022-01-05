package org.team.gstreet.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team.gstreet.member.dto.UsersDTO;
import org.team.gstreet.member.service.ProfileService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

	private final ProfileService profileService;

	private static final String authorizationRequestBaseUris = "oauth2/authorization";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
	private final ClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/customLogin")
	public void loginInput(String userId) {  // 테스트후 삭제해야합니다@@

		log.info("custom Login Page....");
	}

	@GetMapping("/register")
	public void memberRegister() {
		log.info("register page...");
	}

	@PostMapping("/register")
	public String memberRegisterPost(UsersDTO usersDTO, RedirectAttributes redirectAttributes) {
		String id = profileService.register(usersDTO);
		redirectAttributes.addFlashAttribute("result", id);
		return "redirect:/exmain";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/profile")
	public void getProfile(@AuthenticationPrincipal String id, Model model) {
		model.addAttribute("usersDTO", profileService.getProfile(id));
	}

	@PutMapping("/profile")
	public void putProfile() {

	}

	@GetMapping("/exmain")
	public void exMain(Model model) {
		UsersDTO usersDTO = new UsersDTO();
		model.addAttribute("usersDTO", usersDTO);
	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/login")
	public String getLoginPage(Model model) throws Exception {
		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
				.as(Iterable.class);
		if (type != ResolvableType.NONE &&
				ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
		}
		assert clientRegistrations != null;
		clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(),
		                                                                         authorizationRequestBaseUris
				                                                                         + "/"
				                                                                         + registration.getRegistrationId()));
		model.addAttribute("urls", oauth2AuthenticationUrls);

		return "login";
	}

	@GetMapping("/login/{oauth2}")
	public String loginSocial(@PathVariable String oauth2, HttpServletResponse httpServletResponse){
		return "redirect:/oauth2/authorization/" + oauth2;

	}

	@RequestMapping("/accessDenied")
	public String getAccessDenied() {return "accessDenied";}
}
