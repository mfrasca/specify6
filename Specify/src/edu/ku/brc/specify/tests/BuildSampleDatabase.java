/**
 * Copyright (C) 2006 The University of Kansas
 * 
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.tests;

import static edu.ku.brc.specify.tests.DataBuilder.createAccession;
import static edu.ku.brc.specify.tests.DataBuilder.createAccessionAgent;
import static edu.ku.brc.specify.tests.DataBuilder.createAddress;
import static edu.ku.brc.specify.tests.DataBuilder.createAgent;
import static edu.ku.brc.specify.tests.DataBuilder.createAttributeDef;
import static edu.ku.brc.specify.tests.DataBuilder.createCatalogSeries;
import static edu.ku.brc.specify.tests.DataBuilder.createCollectingEvent;
import static edu.ku.brc.specify.tests.DataBuilder.createCollectingEventAttr;
import static edu.ku.brc.specify.tests.DataBuilder.createCollectingTrip;
import static edu.ku.brc.specify.tests.DataBuilder.createCollectionObjDef;
import static edu.ku.brc.specify.tests.DataBuilder.createCollectionObject;
import static edu.ku.brc.specify.tests.DataBuilder.createCollectionObjectAttr;
import static edu.ku.brc.specify.tests.DataBuilder.createCollector;
import static edu.ku.brc.specify.tests.DataBuilder.createDataType;
import static edu.ku.brc.specify.tests.DataBuilder.createDetermination;
import static edu.ku.brc.specify.tests.DataBuilder.createDeterminationStatus;
import static edu.ku.brc.specify.tests.DataBuilder.createGeography;
import static edu.ku.brc.specify.tests.DataBuilder.createGeographyChildren;
import static edu.ku.brc.specify.tests.DataBuilder.createGeographyTreeDef;
import static edu.ku.brc.specify.tests.DataBuilder.createGeographyTreeDefItem;
import static edu.ku.brc.specify.tests.DataBuilder.createGeologicTimePeriod;
import static edu.ku.brc.specify.tests.DataBuilder.createGeologicTimePeriodTreeDef;
import static edu.ku.brc.specify.tests.DataBuilder.createGeologicTimePeriodTreeDefItem;
import static edu.ku.brc.specify.tests.DataBuilder.createLocality;
import static edu.ku.brc.specify.tests.DataBuilder.createLocation;
import static edu.ku.brc.specify.tests.DataBuilder.createLocationTreeDef;
import static edu.ku.brc.specify.tests.DataBuilder.createLocationTreeDefItem;
import static edu.ku.brc.specify.tests.DataBuilder.createPermit;
import static edu.ku.brc.specify.tests.DataBuilder.createPrepType;
import static edu.ku.brc.specify.tests.DataBuilder.createPreparation;
import static edu.ku.brc.specify.tests.DataBuilder.createSpecifyUser;
import static edu.ku.brc.specify.tests.DataBuilder.createTaxon;
import static edu.ku.brc.specify.tests.DataBuilder.createTaxonChildren;
import static edu.ku.brc.specify.tests.DataBuilder.createTaxonTreeDef;
import static edu.ku.brc.specify.tests.DataBuilder.createTaxonTreeDefItem;
import static edu.ku.brc.specify.tests.DataBuilder.createUserGroup;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.ku.brc.dbsupport.AttributeIFace;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.specify.conversion.BasicSQLUtils;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.AccessionAgents;
import edu.ku.brc.specify.datamodel.Address;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.AttributeDef;
import edu.ku.brc.specify.datamodel.CatalogSeries;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectingEventAttr;
import edu.ku.brc.specify.datamodel.CollectingTrip;
import edu.ku.brc.specify.datamodel.CollectionObjDef;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.CollectionObjectAttr;
import edu.ku.brc.specify.datamodel.Collectors;
import edu.ku.brc.specify.datamodel.DataType;
import edu.ku.brc.specify.datamodel.Determination;
import edu.ku.brc.specify.datamodel.DeterminationStatus;
import edu.ku.brc.specify.datamodel.Geography;
import edu.ku.brc.specify.datamodel.GeographyTreeDef;
import edu.ku.brc.specify.datamodel.GeographyTreeDefItem;
import edu.ku.brc.specify.datamodel.GeologicTimePeriod;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDefItem;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.specify.datamodel.Location;
import edu.ku.brc.specify.datamodel.LocationTreeDef;
import edu.ku.brc.specify.datamodel.LocationTreeDefItem;
import edu.ku.brc.specify.datamodel.Permit;
import edu.ku.brc.specify.datamodel.PrepType;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;
import edu.ku.brc.specify.datamodel.TaxonTreeDefItem;
import edu.ku.brc.specify.datamodel.UserGroup;
import edu.ku.brc.specify.tools.SpecifySchemaGenerator;
import edu.ku.brc.ui.UIHelper;

/**
 * 
 * @code_status Alpha
 * @author jstewart
 */
