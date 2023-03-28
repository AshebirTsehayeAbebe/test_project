package optifoodmanagement.config.newconfig;

public interface ModelService {
	Iterable<Model> findAll();
	Model save(Model model);
}
