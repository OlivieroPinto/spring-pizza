package jana60.spring.mvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.spring.mvc.model.ModelPizza;
import jana60.spring.mvc.repository.RepositoryPizza;

@Controller
@RequestMapping("/")
public class ControllerPizza {
	@Autowired
	private RepositoryPizza repo;

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/pizze")
	public String pizze(Model model) {
		List<ModelPizza> listaPizze = (List<ModelPizza>) repo.findAll();
		model.addAttribute("listaPizze", listaPizze);
		return "pizze";
	}

	@GetMapping("/inserisciPizza")
	public String pizzaForm(Model model) {
		model.addAttribute("pizza", new ModelPizza());
		return "inserisciPizza";
	}

	@PostMapping("/inserisciPizza")
	public String save(@Valid @ModelAttribute("pizza") ModelPizza formPizza, BindingResult br) {
		if (br.hasErrors()) {
			return "/";
		} else {
			repo.save(formPizza);
			return "redirect:/home";
		}
	}

}
