package se.nrm.specify.business.logic.specify;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless; 
import se.nrm.specify.datamodel.Collectionobject; 
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Taxon;
import se.nrm.specify.specify.data.jpa.SpecifyDao;

/**
 *
 * @author ida_ho_99
 */
@Stateless
public class SpecifyLogicImpl implements SpecifyLogic {
     
    @EJB
    private SpecifyDao dao;

    public SpecifyLogicImpl() {
    }

    public SpecifyLogicImpl(SpecifyDao dao) {
        this.dao = dao;
    }

    public SpecifyDao getDao() {
        return dao;
    }

    /**
     * This method retrieves dna sequences from database, with dna sequences to 
     * create a .fasta file for creating blast database.  A .fasta file is built 
     * with ids (combined with collectionobjectid and taxon names) and corresponding 
     * dna sequences.
     * 
     * @return String contains dna sequences
     */
    public String getDNASequenceList() {

        StringBuilder sb = new StringBuilder();

        List<Dnasequence> list = dao.getAll(Dnasequence.class);        // List of Dnasequence from database                       
        for (Dnasequence sequence : list) {
            Collectionobject collectionObj = sequence.getCollectionObject();
            
            String jpql = "SELECT d FROM Determination d WHERE d.collectionObjectID.collectionObjectID = " + 
                                                        collectionObj.getCollectionObjectId() + " and d.isCurrent = true";
            
            Taxon taxon = (Taxon)dao.getEntityByJPQL(jpql);
//            Taxon taxon = dao.getTaxonByCollectionobject(collectionObj);    // get taxon by collectionobject from database
                
            sb.append(">");
            sb.append(collectionObj.getCollectionObjectId());
            sb.append("::");
            sb.append(taxon.getFullName());
            sb.append("::");
            sb.append(taxon.getCommonName());
            sb.append("\n");
            sb.append(sequence.getGeneSequence().replaceAll("[0-9 \t\n\r]", ""));
            sb.append("\n");
        }

        return sb.toString();
    }
}
