package rd.kpath.rule.impl;

import com.hp.hpl.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rd.kpath.rule.RuleEnum;
import rd.kpath.rule.RuleService;
import rd.kpath.rule.RuleWorker;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rajith
 */
@Service
public class RuleWorkerImpl implements RuleWorker {

    private static final String ontologyURL = "/home/rajith/ebooks/code_developer_guide/chap15/MyShopBot/example/camera.owl"; //my
    private static final String ontologyNS = "http://www.rajith.com/kpath#";
    private static final String requestRdf = "/home/rajith/my_projects/k-path/src/main/resources/rule.rdf"; //request rdf on UI
    private static final String catalog = "/home/rajith/ebooks/code_developer_guide/chap15/MyShopBot/example/catalogExample1.rdf"; //


    @Autowired
    private RuleService ruleService;

    @Override
    public Map<RuleEnum, String> rdfToMap() {
        Map<RuleEnum, String> map = new HashMap<RuleEnum, String>();
        Model model = ruleService.findModel(requestRdf);
        if (!ruleService.hasItemToSearch(model)) {
            return null;
        }
        map.put(RuleEnum.EXPERIENCE, ruleService.getExperience(model));
        map.put(RuleEnum.AREA, ruleService.getArea(model));
        map.put(RuleEnum.SPECIALITY, ruleService.getSpeciality(model));
        return map;
    }
}
