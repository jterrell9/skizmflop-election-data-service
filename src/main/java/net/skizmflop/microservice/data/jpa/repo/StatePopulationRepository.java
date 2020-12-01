package net.skizmflop.microservice.data.jpa.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.skizmflop.commons.jpa.entity.StatePopulation;

@Repository
public interface StatePopulationRepository extends CrudRepository<StatePopulation, Long> {

}
