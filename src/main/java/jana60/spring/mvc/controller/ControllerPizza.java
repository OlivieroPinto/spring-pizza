package jana60.spring.mvc.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/cancella/{id}")
	public String delete(@PathVariable("id") Integer pizzaId, RedirectAttributes ra) {
		Optional<ModelPizza> result = repo.findById(pizzaId);
		if (result.isPresent()) {
			// repo.deleteById(bookId);
			repo.delete(result.get());
			ra.addFlashAttribute("successMessage", "Pizza " + result.get().getNome() + " deleted!");
			return "/home";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book con id " + pizzaId + " not present");
		}

	}

	@GetMapping("/modificaPizza/{id}")
	public String edit(@PathVariable("id") Integer pizzaId, Model model) {
		Optional<ModelPizza> result = repo.findById(pizzaId);
		// controllo se il Book con quell'id è presente
		if (result.isPresent()) {
			model.addAttribute("pizza", result.get());
			return "inserisciPizza";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book con id " + pizzaId + " not present");
		}

	}

}
