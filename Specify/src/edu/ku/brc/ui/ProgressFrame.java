/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.ui;

import static edu.ku.brc.ui.UIHelper.createButton;
import static edu.ku.brc.ui.UIHelper.createLabel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A Frame displaying the progress of the conversion process.
 * 
 * @author rods
 *
 * @code_status Beta
 *
 * Created Date: Nov 7, 2006
 *
 */
public class ProgressFrame extends JFrame
{
    protected JProgressBar overallProgress;
    protected JProgressBar processProgress;
    protected JLabel       desc;
    protected JButton      closeBtn;
    protected JFrame       instance;
    
    protected boolean      isProcessPercent = false;
    protected int          origMax          = 0;
    
    public ProgressFrame(String title)
    {
        createUI(title);
    }
    
    protected void createUI(String title)
    {
        PanelBuilder    builder    = new PanelBuilder(new FormLayout("p,2px,f:p:g", "p,5px,p,5px,p,10px,p"));
        CellConstraints cc         = new CellConstraints();

        overallProgress = new JProgressBar();
        processProgress = new JProgressBar();
        desc            = createLabel("");
        closeBtn        = createButton("Cancel");

        processProgress.setStringPainted(true);
 
        desc.setHorizontalAlignment(SwingConstants.CENTER);
        builder.add( desc, cc.xywh(1,1,3,1));
        
        builder.add( createLabel("Process:"), cc.xy(1,3)); // I18N
        builder.add( processProgress, cc.xy(3,3));
        
        builder.add( createLabel("Overall:"), cc.xy(1,5)); // I18N
        builder.add( overallProgress, cc.xy(3,5));
        
        builder.add( closeBtn, cc.xy(1,7));
        
        builder.getPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(builder.getPanel());
        
        setSize(new Dimension(500,125));
        
        setTitle(title);
        
        overallProgress.setIndeterminate(true);
        
        instance = this;
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                instance.setVisible(false);
                System.exit(0);
            }
        });
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public synchronized void incOverall()
    {

        overallProgress.setValue(overallProgress.getValue() + 1);

    }
    
    public synchronized void setOverall(final int min, final int max)
    {
        overallProgress.setIndeterminate(min == 0 && max == 0);
        overallProgress.setMinimum(min);
        overallProgress.setMaximum(max);
        overallProgress.setValue(min);

    }
    
    public synchronized void setOverall(final int value)
    {

        overallProgress.setValue(value);

    }
    
    /**
     * @return the origMax
     */
    public synchronized int getOrigMax()
    {
        return origMax;
    }

    /**
     * @param origMax the origMax to set
     */
    public synchronized void setOrigMax(int origMax)
    {
        this.origMax = origMax;
    }

    public synchronized void setProcess(final int min, final int max)
    {
        processProgress.setMinimum(isProcessPercent ? 0 : min);
        processProgress.setMaximum(isProcessPercent ? 100 : max);
        processProgress.setValue(min);
        processProgress.setString("");
        origMax = max;
    }
    
    public synchronized void setProcess(final int value)
    {
        if (processProgress.isIndeterminate())
        {
            processProgress.setIndeterminate(false);
        }
        
        if (isProcessPercent)
        {
            if (value > origMax || value < 0)
            {
                processProgress.setString("100%");
                
            } else
            {
                int percent = (int)(((double)value) / ((double)origMax) * 100.0);
                processProgress.setValue(percent);
                processProgress.setString(value > 0 ? Integer.toString(percent) + "%" : "");
            }
            
        } else 
        {
            if (value >=  processProgress.getMaximum())
            {
                processProgress.setString(processProgress.getMaximum() +" / "+ processProgress.getMaximum());
            } else
            {
                processProgress.setValue(value);
                processProgress.setString(value > 0 ? (processProgress.getValue() +" / "+ processProgress.getMaximum()) : "");
            }
        }
    }
    
    /**
     * @param text
     */
    public synchronized void setDesc(final String text)
    {
        desc.setText(text);
    }
    
    /**
     * 
     */
    public synchronized void processDone()
    {

        desc.setText(" ");
        processProgress.setMinimum(0);
        processProgress.setMaximum(0);
        processProgress.setValue(0);
        processProgress.setString(" ");
        closeBtn.setText("Done");
    }
    
    public JButton getCloseBtn()
    {
        return closeBtn;
    }

    /**
     * @return the isProcessPercent
     */
    public boolean isProcessPercent()
    {
        return isProcessPercent;
    }

    /**
     * @param isProcessPercent the isProcessPercent to set
     */
    public void setProcessPercent(boolean isProcessPercent)
    {
        this.isProcessPercent = isProcessPercent;
    }
    
    public JProgressBar getProcessProgress()
    {
        return processProgress;
    }
}
