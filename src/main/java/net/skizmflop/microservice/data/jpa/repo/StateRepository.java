package net.skizmflop.microservice.data.jpa.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.skizmflop.commons.jpa.entity.State;

@Repository
public interface StateRepository extends CrudRepository<State, String> {
	
	public List<State> findByName(String name);

}
