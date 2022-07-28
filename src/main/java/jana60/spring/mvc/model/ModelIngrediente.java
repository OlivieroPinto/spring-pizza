package jana60.spring.mvc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modelIngredienti")
public class ModelIngrediente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String nome;

	@ManyToMany(mappedBy = "ingredienti")
	List<ModelPizza> pizza;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ModelPizza> getPizza() {
		return pizza;
	}

	public void setPizza(List<ModelPizza> pizza) {
		this.pizza = pizza;
	}
}
