/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.dbsupport;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.GeographyTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.LithoStratTreeDef;
import edu.ku.brc.specify.datamodel.LocationTreeDef;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Aug 14, 2007
 *
 */
public class SpecifyQueryAdjusterForDomain extends QueryAdjusterForDomain
{
    
    private static final String SPECIFYUSERID  = "SPECIFYUSERID";
    private static final String DIVISIONID     = "DIVISIONID";
    private static final String COLMEMID       = "COLMEMID";
    
    private static final String TAXTREEDEFID   = "TAXTREEDEFID";
    private static final String LOCTREEDEFID   = "LOCTREEDEFID";
    private static final String LITHOTREEDEFID = "LITHOTREEDEFID";
    private static final String GTPTREEDEFID   = "GTPTREEDEFID";
    private static final String GEOTREEDEFID   = "GEOTREEDEFID";

    public SpecifyQueryAdjusterForDomain()
    {
        // no op
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain#adjustSQL(java.lang.String, boolean)
     */
    @Override
    public String adjustSQL(final String sql)
    {
        // SpecifyUser should NEVER be null nor the Id !
        SpecifyUser user = SpecifyUser.getCurrentUser();
        if (user != null)
        {
            Integer id = user.getId();
            if (id != null)
            {
                String adjSQL = sql;
                if (StringUtils.contains(adjSQL, SPECIFYUSERID))
                {
                    adjSQL = StringUtils.replace(adjSQL, SPECIFYUSERID, Integer.toString(id));
                }
                
                if (StringUtils.contains(adjSQL, DIVISIONID))
                {
                    Integer divId = user.getAgent().getDivision() != null ? user.getAgent().getDivision().getDivisionId() : null;
                    if (divId != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, DIVISIONID, Integer.toString(divId));
                    }
                }
                
                //System.out.println(adjSQL);
                if (StringUtils.contains(adjSQL, COLMEMID))
                {
                    Collection collection = Collection.getCurrentCollection();
                    if (collection != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, COLMEMID, Integer.toString(collection.getCollectionId()));
                    }
                }
                
                if (StringUtils.contains(adjSQL, TAXTREEDEFID))
                {
                    TaxonTreeDef taxonTreeDef = TaxonTreeDef.getCurrentTaxonTreeDef();
                    if (taxonTreeDef != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, TAXTREEDEFID, Integer.toString(taxonTreeDef.getTaxonTreeDefId()));
                    }
                }
                
                if (StringUtils.contains(adjSQL, GTPTREEDEFID))
                {
                    GeologicTimePeriodTreeDef gtpTreeDef = GeologicTimePeriodTreeDef.getCurrentGeologicTimePeriodTreeDef();
                    if (gtpTreeDef != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, GTPTREEDEFID, Integer.toString(gtpTreeDef.getGeologicTimePeriodTreeDefId()));
                    }
                }
                
                if (StringUtils.contains(adjSQL, LOCTREEDEFID))
                {
                    LocationTreeDef locTreeDef = LocationTreeDef.getCurrentLocationTreeDef();
                    if (locTreeDef != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, LOCTREEDEFID, Integer.toString(locTreeDef.getLocationTreeDefId()));
                    }
                }
                
                if (StringUtils.contains(adjSQL, LITHOTREEDEFID))
                {
                    LithoStratTreeDef lithoTreeDef = LithoStratTreeDef.getCurrentLithoStratTreeDef();
                    if (lithoTreeDef != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, LITHOTREEDEFID, Integer.toString(lithoTreeDef.getLithoStratTreeDefId()));
                    }
                }
                
                if (StringUtils.contains(adjSQL, GEOTREEDEFID))
                {
                    GeographyTreeDef lithoTreeDef = GeographyTreeDef.getCurrentGeographyTreeDef();
                    if (lithoTreeDef != null)
                    {
                        adjSQL = StringUtils.replace(adjSQL, GEOTREEDEFID, Integer.toString(lithoTreeDef.getGeographyTreeDefId()));
                    }
                }
                
                return adjSQL;
                
            } else
            {
                throw new RuntimeException("The SpecifyUser cannot be null!");
            }
        }
        return super.adjustSQL(sql);
    }
}
