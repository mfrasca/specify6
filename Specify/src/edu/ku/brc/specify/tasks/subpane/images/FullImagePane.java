/* Copyright (C) 2012, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.specify.tasks.subpane.images;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.core.Taskable;
import edu.ku.brc.af.tasks.subpane.BaseSubPane;
import edu.ku.brc.ui.ImageDisplay;
import edu.ku.brc.ui.UIHelper;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Aug 30, 2012
 *
 */
public class FullImagePane extends BaseSubPane
{
    private ImageIcon     imgIcon;
    private ImageDisplay  imgDisp;
    private ImageDataItem imgDataItem;
    
    /**
     * @param name
     * @param task
     * @param idi
     */
    public FullImagePane(final String name, final Taskable task, final ImageDataItem idi)
    {
        super(name, task);
        this.imgDataItem = idi;
        
        createUI();
    }
    
    /**
     * Creates the UI.
     */
    protected void createUI()
    {
        
        CellConstraints cc = new CellConstraints();
        
        imgDisp = new ImageDisplay(1024, 768, false, false);
        
        final JScrollPane  sp = UIHelper.createScrollPane(imgDisp, true);
        PanelBuilder pb = new PanelBuilder(new FormLayout("f:p:g", "f:p:g"), this);
        pb.add(sp, cc.xy(1, 1));
        
        imgDataItem.loadScaledImage(-1, new ImageLoaderListener()
        {
            @Override
            public void imagedLoaded(String imageName,
                                     String mimeType,
                                     boolean doLoadFullImage,
                                     int scale,
                                     boolean isError,
                                     ImageIcon imageIcon, 
                                     File localFile)
            {
                imgIcon = !isError ? imageIcon : null;
                imgDisp.setImage(imgIcon);
                FullImagePane.this.repaint();
            }
        }); 
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.subpane.BaseSubPane#aboutToShutdown()
     */
    @Override
    public boolean aboutToShutdown()
    {
        return super.aboutToShutdown();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.subpane.BaseSubPane#shutdown()
     */
    @Override
    public void shutdown()
    {
        imgIcon = null;
        imgDisp.setImage((ImageIcon)null);
        super.shutdown();
    }
    
    
}