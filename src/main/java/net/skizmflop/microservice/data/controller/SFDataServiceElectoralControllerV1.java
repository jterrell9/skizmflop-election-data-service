package net.skizmflop.microservice.data.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import net.skizmflop.microservice.data.service.ElectoralCollegeDataAccessService;

/**
 * Data Service Controller V1
 * @author jack
 */
@Controller
@Api(value="Electoral Controller")
public class SFDataServiceElectoralControllerV1 extends SFDataServiceBaseControllerV1 {
	
	@Autowired
	private ElectoralCollegeDataAccessService electoralCollegeService;
	
	/**
	 * <p>Get info on State.<p>
	 * @param id
	 * @return Response
	 */
	@GetMapping(path="/electoral_votes/{state_id}/{election_year}")
	@ResponseBody
	public Integer getElectoralVotesByStateAndElectionYear(@PathVariable("state_id") String stateId, @PathVariable(value="election_year", required=false) Optional<Integer> electionYear) {
		if(electionYear.isPresent()) {
			return electoralCollegeService.getResultByStateAndElectionYear(stateId, electionYear.get()).get(0).getElectoralVotes();
		}else {
			return electoralCollegeService.get2020ElectoralVotesResultByState(stateId);
		}
	}
	
}
