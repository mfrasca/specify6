/*
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.specify.tools.fielddesc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.betwixt.XMLIntrospector;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.ui.UIHelper;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Sep 4, 2007
 *
 */
public class FieldDescApp extends JFrame
{
    private static final Logger log = Logger.getLogger(FieldDescApp.class);
 
    protected static String    fileName   = "field_desc.xml";
    
    protected Vector<Table>    tables     = new Vector<Table>();
    protected Locale           currLocale = Locale.getDefault();
    
    protected JList            tablesList;
    protected JList            fieldsList;
    protected JTextArea        descText    = new JTextArea();
    protected JTextArea        tblDescText = new JTextArea();
    protected JLabel           fieldLabel  = new JLabel("            ");
    protected DefaultListModel fieldsModel = new DefaultListModel();
    protected JButton          nxtBtn;
    protected JButton          nxtEmptyBtn;
    
    protected int              lastIndex = -1;
    protected Table            prevTable = null;
    
    
    public FieldDescApp()
    {
        tables = readTableList();
        
         buildUI();
    }
    
    /**
     * @return
     */
    public static Vector<Table> readTableList()
    {
        return readTables(XMLHelper.getConfigDir(fileName));
    }
    
    /**
     * @param file
     * @return
     */
    @SuppressWarnings({ "unchecked", "unchecked" })
    public static Vector<Table> readTables(final File file)
    {
        Vector<Table> list = null;
        Hashtable<String, Boolean> hash = new Hashtable<String, Boolean>();
        
        try
        {
            if (true)
            {
                list = new Vector<Table>();
                Element root = XMLHelper.readFileToDOM4J(file);
                for (Object obj : root.selectNodes("/database/table"))
                {
                    Element tbl = (Element)obj;
                    Table table = new Table(XMLHelper.getAttr(tbl, "name", null));
                    
                    DBTableIdMgr.TableInfo ti = DBTableIdMgr.getInstance().getInfoByTableName(table.getName());
                    if (ti != null)
                    {
                        list.add(table);
                        hash.put(table.getName(), true);
                        
                        Element de = (Element)tbl.selectSingleNode("desc");
                        if (de != null)
                        {
                            String country = XMLHelper.getAttr(de, "country", null);
                            Desc desc = new Desc(de.getTextTrim(),
                                    country,
                                    XMLHelper.getAttr(de, "lang", null),
                                    XMLHelper.getAttr(de, "variant", ""));
                            table.setDesc(desc);
                        }
                        
                        for (Object fobj : tbl.selectNodes("field"))
                        {
                            Element fld = (Element)fobj;
                            
                            String name = XMLHelper.getAttr(fld, "name", null);
                            String type = XMLHelper.getAttr(fld, "type", null); 
                            
                            Field field = new Field(name, type);
                            table.getFields().add(field);
                            
                            DBTableIdMgr.FieldInfo fldInfo = null;
                            for (DBTableIdMgr.FieldInfo fi : ti.getFields())
                            {
                                if (fi.getName().equals(field.getName()))
                                {
                                    field.setName(fi.getName()); 
                                    field.setType(fi.getType()); 
                                    fldInfo = fi;
                                    break;
                                }
                            }
                            if (fldInfo == null)
                            {
                                log.error("Can't find field by name ["+field.getName()+"]");
                            }
                            
                            for (Object dobj : fld.selectNodes("desc"))
                            {
                                de = (Element)dobj;
                                
                                String country = XMLHelper.getAttr(de, "country", null);
                                Desc desc = new Desc(de.getTextTrim(),
                                        country,
                                        XMLHelper.getAttr(de, "lang", null),
                                        XMLHelper.getAttr(de, "variant", ""));
                                field.getDescs().add(desc);
                            }
                        }
                        
                        /*
                        for (DBTableIdMgr.TableRelationship rel : ti.getRelationships())
                        {
                            if (rel.getType() == DBTableIdMgr.RelationshipType.ManyToMany || rel.getType() == DBTableIdMgr.RelationshipType.ManyToOne)
                            {
                                table.getFields().add(new Field(rel.getName(), rel.getType().toString()));
                            }
                        }*/
                    } else
                    {
                     // Discarding old table.
                        log.warn("Discarding Old Table ["+table.getName()+"]");
                    }
                }
            } else
            {
                /*XStream xstream = new XStream();
                xstream.alias("table", Table.class);
                xstream.alias("desc", Desc.class);
                xstream.alias("field", Field.class);
                xstream.alias("database", Database.class);
                Database database = (Database)xstream.fromXML(new FileReader(file));
                list = database.getTables();
                */
            }
            
            // Add New Tables
            for (DBTableIdMgr.TableInfo ti : DBTableIdMgr.getInstance().getList())
            {
                if (hash.get(ti.getTableName()) == null)
                {
                    Table table = new Table(ti.getTableName());
                    list.add(table);
                    log.warn("Adding New Table ["+table.getName()+"]");
                    for (DBTableIdMgr.FieldInfo fi : ti.getFields())
                    {
                        Field field = new Field(fi.getName(), fi.getType());
                        table.getFields().add(field);
                    }
                }
            }
            Collections.sort(list);
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
     
        return list;
    }
    
    protected void buildUI()
    {
        
        tablesList = new JList(tables);
        fieldsList = new JList(fieldsModel);
        
        tablesList.setVisibleRowCount(10);
        tablesList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {

            /* (non-Javadoc)
             * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
             */
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    fillFieldList();
                    Table tbl = (Table)tablesList.getSelectedValue();
                    Desc desc = tbl.getDesc();
                    if (desc == null)
                    {
                        desc = new Desc("", currLocale.getCountry(), currLocale.getLanguage(), currLocale.getVariant());
                        tbl.setDesc(desc);
                    }
                    tblDescText.setText(desc.getText());
                    prevTable = tbl;
                }
            }
            
        });
        fieldsList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            /* (non-Javadoc)
             * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
             */
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    //System.out.println("selected");
                    fieldSelected();
                }
            }
            
        });
        
        
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        setSize(800, 600);

        descText.setRows(5);
        descText.setLineWrap(true);
        
        PanelBuilder pb = new PanelBuilder(new FormLayout("max(200px;p),4px,f:p:g", "t:p,4px,f:p:g,4px,p,4px,p"));
        CellConstraints cc = new CellConstraints();
        
        JPanel topInner = new JPanel(new BorderLayout());
        JScrollPane sp = new JScrollPane(tblDescText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        topInner.add(new JLabel("Table Description"), BorderLayout.NORTH);
        topInner.add(sp, BorderLayout.CENTER);
        tblDescText.setRows(8);
        tblDescText.setLineWrap(true);
        tblDescText.setWrapStyleWord(true);
        
        sp = new JScrollPane(tablesList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pb.add(sp, cc.xy(1, 1));
        pb.add(topInner, cc.xy(3, 1));
         
        sp = new JScrollPane(fieldsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        PanelBuilder inner = new PanelBuilder(new FormLayout("max(200px;p),4px,f:p:g", "p,2px,p,2px,p,2px,f:p:g"));
        inner.add(sp, cc.xywh(1, 1, 1, 7));
        inner.add(fieldLabel, cc.xy(3, 1));
        
        sp = new JScrollPane(descText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inner.add(sp,   cc.xy(3, 3));
        descText.setLineWrap(true);
        descText.setRows(8);
        descText.setWrapStyleWord(true);
        
        nxtBtn = new JButton("Next");
        nxtEmptyBtn = new JButton("Next Empty");
        
        JPanel bbp = ButtonBarFactory.buildCenteredBar(new JButton[] {nxtEmptyBtn, nxtBtn});
        inner.add(bbp,   cc.xy(3, 5));
        
        pb.add(inner.getPanel(), cc.xywh(1, 3, 3, 1));
        
        JButton saveBtn = new JButton("Save");
        JButton exitBtn = new JButton("Exit");
        
        bbp = ButtonBarFactory.buildCenteredBar(new JButton[] {exitBtn, saveBtn});
        pb.add(bbp,   cc.xywh(1, 7, 3, 1));

        setContentPane(pb.getPanel());
        
        nxtBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                next();
            }
        });
        nxtEmptyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                nextEmpty();
            }
        });
        
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                write();
            }
        });
        
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                shutdown();
            }
        });
        
        tblDescText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e)
            {
                //System.out.println("focuslost");
                super.focusLost(e);
                if (prevTable != null)
                {
                    prevTable.getDesc().setText(tblDescText.getText());
                }
            }
        });
        descText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e)
            {
                //System.out.println("focuslost");
                super.focusLost(e);
                getDataFromUI();
            }

            @Override
            public void focusGained(FocusEvent arg0)
            {
                super.focusGained(arg0);
                lastIndex = fieldsList.getSelectedIndex();
            }
            
        });
    }
    
    protected void shutdown()
    {
        write();
        setVisible(false);
        System.exit(0);
    }
    
    protected void updateBtns()
    {
        int inx = fieldsList.getSelectedIndex();
        boolean enabled = inx < fieldsModel.size() -1;
        nxtBtn.setEnabled(enabled);
        checkForMoreEmpties();

    }
    
    protected void checkForMoreEmpties()
    {
        int inx = getNextEmptyIndex(fieldsList.getSelectedIndex());
        nxtEmptyBtn.setEnabled(inx != -1);
    }
    
    protected int getNextEmptyIndex(final int inx)
    {
        if (inx > -1 && inx < fieldsModel.size())
        {
            for (int i=inx;i<fieldsModel.size();i++)
            {
                Field f = (Field)fieldsModel.get(i);
                if (f != null)
                {
                    Desc  desc = getDescForCurrLocale(f);
                    if (desc != null && StringUtils.isEmpty(desc.getText()))
                    {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    protected void getDataFromUI()
    {
        //System.out.println("getDataFromUI: "+fieldsList.getSelectedIndex());

        if (lastIndex > -1)
        {
            Field field = (Field)fieldsModel.get(lastIndex);
            if (field != null)
            {
                Desc desc = getDescForCurrLocale(field);
                if (desc != null)
                {
                    desc.setText(descText.getText());
                } else
                {
                    log.error("No Description node!");
                }
            }
        }
        lastIndex = fieldsList.getSelectedIndex();
    }
    
    protected Desc getDescForCurrLocale(final Field field)
    {
        for (Desc d : field.getDescs())
        {
            //System.out.println(d.getCountry()+"  "+currLocale.getCountry()+"  "+d.getLang()+"  "+currLocale.getLanguage());
            if (d.getCountry().equals(currLocale.getCountry()) && d.getLang().equals(currLocale.getLanguage()))
            {
                return d;
            }
        }
        return null;
    }
    
    protected void next()
    {
        int inx = fieldsList.getSelectedIndex();
        if (inx < fieldsModel.size()-1)
        {
            inx++;
        }
        fieldsList.setSelectedIndex(inx);
        updateBtns();
    }
    
    protected void nextEmpty()
    {
        int inx = getNextEmptyIndex(fieldsList.getSelectedIndex()+1);
        if (inx > -1)
        {
            fieldsList.setSelectedIndex(inx);
        }
        updateBtns();
    }
    
    protected void fillFieldList()
    {
        Table tbl = (Table)tablesList.getSelectedValue();
        fieldsModel.clear();
        for (Field f : tbl.getFields())
        {
            fieldsModel.addElement(f);
        }
        fieldsList.setSelectedIndex(0);
        updateBtns();
    }
    
    protected void fieldSelected()
    {
        //System.out.println("fieldSelected: "+fieldsList.getSelectedIndex());
        Field fld  = (Field)fieldsList.getSelectedValue();
        if (fld != null)
        {
            Desc  desc = getDescForCurrLocale(fld);
            if (desc != null)
            {
            } else
            {
                desc = new Desc("", currLocale.getCountry(), currLocale.getLanguage(), currLocale.getVariant());
                fld.getDescs().add(desc);
            }
            fieldLabel.setText(fld.getName());
            descText.setText(desc.getText());
        }
    }
    
    protected boolean write()
    {

        try
        {
            if (tables == null)
            {
                log.error("Datamodel information is null - datamodel file will not be written!!");
                return false;
            }
            
            File file = XMLHelper.getConfigDir(fileName);
            log.info("Writing descriptions to file: " + file.getAbsolutePath());
            
            //Element root = XMLHelper.readFileToDOM4J(new FileInputStream(XMLHelper.getConfigDirPath(datamodelOutputFileName)));
            
            FileWriter fw = new FileWriter(file);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            /*fw.write("<!-- \n");
            fw.write("    Do Not Edit this file!\n");
            fw.write("    Run DatamodelGenerator \n");
            Date date = new Date();
            fw.write("    Generated: "+date.toString()+"\n");
            fw.write("-->\n");*/
            
            //using betwixt for writing out datamodel file.  associated .betwixt files allow you to map and define 
            //output format of attributes in xml file.
            BeanWriter      beanWriter    = new BeanWriter(fw);
            XMLIntrospector introspector = beanWriter.getXMLIntrospector();
            
            introspector.getConfiguration().setWrapCollectionsInElement(false);
            
            beanWriter.getBindingConfiguration().setMapIDs(false);
            beanWriter.setWriteEmptyElements(false);
            beanWriter.enablePrettyPrint();
            beanWriter.write("database", tables);
            
            fw.close();
            
            return true;
            
        } catch (Exception ex)
        {
            log.error("error writing writeTree", ex);
            return false;
        }
    }

    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                FieldDescApp fd = new FieldDescApp();
                UIHelper.centerAndShow(fd);
            }
        });

    }

}
