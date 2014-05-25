package rd.kpath.linkedin;

import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.NetworkStatistics;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * Responsible for linkedin related services
 */
@Controller
public class LinkedInConnectionsController {

	private LinkedIn linkedIn;

	@Inject
	public LinkedInConnectionsController(LinkedIn linkedIn) {
		this.linkedIn = linkedIn;
	}

    /**
     * Get first degree and second degree connections
     * @param model
     * @return
     */
	@RequestMapping(value="/linkedin/connections", method= RequestMethod.GET)
	public String connections(Model model) {
		NetworkStatistics statistics = linkedIn.connectionOperations().getNetworkStatistics();
		model.addAttribute("firstDegreeCount", statistics.getFirstDegreeCount());
		model.addAttribute("secondDegreeCount", statistics.getSecondDegreeCount());
		model.addAttribute("connections", linkedIn.connectionOperations().getConnections());
		return "linkedin/connections";
	}

    @RequestMapping(value="/list/kpath", method= RequestMethod.GET)
    public String findLinkedInProfiles(Model model){
        List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
        List<LinkedInProfile> eligibleConnections = new LinkedList<LinkedInProfile>();
        for(LinkedInProfile linkedInProfile : connections){
            if(linkedInProfile.getIndustry() == null){
                continue;
            }
            if(linkedInProfile.getIndustry().equalsIgnoreCase("Information Technology and Services") || linkedInProfile.getIndustry().equalsIgnoreCase("Computer Software")){
                eligibleConnections.add(linkedInProfile);
            }
        }
        model.addAttribute("connections", eligibleConnections);
        return "rule/kpath";
    }

}
