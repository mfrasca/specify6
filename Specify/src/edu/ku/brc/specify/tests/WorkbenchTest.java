/*
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package edu.ku.brc.specify.tests;

import static edu.ku.brc.specify.tests.HibernateHelper.stopHibernateTransaction;
import static edu.ku.brc.specify.tests.HibernateHelper.startHibernateTransaction;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.specify.datamodel.Workbench;
import edu.ku.brc.specify.datamodel.WorkbenchTemplate;
import edu.ku.brc.specify.datamodel.WorkbenchTemplateMappingItem;

/*
 * @code_status Unknown (auto-generated) * @author megkumin
 * 
 */
public class WorkbenchTest extends TestCase
{
    private static final Logger log = Logger.getLogger(WorkbenchTest.class);

    public static void main(String[] args)
    {

    }

    /**
     * @param arg0
     */
    public WorkbenchTest(String arg0)
    {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        // super.setUp();
        super.setUp();
        AppPreferenceHelper.setupPreferences();
        startHibernateTransaction();
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        // super.tearDown();
        super.tearDown();
        stopHibernateTransaction();
    }

    /**
     * 
     */
    public void testCreateWorkbenchTemplate()
    {
        log.info("Testing a simple WorkbenchTemplate");
        int templateId = -1;
        try
        {
            log.info("creating the WorkbenchTemplate");
            WorkbenchTemplate testtemplate = ObjCreatorHelper.createWorkbenchTemplate("Simple Template for Junit",
                    "This is a template for testing the application thru JUNIT");
            templateId = testtemplate.getWorkbenchTemplateId().intValue();
            assertNotNull(testtemplate);
            log.info("created WorkbenchTemplate with ID:" + templateId);

        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }
        log.info("checking if the WorkbenchTemplate exists in the database ID:" + templateId);
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));
    }

    /**
     * 
     */
    public void testCreationAndDeletionOfWorkbenchTemplate()
    {
        log.info("Testing the creation, insertion and deletion of a WorkbenchTemplate");
        int templateId = -1;
        try
        {
            log.info("creating the WorkbenchTemplate");
            WorkbenchTemplate template = ObjCreatorHelper.createWorkbenchTemplate("Deleted Template for Junit",
                    "This template should be deleted during JUNIT test");
            templateId = template.getWorkbenchTemplateId().intValue();
            assertNotNull(template);
            log.info("created WorkbenchTemplate with ID:" + templateId);

        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }

        log.info("checking if the WorkbenchTemplate exists in the database ID:" + templateId);
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));

        log.info("deleting the WorkbenchTemplate from the database ID:" + templateId);
        assertTrue(WorkbenchTestHelper.deleteTemplateFromDB(templateId));
        log.info("checking if the WorkbenchTemplate exists in the database ID:" + templateId);
        assertFalse(WorkbenchTestHelper.isTemplateInDB(templateId));
    }

    /**
     * 
     */
    public void testCreateSimpleWorkbenchTemplateMapping()
    {
        log.info("Testing Simple WorkbenchTemplateMappingItem");
        int mappingItemId = -1;
        try
        {
            WorkbenchTemplate template = (WorkbenchTemplate) CreateTestDatabases.getDBObject(WorkbenchTemplate.class);

            // template should already exist from first JUNit test
            assertNotNull(template);
            WorkbenchTemplateMappingItem item = ObjCreatorHelper.createMappingItem("SimpleJunitTable", 1,
                    "SimpleJunitField", "SimpleJunitCaption", "SimpleJunitDataType", 0, template);
            mappingItemId = item.getWorkbenchTemplateMappingItemId().intValue();
            assertNotNull(item);
            log.info("created WorkbenchTemplateMappingItem with ID:" + mappingItemId);

        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }
    }

    /**
     * 
     */
    public void testCreationAndDeletionOfWorkbenchTemplateMapping()
    {
        log.info("Testing the creation, insertion and deletion of a WorkbenchTemplateMappingItem");
        int mappingItemId = -1;
        int templateId = -1;
        try
        {
            WorkbenchTemplate template = (WorkbenchTemplate) CreateTestDatabases.getDBObject(WorkbenchTemplate.class);
            templateId = template.getWorkbenchTemplateId().intValue();
            // template should already exist from first JUNit test
            assertNotNull(template);
            WorkbenchTemplateMappingItem item = ObjCreatorHelper.createMappingItem("DELETEME", 1, "DELETEME", "DELETEME",
                    "DELETEME", 0, template);
            mappingItemId = item.getWorkbenchTemplateMappingItemId().intValue();
            assertNotNull(item);
            log.info("created WorkbenchTemplateMappingItem with ID:" + mappingItemId);
        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }
        log.info("checking that the parent exists in the database");
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));
        log.info("checking if the WorkbenchTemplateMappingItem exists in the database");
        assertTrue(WorkbenchTestHelper.isTemplateMappingItemInDB(mappingItemId));

        log.info("deleting the WorkbenchTemplateMappingItem from the database");
        assertTrue(WorkbenchTestHelper.deleteMappingItemFromDB(mappingItemId));
        log.info("checking if the WorkbenchTemplateMappingItem was deleted from the database");
        assertFalse(WorkbenchTestHelper.isTemplateMappingItemInDB(mappingItemId));
        log.info("checking that the parent WorkbenchTemplate still exists in the database");
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));
    }

    /**
     * 
     */
    public void testCreationAndDeletionOfWorkbenchTemplateMappingCascadeDelete()
    {
        log
                .info("Testing the creation, insertion and deletion of a WorkbenchTemplate and make sure its assocaited mappings are deleted");
        int mappingItemId = -1;
        int templateId = -1;
        try
        {
            HibernateHelper.startHibernateTransaction();

            WorkbenchTemplate template = ObjCreatorHelper.createWorkbenchTemplate("Deleted Template for Junit",
                    "This template should be deleted during JUNIT test");
            assertNotNull(template);
            templateId = template.getWorkbenchTemplateId().intValue();

            WorkbenchTemplateMappingItem item = ObjCreatorHelper.createMappingItem("DELETEME", 1, "DELETEME", "DELETEME",
                    "DELETEME", 0, template);
            assertNotNull(item);
            mappingItemId = item.getWorkbenchTemplateMappingItemId().intValue();
            log.info("created WorkbenchTemplateMappingItem with ID:" + mappingItemId);

            HibernateHelper.stopHibernateTransaction();
        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }
        log.info("checking that the parent exists in the database");
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));
        log.info("checking if the WorkbenchTemplateMappingItem exists in the database");
        assertTrue(WorkbenchTestHelper.isTemplateMappingItemInDB(mappingItemId));

        log.info("deleting the WorkbenchTemplate from the database");
        assertTrue(WorkbenchTestHelper.deleteTemplateFromDB(templateId));

        log.info("checking if the WorkbenchTemplateMappingItem was deleted from the database");
        assertFalse(WorkbenchTestHelper.isTemplateMappingItemInDB(mappingItemId));
        log.info("checking that the parent WorkbenchTemplate still exists in the database");
        assertFalse(WorkbenchTestHelper.isTemplateInDB(templateId));
    }

    /**
     * 
     */
    public void testSimpleWorkbench()
    {
        log.info("Testing a simple workbench");
        try
        {
            WorkbenchTemplate template = (WorkbenchTemplate) CreateTestDatabases.getDBObject(WorkbenchTemplate.class);
            assertNotNull(template);
            Workbench workbench = ObjCreatorHelper.createWorkbench("Workbench for Junit", "Test workbench for Junit",
                    "Instituion", 0, template);
            assertNotNull(workbench);
        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }
    }

    /**
     * 
     */
    public void testCreationAndDeletionOfWorkbench()
    {
        log.info("Testing the creation, insertion and deletion of a Workbench");
        int templateId = -1;
        int workbenchId = -1;
        try
        {
            log.info("creating the WorkbenchTemplate");
            WorkbenchTemplate template = ObjCreatorHelper.createWorkbenchTemplate("Template for Junit",
                    "This template used JUNIT test");
            templateId = template.getWorkbenchTemplateId().intValue();
            assertNotNull(template);

            log.info("creating the Workbench");
            Workbench workbench = ObjCreatorHelper.createWorkbench("DELETEME", "DELETEME", "DELETEME", 0, template);
            workbenchId = workbench.getWorkbenchId().intValue();
            log.info("created Workbench with ID:" + workbenchId);
        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }

        log.info("checking if the WorkbenchTemplate exists in the database ID:" + templateId);
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));

        log.info("checking if the Workbenche exists in the database ID:" + workbenchId);
        assertTrue(WorkbenchTestHelper.isWorkbenchInDB(workbenchId));
        log.info("deleting the Workbench from the database ID:" + workbenchId);
        assertTrue(WorkbenchTestHelper.deleteWorkbenchFromDB(workbenchId));
        log.info("checking if the Workbench is deleted from the database ID:" + workbenchId);
        assertFalse(WorkbenchTestHelper.isWorkbenchInDB(workbenchId));

        log.info("checking if the WorkbenchTemplate still exists in the database ID:" + templateId);
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));
    }

    /**
     * 
     */
    public void testDBFullWorkbenchSet()
    {
        log.info("Testing a full workbench set");
        try
        {
            WorkbenchTemplate template = ObjCreatorHelper.createWorkbenchTemplate("TemplateABC for Junit",
                    "This is a templateABC for testing the application thru JUNIT");
            assertNotNull(template);

            String[][] mappingvalues = { { "JunittableA", "0", "JunitfieldA", "JunitcaptionsA", "0", "String" },
                    { "JunittableB", "1", "JunitfieldB", "JunitcaptionsB", "1", "String" }, };
            CreateTestWorkbenches.addMappingItemsToTemplate(template, mappingvalues);

            Workbench workbench = ObjCreatorHelper.createWorkbench("WorkbenchABC for Junit", "Test workbenchABC for Junit",
                    "InstituionABC", 0, template);
            assertNotNull(workbench);

            String[][] workbenchdata = { { "data", "data", "data", "data", "data", "data", "data", "data" },
                    { "data", "data", "data", "data", "data", "data", "data", "data" },
                    { "data", "data", "data", "data", "data", "data", "data", "data" }, };
            CreateTestWorkbenches.addDataToWorkbench(workbench, workbenchdata);
        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }
    }

    /**
     * 
     */
    public void testCreationAndDeletionOfFullWorkbenchSet()
    {
        log.info("Testing the creation, insertion and deletion of a Full WorkbenchSet");
        int templateId = -1;
        int workbenchId = -1;
        try
        {
            HibernateHelper.startHibernateTransaction();
            WorkbenchTemplate template = ObjCreatorHelper.createWorkbenchTemplate("DELETEME for Junit",
                    "DELETEMEThis is a templateABC for testing the application thru JUNIT");
            assertNotNull(template);
            templateId = template.getWorkbenchTemplateId().intValue();

            String[][] mappingvalues = {
                    { "DELETEMEJunittableA", "0", "DELETEMEJunitfieldA", "DELETEMEJunitcaptionsA", "0", "DELETEMEString" },
                    { "DELETEMEJunittableB", "1", "DELETEMEJunitfieldB", "DELETEMEJunitcaptionsB", "1", "DELETEMEString" }, };
            CreateTestWorkbenches.addMappingItemsToTemplate(template, mappingvalues);

            Workbench workbench = ObjCreatorHelper.createWorkbench("DELETEMEWorkbenchABC for Junit",
                    "DELETEMETest workbenchABC for Junit", "DELETEMEInstituionABC", 0, template);
            assertNotNull(workbench);
            workbenchId = workbench.getWorkbenchId().intValue();

            String[][] workbenchdata = {
                    { "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME" },
                    { "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME" },
                    { "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME", "DELETEME" }, };
            CreateTestWorkbenches.addDataToWorkbench(workbench, workbenchdata);

            HibernateHelper.stopHibernateTransaction();
        } catch (Exception ex)
        {
            log.error("******* " + ex);
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
            assertTrue(false);
        }

        log.info("checking if the WorkbenchTemplate exists in the database ID:" + templateId);
        assertTrue(WorkbenchTestHelper.isTemplateInDB(templateId));
        log.info("checking if the Workbenche exists in the database ID:" + workbenchId);
        assertTrue(WorkbenchTestHelper.isWorkbenchInDB(workbenchId));

        log.info("deleting the Workbench from the database ID:" + workbenchId);
        assertTrue(WorkbenchTestHelper.deleteWorkbenchFromDB(workbenchId));
        log.info("checking if the Workbench is deleted from the database ID:" + workbenchId);
        assertFalse(WorkbenchTestHelper.isWorkbenchInDB(workbenchId));

        log.info("deleting the WorkbenchTemplate from the database");
        assertTrue(WorkbenchTestHelper.deleteTemplateFromDB(templateId));
        log.info("checking if the WorkbenchTemplate is deleted from the database ID:" + templateId);
        assertFalse(WorkbenchTestHelper.isTemplateInDB(templateId));
    }
}