public class BuildSampleDatabase
{
    private static final Logger log      = Logger.getLogger(BuildSampleDatabase.class);
    protected static Calendar   calendar = Calendar.getInstance();
    protected static Session session;

    public static Session getSession()
    {
        return session;
    }

    public static void setSession(Session s)
    {
        session = s;
    }
    
    public static List<Object> createSingleDiscipline(final String colObjDefName, final String disciplineName)
    {
        log.info("Creating single discipline database: " + disciplineName);

        Vector<Object> dataObjects = new Vector<Object>();

        // Create the really high-level stuff
        UserGroup userGroup = createUserGroup(disciplineName);
        SpecifyUser user = createSpecifyUser("rods", "rods@ku.edu", (short) 0, userGroup, "CollectionManager");
        DataType dataType = createDataType(disciplineName);
        TaxonTreeDef taxonTreeDef = createTaxonTreeDef("Sample Taxon Tree Def");
        CollectionObjDef collectionObjDef = createCollectionObjDef(colObjDefName, disciplineName, dataType, user,
                taxonTreeDef);

        dataObjects.add(collectionObjDef);
        dataObjects.add(userGroup);
        dataObjects.add(user);
        dataObjects.add(dataType);
        dataObjects.add(taxonTreeDef);
        
        // build the trees
        List<Object> taxa = createSimpleTaxon(collectionObjDef.getTaxonTreeDef());
        List<Object> geos = createSimpleGeography(collectionObjDef, "Geography");
        List<Object> locs = createSimpleLocation(collectionObjDef, "Location");
        List<Object> gtps = createSimpleGeologicTimePeriod(collectionObjDef, "Geologic Time Period");

        dataObjects.addAll(taxa);
        dataObjects.addAll(geos);
        dataObjects.addAll(locs);
        dataObjects.addAll(gtps);
        
        // create some localities
        log.info("Creating localities");
        Locality forestStream = createLocality("Unnamed forest stream", (Geography)geos.get(13));
        Locality lake   = createLocality("Deep, dark lake", (Geography)geos.get(18));
        Locality farmpond = createLocality("Farm pond", (Geography)geos.get(21));
        dataObjects.add(forestStream);
        dataObjects.add(lake);
        dataObjects.add(farmpond);
        
        // agents and addresses
        log.info("Creating agents and addresses");
        List<Agent> agents = new Vector<Agent>();
        agents.add(createAgent("Mr.", "Joshua", "D", "Stewart", "js"));
        agents.add(createAgent("Mr.", "James", "H", "Beach", "jb"));
        agents.add(createAgent("Mrs.", "Mary Margaret", "H", "Kumin", "mk"));
        agents.add(createAgent("Mr.", "Rodney", "C", "Spears", "rs"));
        Agent ku = new Agent();
        ku.initialize();
        ku.setAbbreviation("KU");
        ku.setAgentType(Agent.ORG);
        ku.setName("University of Kansas");
        ku.setEmail("webadmin@ku.edu");
        agents.add(ku);
        agents.get(0).setOrganization(ku);
        agents.get(1).setOrganization(ku);
        agents.get(2).setOrganization(ku);
        agents.get(3).setOrganization(ku);

        List<Address> addrs = new Vector<Address>();
        addrs.add(createAddress(agents.get(0), "11911 S Redbud Ln", null, "Olathe", "KS", "USA", "66061"));
        agents.get(0).setEmail("jds@ku.edu");
        addrs.add(createAddress(agents.get(1), "??? Mississippi", null, "Lawrence", "KS", "USA", "66045"));
        agents.get(0).setEmail("beach@ku.edu");
        addrs.add(createAddress(agents.get(2), "1 Main St", "", "Lenexa", "KS", "USA", "66071"));
        addrs.add(createAddress(agents.get(3), "1335511 Inverness", null, "Lawrence", "KS", "USA", "66047"));
        addrs.add(createAddress(ku, null, null, "Lawrence", "KS", "USA", "66045"));
        
        dataObjects.addAll(agents);
        dataObjects.addAll(addrs);
        
        // collecting events (collectors, collecting trip)
        Collectors collectorJosh = createCollector(agents.get(0), 2);
        Collectors collectorJim = createCollector(agents.get(1), 1);
        CollectingEvent ce1 = createCollectingEvent(forestStream, new Collectors[]{collectorJosh,collectorJim});
        calendar.set(1993, 3, 19, 11, 56, 00);
        ce1.setStartDate(calendar);
        ce1.setStartDateVerbatim("19 Mar 1993, 11:56 AM");
        calendar.set(1993, 3, 19, 13, 03, 00);
        ce1.setEndDate(calendar);
        ce1.setEndDateVerbatim("19 Mar 1993, 1:03 PM");
        
        AttributeDef cevAttrDef = createAttributeDef(AttributeIFace.FieldType.StringType, "ParkName", null);
        CollectingEventAttr cevAttr = createCollectingEventAttr(ce1, cevAttrDef, "Sleepy Hollow", null);

        Collectors collectorMeg = createCollector(agents.get(2), 1);
        Collectors collectorRod = createCollector(agents.get(3), 2);
        CollectingEvent ce2 = createCollectingEvent(farmpond, new Collectors[]{collectorMeg,collectorRod});
        calendar.set(1993, 3, 20, 06, 12, 00);
        ce2.setStartDate(calendar);
        ce2.setStartDateVerbatim("20 Mar 1993, 6:12 AM");
        calendar.set(1993, 3, 20, 07, 31, 00);
        ce2.setEndDate(calendar);
        ce2.setEndDateVerbatim("20 Mar 1993, 7:31 AM");

        CollectingTrip trip = createCollectingTrip("Sample collecting trip", new CollectingEvent[]{ce1,ce2});
        
        dataObjects.add(collectorJosh);
        dataObjects.add(collectorJim);
        dataObjects.add(collectorMeg);
        dataObjects.add(collectorRod);
        dataObjects.add(ce1);
        dataObjects.add(ce2);
        dataObjects.add(trip);
        dataObjects.add(cevAttrDef);
        dataObjects.add(cevAttr);
        
        // permit
        Calendar issuedDate = Calendar.getInstance();
        issuedDate.set(1993, 1, 12);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1993, 2, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(1993, 5, 30);
        Permit permit = createPermit("1993-FISH-0001", "US Dept Fish and Wildlife", issuedDate, startDate, endDate, null);
        permit.setAgentByIssuee(ku);
        dataObjects.add(permit);
        
        // catalog series
        CatalogSeries catalogSeries = createCatalogSeries("KUFSH", "Fish", collectionObjDef);
        dataObjects.add(catalogSeries);

        // collection objects
        Object[]  values = {1001010.1f, "RCS101", agents.get(0), 5,  ce1,
                            1101011.1f, "RCS102", agents.get(0), 20, ce1,
                            1201012.1f, "RCS103", agents.get(1), 15, ce1,
                            1301013.1f, "RCS104", agents.get(1), 25, ce1,
                            1401014.1f, "RCS105", agents.get(2), 35, ce2,
                            1501015.1f, "RCS106", agents.get(2), 45, ce2,
                            1601016.1f, "RCS107", agents.get(2), 55, ce2,
                            1701017.1f, "RCS108", agents.get(3), 65, ce2};
        List<CollectionObject> collObjs = new Vector<CollectionObject>();
        for (int i=0;i<values.length;i+=5)
        {
            collObjs.add(createCollectionObject((Float)values[i],
                                               (String)values[i+1],
                                               null,
                                               (Agent)values[i+2],
                                               catalogSeries,
                                               collectionObjDef,
                                               (Integer)values[i+3],
                                               (CollectingEvent)values[i+4]));
        }
        AttributeDef colObjAttrDef = createAttributeDef(AttributeIFace.FieldType.StringType, "MoonPhase", null);
        CollectionObjectAttr colObjAttr = createCollectionObjectAttr(collObjs.get(0), colObjAttrDef, "Full", null);
        dataObjects.addAll(collObjs);
        dataObjects.add(colObjAttrDef);
        dataObjects.add(colObjAttr);
        
        // determinations (determination status)
        DeterminationStatus current = createDeterminationStatus("Current","Test Status");
        DeterminationStatus notCurrent = createDeterminationStatus("Not current","Test Status");
        DeterminationStatus incorrect = createDeterminationStatus("Incorrect","Test Status");

        List<Determination> determs = new Vector<Determination>();
        Calendar recent = Calendar.getInstance();
        recent.set(2006, 10, 27, 13, 44, 00);
        Calendar longAgo = Calendar.getInstance();
        longAgo.set(1976, 01, 29, 8, 12, 00);
        Calendar whileBack = Calendar.getInstance();
        whileBack.set(2002, 7, 4, 9, 33, 12);
        determs.add(createDetermination(collObjs.get(0), agents.get(0), (Taxon)taxa.get( 8), current, recent));
        determs.add(createDetermination(collObjs.get(1), agents.get(0), (Taxon)taxa.get( 9), current, recent));
        determs.add(createDetermination(collObjs.get(2), agents.get(0), (Taxon)taxa.get(10), current, recent));
        determs.add(createDetermination(collObjs.get(3), agents.get(0), (Taxon)taxa.get(11), current, recent));
        determs.add(createDetermination(collObjs.get(4), agents.get(0), (Taxon)taxa.get(12), current, recent));
        determs.add(createDetermination(collObjs.get(5), agents.get(0), (Taxon)taxa.get(13), current, recent));
        determs.add(createDetermination(collObjs.get(6), agents.get(0), (Taxon)taxa.get(14), current, recent));
        determs.add(createDetermination(collObjs.get(7), agents.get(0), (Taxon)taxa.get(15), current, recent));
        
        determs.add(createDetermination(collObjs.get(0), agents.get(0), (Taxon)taxa.get(8), notCurrent, longAgo));
        determs.add(createDetermination(collObjs.get(1), agents.get(1), (Taxon)taxa.get(15), notCurrent, whileBack));
        determs.add(createDetermination(collObjs.get(2), agents.get(1), (Taxon)taxa.get(16), notCurrent, whileBack));
        determs.add(createDetermination(collObjs.get(3), agents.get(2), (Taxon)taxa.get(17), notCurrent, whileBack));
        determs.add(createDetermination(collObjs.get(4), agents.get(2), (Taxon)taxa.get(17), notCurrent, whileBack));
        determs.add(createDetermination(collObjs.get(4), agents.get(3), (Taxon)taxa.get(20), incorrect, longAgo));
        determs.get(13).setRemarks("This determination is totally wrong.  What a foolish determination.");
        
        dataObjects.add(current);
        dataObjects.add(notCurrent);
        dataObjects.add(incorrect);
        dataObjects.addAll(determs);
        
        // preparations (prep types)
        PrepType skel = createPrepType("Skeleton");
        PrepType cs = createPrepType("C&S");
        PrepType etoh = createPrepType("EtOH");

        List<Preparation> preps = new Vector<Preparation>();
        preps.add(createPreparation(etoh, agents.get(0), collObjs.get(0), (Location)locs.get(8), 1));
        preps.add(createPreparation(etoh, agents.get(0), collObjs.get(1), (Location)locs.get(8), 1));
        preps.add(createPreparation(etoh, agents.get(1), collObjs.get(2), (Location)locs.get(8), 1));
        preps.add(createPreparation(etoh, agents.get(1), collObjs.get(3), (Location)locs.get(8), 1));
        preps.add(createPreparation(etoh, agents.get(2), collObjs.get(4), (Location)locs.get(9), 1));
        preps.add(createPreparation(etoh, agents.get(2), collObjs.get(5), (Location)locs.get(9), 1));
        preps.add(createPreparation(etoh, agents.get(3), collObjs.get(6), (Location)locs.get(9), 1));
        preps.add(createPreparation(etoh, agents.get(3), collObjs.get(7), (Location)locs.get(9), 1));
        preps.add(createPreparation(skel, agents.get(1), collObjs.get(0), (Location)locs.get(12), 1));
        preps.add(createPreparation(skel, agents.get(1), collObjs.get(1), (Location)locs.get(12), 1));
        preps.add(createPreparation(skel, agents.get(1), collObjs.get(2), (Location)locs.get(11), 1));
        preps.add(createPreparation(skel, agents.get(2), collObjs.get(3), (Location)locs.get(10), 1));
        preps.add(createPreparation(skel, agents.get(3), collObjs.get(4), (Location)locs.get(10), 1));
        preps.add(createPreparation(skel, agents.get(0), collObjs.get(5), (Location)locs.get(10), 1));
        preps.add(createPreparation(cs, agents.get(1), collObjs.get(6), (Location)locs.get(10), 1));
        preps.add(createPreparation(cs, agents.get(1), collObjs.get(7), (Location)locs.get(10), 1));
        preps.add(createPreparation(cs, agents.get(1), collObjs.get(2), (Location)locs.get(9), 1));

        dataObjects.add(skel);
        dataObjects.add(cs);
        dataObjects.add(etoh);
        dataObjects.addAll(preps);
        
        // accessions (accession agents, accession authorizations)
        calendar.set(2006, 10, 27, 23, 59, 59);
        Accession acc1 = createAccession("Gift", "Complete", "2006-EN-0001", DateFormat.getInstance().format(calendar.getTime()), calendar, calendar);
        
        Agent donor =    agents.get(0);
        Agent receiver = agents.get(1);
        Agent reviewer = agents.get(2);
        
        List<AccessionAgents> accAgents = new Vector<AccessionAgents>();
        
        accAgents.add(createAccessionAgent("Donor", donor, acc1, null));
        accAgents.add(createAccessionAgent("Receiver", receiver, acc1, null));
        accAgents.add(createAccessionAgent("Reviewer", reviewer, acc1, null));

        Accession acc2 = createAccession("Field Work", "In Process", "2006-IC-0001", DateFormat.getInstance().format(calendar.getTime()), calendar, calendar);
        
        Agent donor2 =    agents.get(2);
        Agent receiver2 = agents.get(3);
        Agent reviewer2 = agents.get(1);
        
        accAgents.add(createAccessionAgent("Donor", donor2, acc2, null));
        accAgents.add(createAccessionAgent("Receiver", receiver2, acc2, null));
        accAgents.add(createAccessionAgent("Reviewer", reviewer2, acc2, null));

        dataObjects.add(acc1);
        dataObjects.add(acc2);
        dataObjects.addAll(accAgents);
        
        // done
        log.info("Done creating single discipline database: " + disciplineName);
        return dataObjects;
    }

