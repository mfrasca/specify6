/* Copyright (C) 2009, University of Kansas Center for Research
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
package edu.ku.brc.specifydroid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.location.GpsSatellite;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Oct 25, 2009
 *
 */
public class SatellitesView extends View
{
    protected final Paint textPaint  = new Paint(); 
    protected final Paint iconPaint  = new Paint(); 
    protected final Paint whitePaint = new Paint(); 
    protected final Paint barPaint   = new Paint(); 
    protected final Paint barOutlinePaint   = new Paint(); 
    
    private HashMap<Integer, SatelliteInfo> satellites = new HashMap<Integer, SatelliteInfo>();
    private Vector<SatelliteInfo>           satList    = new Vector<SatelliteInfo>();
    private Bitmap[] bitmaps = null;
    private int      iconWidth  = 0;
    private int      iconHeight = 0;
    private float    textHeight = 0;
    
    protected int    barWidth   = 14;
    protected int    barGap     = 4;
    
    
    /*private int      scrWidth   = 0;
    private int      scrHeight  = 0;
    private boolean  isPortait  = true;
    
    private int      barWidth   = 6;
    private int      barGap     = 2;*/
    
    private int xGap = 16;
    private int yGap = 8;
    
    private int cols = 0;
    //private int rows = 0;
    
    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SatellitesView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * @param context
     * @param attrs
     */
    public SatellitesView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public SatellitesView(Context context)
    {
        super(context);
    }
    
    
    
