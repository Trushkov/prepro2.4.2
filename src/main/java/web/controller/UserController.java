package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.dao.RoleDao;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RoleDao roleDao;

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String getUserInfo(Model model){
		User user = (User) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		model.addAttribute("user", user);
		return "user";
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String getUsers(ModelMap model) {
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.getUsers());
		return "admin";
	}

	@RequestMapping(value = "admin/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, Model model) {
		if (user.getId() == 0) {
			userService.addUser(user);
			model.addAttribute("users", userService.getUsers());
			model.addAttribute("ROLES", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));

		} else {
			userService.updateUser(user);
		}
		return "redirect:admin";
	}

	@RequestMapping(value = "admin/delete")
	public String removeUser(@RequestParam("id") long id) {
		userService.remove(id);
		return "redirect:admin";
	}

	@RequestMapping(value = "admin/edit-user")
	public String edit(@RequestParam("id") long id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("ROLES", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
		return "edit-user";
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String getForm(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("ROLES", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
		return "registration";
	}

	@RequestMapping(value = "registration/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("user") User user,
					   @RequestParam(value = "rolesValues") String [] roles){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roleSet = new HashSet<>();
		for (String role: roles
		) {
			if (!roleDao.hasRole(role)){
				roleDao.addRole(new Role(role));
			}
			roleSet.add(roleDao.getRole(role));
		}
		user.setRoles(roleSet);
		userService.addUser(user);
		return "redirect:registration";
	}
}