    public static List<Object> createSimpleGeography(final CollectionObjDef colObjDef, final String treeDefName)
    {
        log.info("createSimpleGeography " + treeDefName);

        List<Object> newObjs = new Vector<Object>();

        // Create a geography tree definition (and tie it to the CollectionObjDef)
        GeographyTreeDef geoTreeDef = createGeographyTreeDef(treeDefName);
        geoTreeDef.getCollObjDefs().add(colObjDef);
        colObjDef.setGeographyTreeDef(geoTreeDef);
        // 0
        newObjs.add(geoTreeDef);
        
        // create the geo tree def items
        GeographyTreeDefItem root = createGeographyTreeDefItem(null, geoTreeDef, "GeoRoot", 0);
        GeographyTreeDefItem cont = createGeographyTreeDefItem(root, geoTreeDef, "Continent", 100);
        GeographyTreeDefItem country = createGeographyTreeDefItem(cont, geoTreeDef, "Country", 200);
        GeographyTreeDefItem state = createGeographyTreeDefItem(country, geoTreeDef, "State", 300);
        GeographyTreeDefItem county = createGeographyTreeDefItem(state, geoTreeDef, "County", 400);
        // 1
        newObjs.add(root);
        // 2
        newObjs.add(cont);
        // 3
        newObjs.add(country);
        // 4
        newObjs.add(state);
        // 5
        newObjs.add(county);

        // Create the planet Earth.
        // That seems like a big task for 5 lines of code.
        Geography earth = createGeography(geoTreeDef, null, "Earth", root.getRankId());
        Geography northAmerica = createGeography(geoTreeDef, earth, "North America", cont.getRankId());
        Geography us = createGeography(geoTreeDef, northAmerica, "United States", country.getRankId());
        List<Geography> states = createGeographyChildren(geoTreeDef, northAmerica,
                new String[] { "Kansas", "Iowa", "Nebraska" }, state.getRankId());
        // 6
        newObjs.add(earth);
        // 7
        newObjs.add(northAmerica);
        // 8
        newObjs.add(us);
        // 9, 10, 11
        newObjs.addAll(states);

        
        
        // Create Kansas and a few counties
        List<Geography> counties = createGeographyChildren(geoTreeDef, states.get(0),
                new String[] { "Douglas", "Johnson", "Osage", "Sedgwick" }, county.getRankId());
        // 12, 13, 14, 15
        newObjs.addAll(counties);
        counties = createGeographyChildren(geoTreeDef, states.get(1),
                new String[] { "Blackhawk", "Fayette", "Polk", "Woodbury" }, county.getRankId());
        // 16, 17, 18, 19
        newObjs.addAll(counties);
        counties = createGeographyChildren(geoTreeDef, states.get(2),
                new String[] { "Dakota", "Logan", "Valley", "Wheeler" }, county.getRankId());
        // 20, 21, 22, 23
        newObjs.addAll(counties);

        return newObjs;
    }

