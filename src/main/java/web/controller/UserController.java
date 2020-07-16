package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC-SECURITY application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "hello";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public String getUsers(ModelMap model) {
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.getUsers());
		return "users";
	}

	@RequestMapping(value = "admin/users/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, Model model) {
		if (user.getId() == 0) {
			userService.addUser(user);
			model.addAttribute("users", userService.getUsers());
			model.addAttribute("ROLES", Arrays.asList("USER", "ADMIN"));

		} else {
			userService.updateUser(user);
		}
		return "redirect:users";
	}

	@RequestMapping(value = "admin/users/delete")
	public String removeUser(@RequestParam("id") long id) {
		userService.remove(id);
		return "redirect:/users";
	}

	@RequestMapping(value = "admin/edit-user")
	public String edit(@RequestParam("id") long id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("ROLES", Arrays.asList("USER", "ADMIN"));
		return "edit-user";
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String getForm(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("ROLES", Arrays.asList("USER", "ADMIN"));
		return "registration";
	}

	@RequestMapping(value = "registration/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("user") User user, Model model,
					   @RequestParam(value = "rolesValues") String [] roles){
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("user", new User());
		model.addAttribute("ROLES", Arrays.asList("USER", "ADMIN"));
		/*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));*/
		Set<Role> roleSet = new HashSet<>();
		for (String role: roles
		) {
			roleSet.add(new Role(role));
		}
		user.setRoles(roleSet);
		userService.addUser(user);
		return "redirect:registration";
	}

}