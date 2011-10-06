package edu.ku.brc.services.geolocate.prototype.ui;

import static edu.ku.brc.ui.UIHelper.createI18NFormLabel;
import static edu.ku.brc.ui.UIHelper.createLabel;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.services.geolocate.prototype.ErrorPolygonDrawCancelListener;
import edu.ku.brc.services.geolocate.prototype.ErrorPolygonDrawEvent;
import edu.ku.brc.services.geolocate.prototype.ErrorPolygonDrawListener;
import edu.ku.brc.services.geolocate.prototype.Locality;
import edu.ku.brc.services.geolocate.prototype.LocalityWaypoint;
import edu.ku.brc.services.geolocate.prototype.MapPointerMoveEvent;
import edu.ku.brc.services.geolocate.prototype.MapPointerMoveListener;
import edu.ku.brc.services.geolocate.prototype.Mapper;
import edu.ku.brc.services.geolocate.prototype.MostAccuratePointReleaseListener;
import edu.ku.brc.services.geolocate.prototype.MostAccuratePointSnapListener;
import edu.ku.brc.services.geolocate.prototype.UncertaintyCircleChangeEvent;
import edu.ku.brc.services.geolocate.prototype.UncertaintyCircleChangeListener;
import edu.ku.brc.services.geolocate.prototype.UncertaintyCircleResizeCancelListener;
import edu.ku.brc.services.geolocate.prototype.UncertaintyCircleResizeEvent;
import edu.ku.brc.services.geolocate.prototype.UncertaintyCircleResizeListener;
import edu.ku.brc.services.geolocate.prototype.client.GeographicPoint;
import edu.ku.brc.services.geolocate.prototype.client.Georef_Result;
import edu.ku.brc.services.geolocate.prototype.client.Georef_Result_Set;
import edu.ku.brc.services.mapping.LatLonPlacemarkIFace;
import edu.ku.brc.services.mapping.LatLonPoint;
import edu.ku.brc.services.mapping.LocalityMapper.MapperListener;
import edu.ku.brc.specify.ui.ClickAndGoSelectListener;
import edu.ku.brc.specify.ui.WorldWindPanel;
import edu.ku.brc.ui.BiColorTableCellRenderer;
import edu.ku.brc.ui.JStatusBar;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.view.OrbitView;

/**
 * A UI panel for use in displaying the results of a GEOLocate web service query.
 * 
 * @author jstewart
 * @author rods
 * 
 * @code_status Alpha
 */
/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Aug 12, 2009
 *
 */
public class GeoLocateResultsDisplay extends JPanel implements MapperListener, SelectListener
{
    protected static final int MAP_WIDTH  = 500;
    protected static final int MAP_HEIGHT = 500;
    protected static final int MAP_WIDTH2  = 600;
    protected static final int MAP_HEIGHT2 = 450;

    protected ResultsTableModel tableModel;
    protected JTable            resultsTable;
    
    protected JLabel            mapLabel;
    
    protected JTextField        localityStringField;
    protected JTextField        countyField;
    protected JTextField        stateField;
    protected JTextField        countryField;
    protected JTextField		polyField;    
    
    protected JButton           acceptBtn     = null;
    
    protected WorldWindPanel    wwPanel       = null;
    protected Mapper 			geoMapper	  = null;
    protected Georef_Result      userDefGeoRef = null;
    protected Position          lastClickPos  = null;
    protected boolean useWorldWind = false;
    
    protected JTextField 		latText;
    protected JTextField 		lonText;
    protected JButton 			coordBtn;
    protected JTextField 		uncertTxt;
    protected JButton 			uncertBtn;
    protected JTextArea 		errorPTxt;
    protected JButton 			errorPBtn;
    
    protected JLabel statusLatLbl;
	protected JLabel statusLonLbl;
	protected JLabel statusURLbl;
	protected JLabel statusErrorLbl;
    