    public static List<Object> createSimpleGeologicTimePeriod(final CollectionObjDef colObjDef,
                                                              final String treeDefName)
    {
        log.info("createSimpleGeologicTimePeriod " + treeDefName);

        List<Object> newObjs = new Vector<Object>();

        // Create a geography tree definition
        GeologicTimePeriodTreeDef treeDef = createGeologicTimePeriodTreeDef(treeDefName);
        treeDef.getCollObjDefs().add(colObjDef);
        colObjDef.setGeologicTimePeriodTreeDef(treeDef);
        newObjs.add(treeDef);

        GeologicTimePeriodTreeDefItem defItemLevel0 = createGeologicTimePeriodTreeDefItem(
                null, treeDef, "Level 0", 0);
        GeologicTimePeriodTreeDefItem defItemLevel1 = createGeologicTimePeriodTreeDefItem(
                defItemLevel0, treeDef, "Level 1", 100);
        GeologicTimePeriodTreeDefItem defItemLevel2 = createGeologicTimePeriodTreeDefItem(
                defItemLevel1, treeDef, "Level 2", 200);
        GeologicTimePeriodTreeDefItem defItemLevel3 = createGeologicTimePeriodTreeDefItem(
                defItemLevel2, treeDef, "Level 3", 300);
        newObjs.add(defItemLevel0);
        newObjs.add(defItemLevel1);
        newObjs.add(defItemLevel2);
        newObjs.add(defItemLevel3);

        // Create the defItemLevel0
        GeologicTimePeriod level0 = createGeologicTimePeriod(treeDef, null,
                "Time As We Know It", 10.0f, 0.0f, defItemLevel0.getRankId());
        GeologicTimePeriod level1 = createGeologicTimePeriod(treeDef, level0,
                "Some Really Big Time Period", 5.0f, 0.0f, defItemLevel0.getRankId());
        GeologicTimePeriod level2 = createGeologicTimePeriod(treeDef, level1,
                "A Slightly Smaller Time Period", 1.74f, 0.0f, defItemLevel0.getRankId());
        GeologicTimePeriod level3_1 = createGeologicTimePeriod(treeDef, level2,
                "Yesterday", 0.1f, 0.0f, defItemLevel0.getRankId());
        GeologicTimePeriod level3_2 = createGeologicTimePeriod(treeDef, level2,
                "A couple of days ago", 0.2f, 0.1f, defItemLevel0.getRankId());
        GeologicTimePeriod level3_3 = createGeologicTimePeriod(treeDef, level2,
                "Last week", 0.7f, 1.4f, defItemLevel0.getRankId());
        newObjs.add(level0);
        newObjs.add(level1);
        newObjs.add(level2);
        newObjs.add(level3_1);
        newObjs.add(level3_2);
        newObjs.add(level3_3);

        return newObjs;
    }

