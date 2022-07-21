package jana60.spring.mvc.repository;

import org.springframework.data.repository.CrudRepository;

import jana60.spring.mvc.model.ModelPizza;

public interface RepositoryPizza extends CrudRepository<ModelPizza, Integer> {

}
