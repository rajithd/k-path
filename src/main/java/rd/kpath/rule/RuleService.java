package rd.kpath.rule;

import com.hp.hpl.jena.rdf.model.Model;

public interface RuleService {

    public Model findModel(String rdfUrl);

    public boolean hasItemToSearch(Model model);

    public String getExperience(Model model);

    public String getArea(Model model);

    public String getSpeciality(Model model);
}