    public static List<Object> createSimpleLocation(final CollectionObjDef colObjDef, final String treeDefName)
    {
        log.info("createSimpleLocation " + treeDefName);

        List<Object> newObjs = new Vector<Object>();

        // Create a geography tree definition
        LocationTreeDef locTreeDef = createLocationTreeDef(treeDefName);
        locTreeDef.getCollObjDefs().add(colObjDef);
        colObjDef.setLocationTreeDef(locTreeDef);

        LocationTreeDefItem building = createLocationTreeDefItem(null, locTreeDef, "building", 0);
        building.setIsEnforced(true);
        LocationTreeDefItem room = createLocationTreeDefItem(building, locTreeDef, "room", 100);
        room.setIsInFullName(true);
        LocationTreeDefItem freezer = createLocationTreeDefItem(room, locTreeDef, "freezer", 200);
        freezer.setIsInFullName(true);
        LocationTreeDefItem shelf = createLocationTreeDefItem(freezer, locTreeDef, "shelf", 300);
        shelf.setIsInFullName(true);

        // Create the building
        Location dyche = createLocation(locTreeDef, null, "Dyche Hall", building.getRankId());
        Location rm606 = createLocation(locTreeDef, dyche, "Room 606", room.getRankId());
        Location freezerA = createLocation(locTreeDef, rm606, "Freezer A", freezer.getRankId());
        Location shelf5 = createLocation(locTreeDef, freezerA, "Shelf 5", shelf.getRankId());
        Location shelf4 = createLocation(locTreeDef, freezerA, "Shelf 4", shelf.getRankId());
        Location shelf3 = createLocation(locTreeDef, freezerA, "Shelf 3", shelf.getRankId());
        Location shelf2 = createLocation(locTreeDef, freezerA, "Shelf 2", shelf.getRankId());
        Location shelf1 = createLocation(locTreeDef, freezerA, "Shelf 1", shelf.getRankId());

        // 0
        newObjs.add(locTreeDef);
        // 1
        newObjs.add(building);
        // 2
        newObjs.add(room);
        // 3
        newObjs.add(freezer);
        // 4
        newObjs.add(shelf);
        // 5
        newObjs.add(dyche);
        // 6
        newObjs.add(rm606);
        // 7
        newObjs.add(freezerA);
        // 8
        newObjs.add(shelf5);
        // 9
        newObjs.add(shelf4);
        // 10
        newObjs.add(shelf3);
        // 11
        newObjs.add(shelf2);
        // 12
        newObjs.add(shelf1);
        
        return newObjs;
    }


