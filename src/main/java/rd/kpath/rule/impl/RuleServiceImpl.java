package rd.kpath.rule.impl;


import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import org.springframework.stereotype.Service;
import rd.kpath.rule.RuleService;

@Service
public class RuleServiceImpl implements RuleService {

    private String targetItem = null;
    private String targetType = null;

    @Override
    public Model findModel(String rdfUrl) {
        try {
            Model model = ModelFactory.createDefaultModel();
            FileManager.get().readModel(model, rdfUrl);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean hasItemToSearch(Model model) {
        String queryString =
                "SELECT ?subject ?predicate ?object " +
                        "WHERE {" +
                        "   ?subject <" + RDF.type + "> ?object. " +
                        "   }";

        QueryExecution qe = findQueryExecutionByQuery(queryString, model);
        ResultSet rs = qe.execSelect();

        // collect the data type property names
        while (rs.hasNext()) {
            ResultBinding binding = (ResultBinding) rs.next();
            RDFNode rn = binding.get("subject");
            if (rn != null) {
                targetItem = rn.toString();
            }
            rn = binding.get("object");
            if (rn != null) {
                targetType = rn.toString();
            }
        }
        qe.close();

        if (targetItem == null || targetItem.length() == 0) {
            return false;
        }
        if (targetType == null || targetType.length() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String getExperience(Model model) {
        String queryString =
                "SELECT ?value " +
                        "WHERE {" +
                        "   <" + targetItem + "> <http://www.rajith.com/kpath#experience> ?value. " +
                        "   }";

        QueryExecution qe = findQueryExecutionByQuery(queryString, model);
        ResultSet rs = qe.execSelect();

        while (rs.hasNext()) {
            ResultBinding binding = (ResultBinding) rs.next();
            RDFNode rn = binding.get("value");
            if (rn != null && !rn.isAnon()) {
                return rn.toString();
            }
        }
        qe.close();
        return null;
    }

    @Override
    public String getArea(Model model) {

        String queryString =
                "SELECT ?value " +
                        "WHERE {" +
                        "   <" + targetItem + "> <http://www.rajith.com/kpath#area> ?value. " +
                        "   }";

        QueryExecution qe = findQueryExecutionByQuery(queryString, model);
        ResultSet rs = qe.execSelect();

        while (rs.hasNext()) {
            ResultBinding binding = (ResultBinding) rs.next();
            RDFNode rn = binding.get("value");
            if (rn != null && !rn.isAnon()) {
                return rn.toString();
            }
        }
        qe.close();
        return null;

    }

    @Override
    public String getSpeciality(Model model) {
        String queryString =
                "SELECT ?value " +
                        "WHERE {" +
                        "   <" + targetItem + "> <http://www.rajith.com/kpath#speciality> ?value. " +
                        "   }";

        QueryExecution qe = findQueryExecutionByQuery(queryString, model);
        ResultSet rs = qe.execSelect();

        while (rs.hasNext()) {
            ResultBinding binding = (ResultBinding) rs.next();
            RDFNode rn = binding.get("value");
            if (rn != null && !rn.isAnon()) {
                return rn.toString();
            }
        }
        qe.close();
        return null;
    }

    private QueryExecution findQueryExecutionByQuery(String queryString, Model model) {
        Query q = QueryFactory.create(queryString);
        return QueryExecutionFactory.create(q, model);
    }
}
