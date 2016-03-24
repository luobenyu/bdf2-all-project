package test;

import java.util.List;

import com.bstek.bdf2.job.model.JobDefinition;
import com.bstek.bdf2.job.service.IJobDataService;

public class TestJobDataService implements IJobDataService {

	public List<JobDefinition> filterJobs(List<JobDefinition> jobs) {
		return jobs;  
	}

	public String getCompanyId() {
		return "bstek"; 
	}

}