    public static List<Object> createSimpleTaxon(final TaxonTreeDef taxonTreeDef)
    {
        log.info("createSimpleTaxon " + taxonTreeDef.getName());

        Vector<Object> newObjs = new Vector<Object>();
        // Create a Taxon tree definition
        TaxonTreeDefItem defItemLevel0 = createTaxonTreeDefItem(null, taxonTreeDef, "order", 0);
        defItemLevel0.setIsEnforced(true);
        TaxonTreeDefItem defItemLevel1 = createTaxonTreeDefItem(defItemLevel0, taxonTreeDef, "family", 100);
        TaxonTreeDefItem defItemLevel2 = createTaxonTreeDefItem(defItemLevel1, taxonTreeDef, "genus", 200);
        defItemLevel2.setIsEnforced(true);
        defItemLevel2.setIsInFullName(true);
        TaxonTreeDefItem defItemLevel3 = createTaxonTreeDefItem(defItemLevel2, taxonTreeDef, "species", 300);
        defItemLevel3.setIsEnforced(true);
        defItemLevel3.setIsInFullName(true);

        // 0
        newObjs.add(defItemLevel0);
        // 1
        newObjs.add(defItemLevel1);
        // 2
        newObjs.add(defItemLevel2);
        // 3
        newObjs.add(defItemLevel3);

        // Create the defItemLevel0
        Taxon order = createTaxon(taxonTreeDef, null, "Percidae", defItemLevel0.getRankId());
        Taxon family = createTaxon(taxonTreeDef, order, "Perciformes", defItemLevel1.getRankId());
        Taxon genus = createTaxon(taxonTreeDef, family, "Ammocrypta", defItemLevel2.getRankId());
        // 4
        newObjs.add(order);
        // 5
        newObjs.add(family);
        // 6
        newObjs.add(genus);

        String[] speciesNames = { "asprella", "beanii", "bifascia", "clara", "meridiana", "pellucida", "vivax" };
        List<Object> kids = createTaxonChildren(taxonTreeDef, genus, speciesNames, defItemLevel3.getRankId());
        // 7, 8, 9, 10, 11, 12, 13
        newObjs.addAll(kids);

        genus = createTaxon(taxonTreeDef, order, "Caranx", defItemLevel2.getRankId());
        // 14
        newObjs.add(genus);
        String[] speciesNames2 = { "bartholomaei", "caballus", "caninus", "crysos", "dentex", "hippos", "latus" };
        kids = createTaxonChildren(taxonTreeDef, genus, speciesNames2, defItemLevel3.getRankId());
        // 15, 16, 17, 18, 19, 20, 21
        newObjs.addAll(kids);
        
        return newObjs;
    }


