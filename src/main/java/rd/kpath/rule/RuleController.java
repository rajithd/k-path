package rd.kpath.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class RuleController {

    @Autowired
    private RuleWorker ruleWorker;

    @RequestMapping(value = "/rule/define", method = RequestMethod.GET)
    public String defineRule() {
        return "rule/rule";
    }

    @RequestMapping(value = "/rule/define", method = RequestMethod.POST)
    public String createRule(Rule rule) {
        Map<RuleEnum,String> map = ruleWorker.rdfToMap();
        return "rule/rule";
    }

//    @RequestMapping(value = "/list/kpath", method = RequestMethod.GET)
//    public String listRule() {
//        return "rule/kpath";
//    }
}
