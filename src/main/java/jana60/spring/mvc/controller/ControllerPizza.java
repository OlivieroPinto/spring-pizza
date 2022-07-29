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

import jana60.spring.mvc.model.ModelIngrediente;
import jana60.spring.mvc.model.ModelPizza;
import jana60.spring.mvc.repository.RepositoryIngredienti;
import jana60.spring.mvc.repository.RepositoryPizza;

@Controller
@RequestMapping("/")
public class ControllerPizza {
	@Autowired
	private RepositoryPizza repo;
	@Autowired
	private RepositoryIngredienti ingredientiRepo;

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/pizze")
	public String pizze(Model model) {
		List<ModelPizza> listaPizze = (List<ModelPizza>) repo.findAll();
		// List<ModelIngrediente> listaIngrediente = (List<ModelIngrediente>)
		// ingredientiRepo.findAll();
		model.addAttribute("listaPizze", listaPizze);
		// model.addAttribute("listaIngrediente", listaIngrediente);
		return "pizze";
	}

	@GetMapping("/inserisciPizza")
	public String pizzaForm(Model model) {
		model.addAttribute("pizza", new ModelPizza());
		model.addAttribute("listaIngrediente", ingredientiRepo.findAll());
		return "inserisciPizza";
	}

	@PostMapping("/inserisciPizza")
	public String save(@Valid @ModelAttribute("pizza") ModelPizza formPizza, BindingResult br) {
		if (br.hasErrors()) {
			return "/";
		} else {
			repo.save(formPizza);
			return "redirect:/inserisciPizza";
		}
	}

	@GetMapping("/inserisciIngrediente")
	public String ingredienteForm(Model model) {
		model.addAttribute("ingrediente", new ModelIngrediente());
		model.addAttribute("listaIngrediente", ingredientiRepo.findAll());
		return "inserisciIngrediente";
	}

	@PostMapping("/inserisciIngrediente")
	public String save(@Valid @ModelAttribute("ingrediente") ModelIngrediente formIngrediente, BindingResult br) {
		if (br.hasErrors()) {
			return "/";
		} else {
			ingredientiRepo.save(formIngrediente);
			return "redirect:/inserisciIngrediente";
		}
	}

	@GetMapping("/cancella/{id}")
	public String delete(@PathVariable("id") Integer pizzaId, RedirectAttributes ra) {
		Optional<ModelPizza> result = repo.findById(pizzaId);
		if (result.isPresent()) {
			repo.delete(result.get());
			ra.addFlashAttribute("successMessage", "Pizza " + result.get().getNome() + " deleted!");
			return "/home";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book con id " + pizzaId + " not present");
		}

	}

	@GetMapping("/modificaPizza/{id}")
	public String edit(@PathVariable("id") Integer pizzaId, Model model, @PathVariable("id") Integer ingredienteId) {
		Optional<ModelPizza> result = repo.findById(pizzaId);
		Optional<ModelIngrediente> resultIngredienti = ingredientiRepo.findById(ingredienteId);
		if (result.isPresent()) {
			model.addAttribute("pizza", result.get());
			model.addAttribute("listaIngrediente", resultIngredienti.get());
			return "inserisciPizza";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book con id " + pizzaId + " not present");
		}

	}

}