    public static void persist(Object o)
    {
        if (session != null)
        {
            session.persist(o);
        }
    }


    public static void persist(Object[] oArray)
    {
        for (Object o: oArray)
        {
            persist(o);
        }
    }


    public static void persist(List<Object> oList)
    {
        for (Object o: oList)
        {
            persist(o);
        }
    }


    public static void startTx()
    {
        HibernateUtil.beginTransaction();
    }


    public static void commitTx()
    {
        HibernateUtil.commitTransaction();
    }
    

    public static void rollbackTx()
    {
        HibernateUtil.rollbackTransaction();
    }
    

    public static void main(String[] args) throws Exception
    {
        SpecifySchemaGenerator schemaGen = new SpecifySchemaGenerator();
        String databaseName = "testfish";
        String databaseHost = "localhost";
        schemaGen.generateSchema(databaseHost, databaseName);

        String userName = "rods";
        String password = "rods";

        if (UIHelper.tryLogin("com.mysql.jdbc.Driver",
                                "org.hibernate.dialect.MySQLDialect",
                                databaseName,
                                "jdbc:mysql://" + databaseHost + "/" + databaseName,
                                userName,
                                password))
        {
            BasicSQLUtils.cleanAllTables();
            setSession(HibernateUtil.getCurrentSession());
            boolean single = true;
            if (single)
            {
                try
                {
                    startTx();
                    List<Object> dataObjects = createSingleDiscipline("Fish", "fish");

                    
                    // persist the first data object (the CollectionObjDef)
                    
                    // in the final version, this should be enough to do it all
                    //persist(dataObjects.get(0));
                    
                    // but for now
                    for (Object o: dataObjects)
                    {
                        persist(o);
                    }
                    commitTx();
                }
                catch(Exception e)
                {
                    try
                    {
                        rollbackTx();
                        log.error("Failed to persist DB objects", e);
                    }
                    catch(Exception e2)
                    {
                        log.error("Failed to persist DB objects.  Rollback failed.  DB may be in inconsistent state", e2);
                    }
                }
            }
        }
    }
}