    /**
     * @param bitmapids
     */
    public void init(final int[] bitmapids)
    {
        bitmaps = new Bitmap[bitmapids.length];
        for (int i=0;i<bitmapids.length;i++)
        {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), bitmapids[i]);
            iconWidth  = bitmaps[i].getWidth();
            iconHeight = bitmaps[i].getHeight();
        }
        
        this.textPaint.setARGB(128, 255, 255, 255);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(20);
        this.textPaint.setFakeBoldText(true); 

        this.iconPaint.setARGB(80, 255, 255, 255);
        this.iconPaint.setStyle(Style.FILL); 

        this.whitePaint.setARGB(255, 255, 255, 255);
        this.whitePaint.setAntiAlias(true);
        this.whitePaint.setTextSize(12);
        this.whitePaint.setFakeBoldText(true); 
        this.whitePaint.setStyle(Style.STROKE);

        this.barPaint.setARGB(255, 0, 0, 255);
        this.barPaint.setAntiAlias(true);
        /*this.barPaint.setStyle(Style.FILL); 
        Shader shader = new LinearGradient(0, 0, barWidth, 48, new int[] {Color.GREEN, Color.YELLOW, Color.BLUE, Color.parseColor("#E6E6FA"), Color.RED, }, 
                                           new float[]{0.1f, 0.3f, 0.6f, 0.8f, 1.0f}, Shader.TileMode.CLAMP); 
        
        Shader shader1 = new LinearGradient(0, 0, barWidth, 48, Color.GREEN, Color.RED, Shader.TileMode.CLAMP); 
        this.barPaint.setShader(shader);*/  
        
        this.barOutlinePaint.setARGB(128, 255, 255, 255);
        this.barOutlinePaint.setAntiAlias(true);
        this.barOutlinePaint.setStyle(Style.STROKE); 

        
        this.textHeight = this.textPaint.getFontMetrics().ascent + this.textPaint.getFontMetrics().descent;
    }
    
    /* (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(final Canvas canvas)
    {
        super.onDraw(canvas);
        
        this.textHeight = Math.abs(this.textPaint.getFontMetrics().ascent) + this.textPaint.getFontMetrics().descent;
        
        /*RectF rect;
        if (isPortait)
        {
            int h = scrHeight / 2;
            int w = Math.min(h, scrWidth);
            int x = (scrWidth - w) / 2;
            int y = h + ((h - w) / 2);
            rect = new RectF(x, y, x+w, y+w);
        } else
        {
            int w = scrWidth / 2;
            int h = Math.min(w, scrHeight);
            int y = (scrHeight - h) / 2;
            int x = w + ((w - h) / 2);
            rect = new RectF(x, y, x+h, y+h);
        }
        rect.inset(4, 4);
        canvas.drawArc(rect, 0, 360, true, whitePaint);
        */
        
        float x = xGap;
        float y = yGap;
        int   r = 0;
        for (int i=0;i<satList.size();i++)
        {
            SatelliteInfo si        = satList.get(i);
            String        text      = Integer.toString(si.getSatellite().getPrn());
            float         textWidth = textPaint.measureText(text);
            
            float snr     = Math.min(si.getSatellite().getSnr(), 50.0f) / 50.0f;
            int snrOffset = iconHeight - (int)Math.round(snr * iconHeight);
            
            if (false)
            {
                canvas.drawBitmap(bitmaps[0], x, y, iconPaint); // paint with fade
                canvas.save();
                canvas.clipRect(x, y+snrOffset, x+iconWidth, y+iconHeight);
                canvas.drawBitmap(bitmaps[0], x, y, null);
                canvas.restore();
            } else
            {
                float barHeight = snr * iconHeight;
                canvas.drawRect(x, y, x+barWidth, y + iconHeight, barOutlinePaint);
                
                canvas.save();
                canvas.clipRect(x, y + (iconHeight - barHeight), x+barWidth, y + iconHeight);
                canvas.drawRect(x, y, x+barWidth, y + iconHeight, barPaint);
                canvas.restore();
                
                x += barWidth + barGap;
                canvas.drawBitmap(bitmaps[0], x, y, null);
            }
            
            //placeSatellite(rect, canvas, si.getSatellite());
            
            //Log.d("APP", String.format("%2.0f, %2.0f, %2.0f, %2.0f", x, y+snrOffset, x+iconWidth, y+iconHeight));
            
            canvas.drawText(text, x + ((iconWidth - textWidth) / 2), y + iconHeight + (yGap / 2) + this.textHeight, textPaint);
            
            r++;
            if (r >= cols)
            {
                r = 0;
                x = xGap;
                y += iconHeight + yGap + (yGap / 2) + this.textHeight;
                
            } else
            {
                x += iconWidth + xGap;
            }
        }
    }
    
    /**
     * @param rect
     * @param sat
     */
    /*private void placeSatellite(final RectF rect,
                                final Canvas canvas,
                                final GpsSatellite sat)
    {
        double d  = rect.width() / 2;
        double a1 = 360.0f - (sat.getAzimuth() - 90.0); // Convert to cartesian angle
        double a2 = a1 * Math.PI / 180.0;               // Convert to radians
        double x  = d * Math.cos(a2);
        double y  = -1.0 * d * Math.sin(a2);
        String anchor = (sat.getAzimuth() >= 90.0 && sat.getAzimuth() <= 270) ? "n" : "s";
        
        //if (anchor.equals("s"))
        {
            float cx = rect.left + ((float)(x + d));
            float cy = rect.top + ((float)(y + d));
            canvas.drawRect(cx, cy, cx+5, cy+5, whitePaint);
            //Log.d("APP", String.format("%5.2f, %d, %d %s", sat.getAzimuth(), (int)cx, (int)cy, anchor));
        }
        
        double azimuth   = sat.getAzimuth();
        double elevation = sat.getElevation();
        double Lx = Math.cos(Math.PI*azimuth/180.)*Math.cos(Math.PI*elevation/180.);
        double Ly = Math.sin(Math.PI*azimuth/180.)*Math.cos(Math.PI*elevation/180.);
        double Lz = Math.sin(Math.PI*elevation/180);

        Log.d("APP", String.format("%5.2f, %5.2f - %5.2f, %5.2f, %5.2f", sat.getAzimuth(), sat.getElevation(), Lx, Ly, Lz));
        //Log.d("APP", String.format("%d  %d - %5.2f, %d, %d %s", (int)rect.width(), (int)d, sat.getAzimuth(), (int)x, (int)y, anchor));
    }*/
    
    /*
    void drawWireFrame(final RectF rect,
                       final Canvas canvas,
                       final GpsSatellite sat)
    {
        double azimuth   = sat.getAzimuth();
        double elevation = sat.getElevation();
        
        double theta = Math.PI * azimuth / 180.0;
        double phi = Math.PI * elevation / 180.0;
        float cosT = (float) Math.cos(theta), sinT = (float) Math.sin(theta);
        float cosP = (float) Math.cos(phi), sinP = (float) Math.sin(phi);
        float cosTcosP = cosT * cosP, cosTsinP = cosT * sinP, sinTcosP = sinT * cosP, sinTsinP = sinT * sinP;
        
        Point points = new Point();
        int scaleFactor = (int)rect.width() / 4;
        float near = 3;
        int i;
        float nearToObj = 1.5f;

            int x0 = vertices[i].x;
            int y0 = vertices[i].y;
            int z0 = vertices[i].z;
            float x1 = cosT * x0 + sinT * z0;
            float y1 = -sinTsinP * x0 + cosP * y0 + cosTsinP * z0;
            float z1 = cosTcosP * z0 - sinTcosP * x0 - sinP * y0;
            x1 = x1 * near / (z1 + near + nearToObj);
            y1 = y1 * near / (z1 + near + nearToObj);
            points[i] = new Point((int) (width / 2 + scaleFactor * x1 + 0.5), (int) (height / 2
                    - scaleFactor * y1 + 0.5));
            
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.green);
        for (i = 0; i < edges.length; ++i)
        {
            g.drawLine(points[edges[i].a].x, points[edges[i].a].y, points[edges[i].b].x,
                    points[edges[i].b].y);
        }
    }*/
    
    /**
     * @param satellite
     */
    public void updateSatellite(final GpsSatellite satellite)
    {
        SatelliteInfo si = satellites.get(satellite.getPrn());
        if (si == null)
        {
            SatelliteInfo satInfo = new SatelliteInfo(satellite);
            satellites.put(satellite.getPrn(), satInfo);
            satList.add(satInfo);
        } else
        {
            si.setUpdated(true);
        }
    }
    
    /**
     * 
     */
    public void startSatUpdate()
    {
        for (SatelliteInfo si : satList)
        {
            si.setUpdated(false);
        }
    }
    
    /**
     * 
     */
    public void endSatUpdate()
    {
        for (SatelliteInfo si : new Vector<SatelliteInfo>(satList))
        {
            if (!si.isUpdated())
            {
                satList.remove(si);
                satellites.remove(si.getSatellite().getPrn());
            }
        }
        
        Collections.sort(satList);
        updateView();
    }
    
    /**
     * 
     */
    public void updateView()
    {
        invalidate();
        drawableStateChanged();
    }
    
    /**
     * 
     */
    public void clear()
    {
        satellites.clear();
        satList.clear();
    }

    
    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void calColsRows(int widthMeasureSpec, 
                             int heightMeasureSpec)
    {
        //isPortait = widthMeasureSpec < heightMeasureSpec;
        //scrWidth  = widthMeasureSpec;
        //scrHeight = heightMeasureSpec;
        
        //cols = (isPortait ? widthMeasureSpec : (widthMeasureSpec / 2)) / (iconWidth + xGap);
        //rows = (isPortait ? (heightMeasureSpec / 2) : heightMeasureSpec) / (iconHeight + yGap + (int)textHeight + (yGap / 2));
        
        int cellWidth = iconWidth + barGap + barWidth;
        
        cols = widthMeasureSpec / (cellWidth + xGap);
        //rows = heightMeasureSpec / (iconHeight + yGap + (int)textHeight + (yGap / 2));
    }

    /* (non-Javadoc)
     * @see android.view.View#onFinishInflate()
     */
    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
    }

    /* (non-Javadoc)
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        calColsRows(widthMeasureSpec, heightMeasureSpec);
    }

    /* (non-Javadoc)
     * @see android.view.View#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
    }

    /* (non-Javadoc)
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        
        calColsRows(w, h);
    }

    //-------------------------------
    class SatelliteInfo implements Comparable<SatelliteInfo>
    {
        protected boolean      isUpdated;
        protected GpsSatellite satellite;
        /**
         * @param satellite
         */
        public SatelliteInfo(GpsSatellite satellite)
        {
            super();
            this.satellite = satellite;
            this.isUpdated = true;
        }
        
        /**
         * @return the wasUpdated
         */
        public boolean isUpdated()
        {
            return isUpdated;
        }
        /**
         * @param wasUpdated the wasUpdated to set
         */
        public void setUpdated(boolean wasUpdated)
        {
            this.isUpdated = wasUpdated;
        }
        /**
         * @return the satellite
         */
        public GpsSatellite getSatellite()
        {
            return satellite;
        }
        /**
         * @param satellite the satellite to set
         */
        public void setSatellite(GpsSatellite satellite)
        {
            this.satellite = satellite;
        }
        
        public Integer getId()
        {
            return satellite.getPrn();
        }

        /* (non-Javadoc)
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(SatelliteInfo another)
        {
            return getId().compareTo(another.getId());
        }
        
    }
}
