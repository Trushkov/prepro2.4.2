package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping(value = "admin/users", method = RequestMethod.GET)
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

		} else {
			userService.updateUser(user);
		}
		return "redirect:admin/users";
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
		return "edit-user";
	}

	@RequestMapping(value = "registration")
	public String save(){
		return "registration";
	}

}