package net.skizmflop.microservice.data.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.skizmflop.commons.jpa.entity.ElectoralCollege;
import net.skizmflop.commons.jpa.entity.State;
import net.skizmflop.commons.jpa.entity.StatePopulation;
import net.skizmflop.commons.util.CSVParser;
import net.skizmflop.commons.util.DataPopulator;
import net.skizmflop.microservice.data.service.ElectoralCollegeDataAccessService;
import net.skizmflop.microservice.data.service.StateDataAccessService;
import net.skizmflop.microservice.data.service.StatePopulationDataAccessService;

public class UtilDriver {

	/**
	 * Main method driver for running utilities.
	 * @param args
	 */
	public static void main(String[] args) {
//		populationCSVreader();
		
	}
	
	private static void populateElectoralData() {
		ElectoralCollegeDataAccessService electoralCollegeDataAccessService = new ElectoralCollegeDataAccessService();
		for(ElectoralCollege stateData: DataPopulator.populate2020ElectoralData()) {
			electoralCollegeDataAccessService.save(stateData);
		}
	}
	
	/**
	 * parses csv file and inserts into db
	 */
	private static void populationCSVreader() {
		StateDataAccessService stateDataAccessService = new StateDataAccessService();
		StatePopulationDataAccessService populationDataService = new StatePopulationDataAccessService();
		
		final int YEAR = 2018;
		List<String[]> populationData_2018 = CSVParser.parseFile(new File("/Users/jack/dev/data/census/ACSDT1Y2018.B01003_2020-08-02T073735/ACSDT1Y2018.B01003_data_with_overlays_2020-08-02T073730.csv"), 2);
		List<StatePopulation> statePopulations = new ArrayList<>();
		int count = 0;
		for(int i=0; i<populationData_2018.size(); i++) {
			if(i == 0) continue;
			if(StringUtils.equals(populationData_2018.get(i)[1], "Puerto Rico")) continue;
			if(StringUtils.contains(populationData_2018.get(i)[1], ",")) continue;
			State state = stateDataAccessService.getByUniqueName(populationData_2018.get(i)[1]);
			Long population = Long.parseLong(populationData_2018.get(i)[2]);
			populationDataService.save(new StatePopulation(state, population, YEAR));
			System.out.printf("%s: %,d%n", state, population);
//			System.out.println(StringUtils.join(populationData_2018.get(i), ", "));
			count++;
		}
		System.out.println(count + " RECORDS SAVED TO DATABASE");
	}
	
}