    /**
     * Constructor.
     */
    public GeoLocateResultsDisplay()
    {
        super();
        
        useWorldWind = AppPreferences.getLocalPrefs().getBoolean("USE.WORLDWIND", false);
        if (useWorldWind)
        	setLayout(new FormLayout("p,10px,500px,10px,f:p:g", "p,2px,p,2px,p,2px,p,10px,f:p:g")); //$NON-NLS-1$ //$NON-NLS-2$
        else
        	setLayout(new FormLayout("p,10px,500px,10px,f:p:g", "p,2px,p,2px,p,2px,p,5px,f:p:g,10px,f:p:g"));
        
        CellConstraints cc = new CellConstraints();
        
        // add the query fields to the display
        int rowIndex = 1;
        localityStringField = addRow(cc, getResourceString("GeoLocateResultsDisplay.LOCALITY_DESC"),      1, rowIndex); //$NON-NLS-1$
        rowIndex+=2;
        countyField         = addRow(cc, getResourceString("GeoLocateResultsDisplay.COUNTY"), 1, rowIndex); //$NON-NLS-1$
        rowIndex+=2;
        stateField          = addRow(cc, getResourceString("GeoLocateResultsDisplay.STATE"),    1, rowIndex); //$NON-NLS-1$
        rowIndex+=2;
        countryField        = addRow(cc, getResourceString("GeoLocateResultsDisplay.COUNTRY"),    1, rowIndex); //$NON-NLS-1$
        rowIndex+=2;

        // add the JLabel to show the map
        mapLabel = createLabel(getResourceString("GeoLocateResultsDisplay.LOADING_MAP")); //$NON-NLS-1$
        mapLabel.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        
        if (!useWorldWind)
        {
        	//Add the correction marker section.
        	JPanel corMarkerPanel = new JPanel();
            add(corMarkerPanel, cc.xywh(1,rowIndex,3,1));
            rowIndex+=2;

            corMarkerPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Correction (Green) Marker Properties",
            		TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 12), Color.BLACK));
            corMarkerPanel.setLayout(new GridLayout(1, 3));
            
            JPanel coordinatesP = new JPanel();
            coordinatesP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            GroupLayout layout = new GroupLayout(coordinatesP);
            coordinatesP.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            JLabel latLbl = new JLabel("lat: ");
            latText = new JTextField();
            latText.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					statusErrorLbl.setText("");
					
				}
			});
            
            JLabel lonLbl = new JLabel("lon: ");
            lonText = new JTextField();
            lonText.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					statusErrorLbl.setText("");
					
				}
			});

            coordBtn = new JButton("apply manual");
            coordBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					applyManualCoordinates();
				}
			});
            
            layout.setHorizontalGroup(layout.createSequentialGroup()
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            				.addComponent(latLbl).addComponent(lonLbl))
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            				.addComponent(latText).addComponent(lonText).addComponent(coordBtn)));
            
            layout.setVerticalGroup(layout.createSequentialGroup()
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            				.addComponent(latLbl).addComponent(latText))
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            				.addComponent(lonLbl).addComponent(lonText))
            		.addComponent(coordBtn).addGap(5));
            corMarkerPanel.add(coordinatesP);
            
            
            JPanel uncertaintyP = new JPanel();
            uncertaintyP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            layout = new GroupLayout(uncertaintyP);
            uncertaintyP.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            JLabel uncertLbl1 = new JLabel("uncertainty radius");
            JLabel uncertLbl2 = new JLabel("in meters: ");
            uncertTxt = new JTextField();
            uncertTxt.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					statusErrorLbl.setText("");
					
				}
			});

            uncertBtn = new JButton("apply manual");
            uncertBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equalsIgnoreCase("apply manual"))
					{
						long uRad = 0;
						try
						{
							uRad = Long.parseLong(uncertTxt.getText());
						}
						
						catch (Exception ex)
						{
							statusErrorLbl.setText("Error: Invalid uncertainty radius data type.");
							return;
						}
						
						geoMapper.editUncertaintyCircle(uRad);
						geoMapper.hideEditUncertaintyHandle();
						uncertBtn.setText("edit radius");
						uncertTxt.setEditable(false);
					}
					
					else
					{
						uncertTxt.setEditable(true);
						uncertBtn.setText("apply manual");
						geoMapper.showEditUncertaintyHandle();
					}
				}
			});
            
            layout.setHorizontalGroup(layout.createSequentialGroup()
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            				.addComponent(uncertLbl1).addComponent(uncertLbl2))
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    				.addComponent(uncertTxt).addComponent(uncertBtn)));
            
            layout.setVerticalGroup(layout.createSequentialGroup()
            		.addComponent(uncertLbl1)
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            				.addComponent(uncertLbl2).addComponent(uncertTxt))
            		.addComponent(uncertBtn));
            corMarkerPanel.add(uncertaintyP);
            
            JPanel errorPolygonP = new JPanel();
            errorPolygonP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            layout = new GroupLayout(errorPolygonP);
            errorPolygonP.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            JLabel errorPLbl= new JLabel("error polygon");
            errorPTxt = new JTextArea();
            errorPTxt.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					statusErrorLbl.setText("");
					
				}
			});

            errorPTxt.setLineWrap(true);
            JScrollPane errorPScrollPane = new JScrollPane(errorPTxt, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            errorPScrollPane.setPreferredSize(new Dimension(70, 50));
            errorPBtn = new JButton("apply manual");
            errorPBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equalsIgnoreCase("apply manual"))
					{

			        	List<GeoPosition> errorRegion = new ArrayList<GeoPosition>();
						try
						{
							if (errorPTxt.getText().length() > 0)
							{
								double lat = Double.NaN; 
					        	double lon = Double.NaN;
					        	String[] latLons =  errorPTxt.getText().split(",");
					        	for (int i=0; i<latLons.length; i++)
					        	{
					        		if ((i%2) == 0) //Latitude.
					        			lat = Double.parseDouble(latLons[i]);
					        		else //Longitude.
					        		{
					        			lon = Double.parseDouble(latLons[i]);
					        			GeoPosition pos = new GeoPosition(lat, lon);
					        			errorRegion.add(pos);
					        		}
					        	}
							}
						}
						
						catch (Exception ex)
						{
							statusErrorLbl.setText("Error: Invalid polygon string format.");
							return;
						}
						
						geoMapper.drawPolygon(errorRegion);
						geoMapper.hideEditPolygonHandle();
						errorPBtn.setText("clear polygon");
						errorPTxt.setEditable(false);
					}
					
					else if (e.getActionCommand().equalsIgnoreCase("draw polygon"))
					{
						errorPTxt.setEditable(true);
						errorPBtn.setText("apply manual");
						geoMapper.showEditPolygonHandle();
					}
					
					else if (e.getActionCommand().equalsIgnoreCase("clear polygon"))
					{
						errorPTxt.setText("");
						geoMapper.removePolygon();
						errorPBtn.setText("draw polygon");
					}
				}
			});
            
            layout.setHorizontalGroup(layout.createSequentialGroup()
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            						.addComponent(errorPLbl).addComponent(errorPScrollPane))
            				.addComponent(errorPBtn)));
            
            layout.setVerticalGroup(layout.createSequentialGroup()
            		.addComponent(errorPLbl).addComponent(errorPScrollPane).addComponent(errorPBtn));
            corMarkerPanel.add(errorPolygonP);
            
            
            //add(mapLabel, cc.xywh(5,1,1,9));
            //Add the map.
        	geoMapper = new Mapper();
        	geoMapper.setMapSize(new Dimension(MAP_WIDTH2, MAP_HEIGHT2));
        	geoMapper.setZoomButtonsVisible(false);
        	geoMapper.setZoomSliderVisible(false);
        	
        	JPanel statusPanel = new JPanel();
        	statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        	statusPanel.setPreferredSize(new Dimension(MAP_WIDTH2, 16));
        	statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        	
        	statusLatLbl = new JLabel("Lat: ,", JLabel.LEFT);
        	statusLatLbl.setPreferredSize(new Dimension(100, 12));
        	statusLatLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        	statusLonLbl = new JLabel("Lon: ", JLabel.LEFT);
        	statusLonLbl.setPreferredSize(new Dimension(100, 12));
        	statusLonLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        	statusURLbl = new JLabel("", JLabel.LEFT);
        	statusURLbl.setPreferredSize(new Dimension(100, 12));
        	statusURLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        	statusErrorLbl = new JLabel("", JLabel.LEFT);
        	statusErrorLbl.setPreferredSize(new Dimension(290, 12));
        	statusErrorLbl.setForeground(Color.RED);
        	statusErrorLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        	statusPanel.add(statusErrorLbl);
        	statusPanel.add(statusURLbl);
        	statusPanel.add(statusLatLbl);
        	statusPanel.add(statusLonLbl);
        	
        	JPanel mPanel = new JPanel((LayoutManager) (new BorderLayout(0, 2)));
        	mPanel.setPreferredSize(new Dimension(MAP_WIDTH2, MAP_HEIGHT2 + statusPanel.getHeight()));
        	mPanel.setMinimumSize(new Dimension(MAP_WIDTH2, MAP_HEIGHT2 + statusPanel.getHeight()));
        	
        	geoMapper.addUncertaintyCircleResizeCancelListener(new UncertaintyCircleResizeCancelListener() {
				
				@Override
				public void uncertaintyCircleResizeCancelled() {
					geoMapper.hideEditUncertaintyHandle();
					uncertBtn.setText("edit radius");
					uncertTxt.setEditable(false);
				}
			});
        	
        	geoMapper.addErrorPolygonDrawCancelListener(new ErrorPolygonDrawCancelListener() {
				
				@Override
				public void errorPolygonDrawCancelled() {
					errorPTxt.setText("");
					errorPTxt.setEditable(false);
					errorPBtn.setText("draw polygon");
				}
			});
        	
        	geoMapper.addErrorPolygonDrawListener(new ErrorPolygonDrawListener() {
				
				@Override
				public void errorPolygonDrawn(ErrorPolygonDrawEvent evt) {
					errorPTxt.setText(geoMapper.getMostAccurateResultPt().getLocality()
							.getErrorPolygon());
					
					geoMapper.hideEditPolygonHandle();
					errorPBtn.setText("clear polygon");
					errorPTxt.setEditable(false);
					
				}
			});
        	
        	geoMapper.addUncertaintyCircleResizeListener(new UncertaintyCircleResizeListener() {
				
				@Override
				public void uncertaintyCircleResized(UncertaintyCircleResizeEvent evt) {
					uncertTxt.setText(geoMapper.getMostAccurateResultPt().getLocality()
							.getUncertaintyMeters());
					
					geoMapper.hideEditUncertaintyHandle();
					uncertBtn.setText("edit radius");
					uncertTxt.setEditable(false);
					statusURLbl.setText("");
				}
			});
        	
        	geoMapper.addUncertaintyCircleChangeListener(new UncertaintyCircleChangeListener() {
				
				@Override
				public void uncertaintyCircleChanged(UncertaintyCircleChangeEvent evt) {
					statusURLbl.setText("U. Radius: " + Long.toString(evt.getUncertaintyRadiusInMeters()) + "m");
				}
			});
        	
        	geoMapper.addMapPointerMoveListener(new MapPointerMoveListener() {
				
				@Override
				public void mapPointerMoved(MapPointerMoveEvent evt) {
					GeoPosition pointerPos = evt.getLocation();
            		setStatusBarCoordinates(pointerPos.getLatitude(), pointerPos.getLongitude());
				}
			});
        	
        	geoMapper.addMostAccuratePointReleaseListener(new MostAccuratePointReleaseListener() {
				
				@Override
				public void mostAccuratePointReleased(MapPointerMoveEvent evt) {
					LocalityWaypoint mostAccurate = geoMapper.getMostAccurateResultPt();
					double lat = mostAccurate.getPosition().getLatitude();
                	double lon = mostAccurate.getPosition().getLongitude();
                	latText.setText(Double.toString(geoMapper.decimalRound(lat, 6)));
					lonText.setText(Double.toString(geoMapper.decimalRound(lon, 6)));
					
					String resUncert = mostAccurate.getLocality().getUncertaintyMeters();
					if ((resUncert != null) && !(resUncert.equalsIgnoreCase("unavailable")))
						uncertTxt.setText(resUncert);
					else
						uncertTxt.setText("");
					
					String resErrorP = mostAccurate.getLocality().getErrorPolygon();
					if ((resErrorP != null) && !(resErrorP.equalsIgnoreCase("unavailable")))
						errorPTxt.setText(resErrorP);
					else
						errorPTxt.setText("");
				}
			});
        	
        	geoMapper.addMostAccuratePointSnapListener(new MostAccuratePointSnapListener() {
				
				@Override
				public void mostAccuratePointSnapped(MapPointerMoveEvent evt) {
					int index = 0;
					double snapLat = evt.getLocation().getLatitude();
					double snapLon = evt.getLocation().getLongitude();
					
					for (int i=0; i<tableModel.getResults().size(); i++)
					{
						index = i;
						Georef_Result res = tableModel.getResult(index);
						double resLat = geoMapper.decimalRound(res.getWGS84Coordinate().getLatitude(), 6);
						double resLon = geoMapper.decimalRound(res.getWGS84Coordinate().getLongitude(), 6);
						if ((snapLat == resLat) && (snapLon == resLon))
						{
							latText.setText(Double.toString(resLat));
							lonText.setText(Double.toString(resLon));
							String resUncert = res.getUncertaintyRadiusMeters();
							if ((resUncert != null) && !(resUncert.equalsIgnoreCase("unavailable")))
								uncertTxt.setText(resUncert);
							else
								uncertTxt.setText("");
							
							String resErrorP = res.getUncertaintyPolygon();
							if ((resErrorP != null) && !(resErrorP.equalsIgnoreCase("unavailable")))
								errorPTxt.setText(resErrorP);
							else
								errorPTxt.setText("");
							break;
						}
					}
					// Auto select the row corresponding to the point snapped on.
			        resultsTable.getSelectionModel().setSelectionInterval(index, index);
			        resultsTable.repaint();
				}
			});

        	mPanel.add(geoMapper, BorderLayout.CENTER);
        	mPanel.add(statusPanel, BorderLayout.SOUTH);
        	add(mPanel, cc.xywh(5,1,1,11)); 
        } 
        
        else
        {
            wwPanel = new WorldWindPanel();
            wwPanel.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
            wwPanel.getWorld().addSelectListener(new ClickAndGoSelectListener(wwPanel.getWorld(), MarkerLayer.class));
            wwPanel.getWorld().addSelectListener(this);
            
            wwPanel.getWorld().getInputHandler().addMouseListener(new MouseAdapter()
            {
                /* (non-Javadoc)
                 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
                 */
                @Override
                public void mouseClicked(final MouseEvent e)
                {
                    super.mouseClicked(e);
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            Position pos = wwPanel.getWorld().getCurrentPosition();
                            if (!pos.equals(lastClickPos))
                            {
                                if (userDefGeoRef == null)
                                {
                                    addUserDefinedMarker();
                                } else 
                                {
                                    repositionUserDefMarker();
                                }
                            }
                        }
                    });
                }
            });
            
            add(wwPanel, cc.xywh(5,1,1,9));
        }

        // add the results table
        tableModel   = new ResultsTableModel();
        resultsTable = new JTable(tableModel);
        resultsTable.setShowVerticalLines(false);
        resultsTable.setShowHorizontalLines(false);
        resultsTable.setRowSelectionAllowed(true);
        resultsTable.setDefaultRenderer(String.class, new BiColorTableCellRenderer(false));
        
        resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    if (acceptBtn != null)
                    {
                        //System.out.println(resultsTable.getSelectedRowCount());
                        acceptBtn.setEnabled(resultsTable.getSelectedRowCount() > 0);
                    }
                }
            }
        });
        
        if (wwPanel != null)
        {
            resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e)
                {
                    wwPanel.flyToMarker(resultsTable.getSelectedRow());
                }
            });
        }
        
        else
        {
        	resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e)
                {
                	if (resultsTable.getSelectedRow() > -1)
                	{
	                	Georef_Result res = tableModel.getResult(resultsTable.getSelectedRow());
	                	double lat = res.getWGS84Coordinate().getLatitude();
	                	double lon = res.getWGS84Coordinate().getLongitude();
	                	geoMapper.snapMostAccuratePointTo(new GeoPosition(lat, lon));
	                	latText.setText(Double.toString(geoMapper.decimalRound(lat, 6)));
						lonText.setText(Double.toString(geoMapper.decimalRound(lon, 6)));
						geoMapper.hideEditUncertaintyHandle();
						geoMapper.hideEditPolygonHandle();
						
						String resUncert = res.getUncertaintyRadiusMeters();
						if ((resUncert != null) && !(resUncert.equalsIgnoreCase("unavailable")))
						{
							uncertTxt.setText(resUncert);
							uncertTxt.setEditable(false);
							uncertBtn.setText("edit radius");
						}
						else
						{
							uncertTxt.setText("");
							uncertTxt.setEditable(true);
							uncertBtn.setText("apply manual");
						}
						
						String resErrorP = res.getUncertaintyPolygon();
						if ((resErrorP != null) && !(resErrorP.equalsIgnoreCase("unavailable")))
						{
							errorPTxt.setText(resErrorP);
							errorPTxt.setEditable(false);
							errorPBtn.setText("clear polygon");
						}
						else
						{
							errorPTxt.setText("");
							errorPTxt.setEditable(false);
							errorPBtn.setText("draw polygon");
						}
                	}
                }
            });
        }

        // add a cell renderer that uses the tooltip to show the text of the "parse pattern" column in case
        // it is too long to show and gets truncated by the standard cell renderer
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof String)
                {
                    ((JLabel)c).setToolTipText((String)value);
                }
                return c;
            }
        };
        resultsTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
        
        JScrollPane scrollPane = new JScrollPane(resultsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, cc.xywh(1,rowIndex, 3, 1));
        rowIndex+=2;
    }
    
    protected void setStatusBarCoordinates(double latitude, double longitude) {
    	String msg = String.format("Lat: %10.6f,", latitude);
    	statusLatLbl.setText(msg);
    	msg = String.format("Lon: %11.6f", longitude);
    	statusLonLbl.setText(msg);
	}

	protected void applyManualCoordinates() {
		
		double lat = Double.NaN;
		double lon = Double.NaN;
		
		try
		{
			lat = Double.parseDouble(latText.getText());
			lon = Double.parseDouble(lonText.getText());
		}
		
		catch (Exception ex)
		{
			statusErrorLbl.setText("Error: Invalid coordinate(s) data type.");
			return;
		}
		
		geoMapper.snapMostAccuratePointTo(new GeoPosition(lat, lon));
	}

	/**
     * @param acceptBtn the acceptBtn to set
     */
    public void setAcceptBtn(JButton acceptBtn)
    {
        this.acceptBtn = acceptBtn;
    }

    /**
     * 
     */
    private void addUserDefinedMarker()
    {
        Position        pos = wwPanel.getWorld().getCurrentPosition();
        GeographicPoint pnt = new GeographicPoint();
        pnt.setLatitude(pos.getLatitude().getDegrees());
        pnt.setLongitude(pos.getLongitude().getDegrees());
        
        // Create User defined point/marker
        userDefGeoRef = new Georef_Result();
        userDefGeoRef.setWGS84Coordinate(pnt);
        userDefGeoRef.setParsePattern(getResourceString("GeoLocateResultsDisplay.USRDEF")); // XXX I18N
        tableModel.add(userDefGeoRef);
        
        // Auto select the User Defined row
        int lastRow = tableModel.getRowCount() - 1;
        resultsTable.getSelectionModel().setSelectionInterval(lastRow, lastRow);
        resultsTable.repaint();
        
        wwPanel.placeMarkers(tableModel.getPoints(), null);
    }
    
    /**
     * 
     */
    private void repositionUserDefMarker()
    {
        Position        pos = wwPanel.getWorld().getCurrentPosition();
        GeographicPoint pnt = userDefGeoRef.getWGS84Coordinate();
        pnt.setLatitude(pos.getLatitude().getDegrees());
        pnt.setLongitude(pos.getLongitude().getDegrees());
        
        tableModel.fireTableCellUpdated(tableModel.getRowCount()-1, 1);
        tableModel.fireTableCellUpdated(tableModel.getRowCount()-1, 2);
        
        wwPanel.placeMarkers(tableModel.getPoints(), null);
        wwPanel.getWorld().repaint();
        
        int lastRow = tableModel.getRowCount() - 1;
        resultsTable.getSelectionModel().setSelectionInterval(lastRow, lastRow);
        resultsTable.repaint();
    }
    
    /**
     * @param localityString
     * @param county
     * @param state
     * @param country
     * @param georefResults
     */
    public void setGeoLocateQueryAndResults(String localityString, 
                                            String county, 
                                            String state, 
                                            String country, 
                                            Georef_Result_Set georefResults)
    {
        localityStringField.setText(localityString);
        localityStringField.setCaretPosition(0);
        countyField.setText(county);
        countyField.setCaretPosition(0);
        stateField.setText(state);
        stateField.setCaretPosition(0);
        countryField.setText(country);
        countryField.setCaretPosition(0);
        
        tableModel.setResultSet(georefResults.getResultSet());
        
        if (wwPanel != null)
        {
            ArrayList<LatLonPlacemarkIFace> pnts = new ArrayList<LatLonPlacemarkIFace>(georefResults.getResultSet().length);
            for (Georef_Result grr : georefResults.getResultSet())
            {
                pnts.add(new LatLonPoint(grr.getWGS84Coordinate().getLatitude(), grr.getWGS84Coordinate().getLongitude()));
            }
            wwPanel.placeMarkers(pnts, 0);
            
        } else
        {
            //mapLabel.setText(getResourceString("GeoLocateResultsDisplay.LOADING_MAP")); //$NON-NLS-1$
        	//Build locality way points to plot.
        	LocalityWaypoint[] lWps = new LocalityWaypoint[ georefResults.getNumResults()];
        	int index = 0;
        	for (Georef_Result grr : georefResults.getResultSet())
            {
        		Locality loc = new Locality();
        		loc.setLocality(localityString);
        		loc.setCountry(country);
        		loc.setState(state);
        		loc.setCounty(county);
        		loc.setPrecision(grr.getPrecision());
        		loc.setScore(grr.getScore());
        		loc.setLatitude(grr.getWGS84Coordinate().getLatitude());
        		loc.setLongitude(grr.getWGS84Coordinate().getLongitude());
        		loc.setErrorPolygon(grr.getUncertaintyPolygon());
        		loc.setUncertaintyMeters(grr.getUncertaintyRadiusMeters());
        		lWps[index] = new LocalityWaypoint(loc);
        		index++;
            }
        	geoMapper.plotResultSet(lWps, 0);
        }
        //TODO: This might be crucial!
        //GeoLocate.getMapOfGeographicPoints(georefResults.getResultSet(), GeoLocateResultsDisplay.this);
        
        // set the table height to at most 10 rows
        Dimension size = resultsTable.getPreferredScrollableViewportSize();
        size.height = Math.min(size.height, resultsTable.getRowHeight()*10);
        resultsTable.setPreferredScrollableViewportSize(size);
        UIHelper.calcColumnWidths(resultsTable);
    }
    
    /**
     * Returns the selected result.
     * 
     * @return the selected result
     */
    public Georef_Result getSelectedResult()
    {
        int rowIndex = resultsTable.getSelectedRow();
        if (rowIndex < 0 || rowIndex > tableModel.getRowCount())
        {
            return null;
        }
        
        return tableModel.getResult(rowIndex);
    }
    
    /**
     * Selects the result with the given index in the results list.
     * 
     * @param index the index of the result to select
     */
    public void setSelectedResult(int index)
    {
        if (index < 0 || index > resultsTable.getRowCount()-1)
        {
            resultsTable.clearSelection();
        }
        else
        {
            resultsTable.setRowSelectionInterval(index, index);
            int colCount = resultsTable.getColumnCount();
            resultsTable.setColumnSelectionInterval(0, colCount-1);
        }
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.services.LocalityMapper.MapperListener#exceptionOccurred(java.lang.Exception)
     */
    public void exceptionOccurred(Exception e)
    {
        if (mapLabel != null) mapLabel.setText(getResourceString("GeoLocateResultsDisplay.ERROR_GETTING_MAP")); //$NON-NLS-1$
        JStatusBar statusBar = UIRegistry.getStatusBar();
        statusBar.setErrorMessage(getResourceString("GeoLocateResultsDisplay.ERROR_GETTING_MAP"), e); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.services.LocalityMapper.MapperListener#mapReceived(javax.swing.Icon)
     */
    public void mapReceived(Icon map)
    {
        JStatusBar statusBar = UIRegistry.getStatusBar();
        statusBar.setText(""); //$NON-NLS-1$
        mapLabel.setText(null);
        mapLabel.setIcon(map);
        repaint();
    }
    
    /* (non-Javadoc)
     * @see gov.nasa.worldwind.event.SelectListener#selected(gov.nasa.worldwind.event.SelectEvent)
     */
    @Override
    public void selected(final SelectEvent event)
    {
        if (event.getEventAction().equals(SelectEvent.LEFT_CLICK))
        {
            // This is a left click
            if (event.hasObjects() && event.getTopPickedObject().hasPosition())
            {
                lastClickPos = wwPanel.getWorld().getCurrentPosition();
                
                // There is a picked object with a position
                if (wwPanel.getWorld().getView() instanceof OrbitView)
                {
                    if (event.getTopObject().getClass().equals(BasicMarker.class))
                    {
                        int inx = wwPanel.getMarkers().indexOf(event.getTopObject());
                        if (inx > -1)
                        {
                            resultsTable.setRowSelectionInterval(inx, inx);
                        }
                    } else if (event.getTopObject().getClass().equals(GlobeAnnotation.class))
                    {
                        int inx = wwPanel.getAnnotations().indexOf(event.getTopObject());
                        if (inx > -1)
                        {
                            resultsTable.setRowSelectionInterval(inx, inx);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a new row to this object's content area.
     * 
     * @param cc the cell constraints of the new row
     * @param labelStr the text label for the new row
     * @param column the starting column number for the new row's UI
     * @param row the row number of the new row
     * @return the {@link JTextField} added to the new row
     */
    protected JTextField addRow(final CellConstraints cc,
                                final String labelStr,
                                final int column,
                                final int row)
    {
        add(createI18NFormLabel(labelStr), cc.xy(column,row)); //$NON-NLS-1$
        JTextField tf = createTextField();
        tf.setEditable(false);
        add(tf, cc.xy(column+2,row));
        return tf;
    }
    
    /**
     * Cleans up the panel.
     */
    public void shutdown()
    {
        if (wwPanel != null)
        {
            wwPanel.shutdown();
        }
    }

    /**
     * Creates a {@link JTextField} customized for use in this UI widget.
     * 
     * @return a {@link JTextField}
     */
    protected JTextField createTextField()
    {
        JTextField tf     = UIHelper.createTextField();
        Insets     insets = tf.getBorder().getBorderInsets(tf);
        tf.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.bottom));
        tf.setForeground(Color.BLACK);
        tf.setBackground(Color.WHITE);
        tf.setEditable(false);
        return tf;
    }
    
    //-----------------------------------------------------------------
    //
    //-----------------------------------------------------------------
    protected class ResultsTableModel extends AbstractTableModel
    {
        protected List<Georef_Result> results;
        
        public void setResultSet(Georef_Result[] results)
        {
            this.results = new ArrayList<Georef_Result>();
            for (Georef_Result grr : results)
            	this.results.add(grr);
            		
            fireTableDataChanged();
        }
        
        /**
         * @param grr
         */
        public void add(final Georef_Result grr)
        {
            results.add(grr);
            SwingUtilities.invokeLater(new Runnable() {
                public void run()
                {
                    fireTableDataChanged();
                }
            });
        }
        
        /**
         * @param index
         * @return
         */
        public Georef_Result getResult(int index)
        {
            return results.get(index);
        }
        
        /**
         * @return the results
         */
        public List<Georef_Result> getResults()
        {
            return results;
        }
        
        /**
         * @return
         */
        public List<LatLonPlacemarkIFace> getPoints()
        {
            ArrayList<LatLonPlacemarkIFace> pnts = new ArrayList<LatLonPlacemarkIFace>(results.size());
            
            for (Georef_Result grr : results)
            {
                pnts.add(new LatLonPoint(grr.getWGS84Coordinate().getLatitude(), grr.getWGS84Coordinate().getLongitude()));
            }
            return pnts;
        }

        /* (non-Javadoc)
         * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
         */
        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:
                {
                    return Integer.class;
                }
                case 1:
                case 2:
                {
                    return Double.class;
                }
                case 3:
                case 4:
                case 5:
                case 6:
                {
                    return String.class;
                }
            }
            return null;
        }

        /* (non-Javadoc)
         * @see javax.swing.table.AbstractTableModel#getColumnName(int)
         */
        @Override
        public String getColumnName(int column)
        {
            switch (column)
            {
                case 0:
                {
                    return getResourceString("GeoLocateResultsDisplay.NUMBER"); //$NON-NLS-1$
                }
                case 1:
                {
                    return getResourceString("GeoLocateResultsDisplay.LATITUDE"); //$NON-NLS-1$
                }
                case 2:
                {
                    return getResourceString("GeoLocateResultsDisplay.LONGITUDE"); //$NON-NLS-1$
                }
                case 3:
                {
                    return getResourceString("GeoLocateResultsDisplay.PARSE_PATTERN"); //$NON-NLS-1$
                }
                case 4:
                {
                    return getResourceString("GeoLocateResultsDisplay.PRECISION"); //$NON-NLS-1$
                }
                case 5:
                {
                    return getResourceString("GeoLocateResultsDisplay.ERROR_POLY"); //$NON-NLS-1$
                }
                case 6:
                {
                    return getResourceString("GeoLocateResultsDisplay.UNCERTAINTY"); //$NON-NLS-1$
                }
            }
            return null;
        }

        /* (non-Javadoc)
         * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        @Override
        public int getColumnCount()
        {
            return 7;
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getRowCount()
         */
        @Override
        public int getRowCount()
        {
            return (results == null) ? 0 : results.size();
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            Georef_Result res = results.get(rowIndex);
            switch (columnIndex)
            {
                case 0:
                {
                    return rowIndex+1;
                }
                case 1:
                {
                    return res.getWGS84Coordinate().getLatitude();
                }
                case 2:
                {
                    return res.getWGS84Coordinate().getLongitude();
                }
                case 3:
                {
                	return res.getParsePattern();
                }
                case 4:
                {
                	if (res.getPrecision() != null)
                		return (res.getPrecision() + " (" + res.getScore() + ")");
                	else
                		return "N\\A";
                }
                case 5:
                {
                	String cellText = "unavailable";
                	if (res.getUncertaintyPolygon() != null)
                	{
	                	String polyString = res.getUncertaintyPolygon();
	                	if (!cellText.equalsIgnoreCase(polyString.toLowerCase()))
	                	{
	                		cellText = "present";
	                	}
                	}
                	
                	else
                		cellText = "N\\A";
                	
                	return cellText;
                }
                case 6:
                {
                	if (res.getUncertaintyRadiusMeters() != null)
                		return res.getUncertaintyRadiusMeters();
                	else
                		return "N\\A";
                }
            }
            return null;
        }
    }
}