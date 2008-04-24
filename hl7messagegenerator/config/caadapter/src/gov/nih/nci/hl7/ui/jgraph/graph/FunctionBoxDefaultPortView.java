/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/graph/FunctionBoxDefaultPortView.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 * The caAdapter Software License, Version 1.3
 * Copyright Notice.
 * 
 * Copyright 2006 SAIC. This software was developed in conjunction with the National Cancer Institute. To the extent government employees are co-authors, any rights in such works are subject to Title 17 of the United States Code, section 105. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the Copyright Notice above, this list of conditions, and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 * 
 * 
 * "This product includes software developed by the SAIC and the National Cancer Institute."
 * 
 * 
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear. 
 * 
 * 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
 * 
 * 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
 * 
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT, THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */


package gov.nih.nci.hl7.ui.jgraph.graph;

import org.jgraph.JGraph;
import org.jgraph.graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * This class defines a custom view class associated with the custom Port definition class.
 * @see FunctionBoxDefaultPort
 * @see gov.nih.nci.hl7.ui.jgraph.MiddlePanelJGraphViewFactory
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class FunctionBoxDefaultPortView extends PortView
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: FunctionBoxDefaultPortView.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/graph/FunctionBoxDefaultPortView.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	/**
	 * Default size for all ports is 6.
	 */
	public static transient int MY_SIZE = 10;
	public static transient FunctionBoxDefaultPortRenderer renderer = new FunctionBoxDefaultPortRenderer();

	/**
	 * Constructs an empty portview.
	 */
	public FunctionBoxDefaultPortView()
	{
		super();
	}

	/**
	 * Constructs a view that holds a reference to the specified cell, anchor
	 * and parent vertex.
	 *
	 * @param cell reference to the cell in the model
	 */
	public FunctionBoxDefaultPortView(Object cell)
	{
		super(cell);
	}

	/**
	 * Returns the bounds for the port view.
	 */
	public Rectangle2D getBounds()
	{
		super.SIZE = MY_SIZE;
		return super.getBounds();
//		Rectangle2D bounds = getAttributes().createRect(getLocation(null));
//		if (bounds != null)
//			bounds.setFrame(bounds.getX() - SIZE / 2, bounds.getY() - SIZE / 2,
//					bounds.getWidth() + SIZE, bounds.getHeight() + SIZE);
//		return bounds;
	}

	/**
	 * Returns a renderer for the class.
	 */
	public CellViewRenderer getRenderer()
	{
		return FunctionBoxDefaultPortView.renderer;    //To change body of overridden methods use File | Settings | File Templates.
	}
}

class FunctionBoxDefaultPortRenderer extends JComponent implements CellViewRenderer, Serializable
{
	/**
	 * Cache the current graph for drawing
	 */
	protected transient JGraph graph;

	/**
	 * Cache the current edgeview for drawing.
	 */
	protected transient PortView view;

	/**
	 * Cached hasFocus and selected value.
	 */
	protected transient boolean hasFocus, selected, preview;

	private transient Color defaultGradientColor = new Color(219, 219, 219);//new Color(255, 255, 255);
	private transient Color defaultSelectionBorderColor = new Color(219, 10, 10);//new Color(255, 255, 255);
    private transient Color defaultMappedFillColor = new Color(128, 128, 128);//new Color(81, 81, 255);


	/**
	 * Cached default foreground and default background.
	 */
	transient protected Color defaultForeground;
	transient protected Color defaultBackground;
	transient protected Color defaultBorderColor;

	transient protected Color bordercolor;

	transient protected Color gradientColor;

	/**
	 * Cached borderwidth.
	 */
	transient protected int borderWidth;

	public FunctionBoxDefaultPortRenderer()
	{
		defaultForeground = (UIManager.getColor("MenuItem.selectionBackground"));
		defaultBackground = (UIManager.getColor("Tree.selectionBorderColor"));
		defaultBorderColor = new Color(0, 0, 0);//(UIManager.getColor("Tree.selectionBorderColor"));
	}

	/**
	 * Configure and return the renderer based on the passed in
	 * components. The value is typically set from messaging the
	 * graph with <code>convertValueToString</code>.
	 * We recommend you check the value's class and throw an
	 * illegal argument exception if it's not correct.
	 *
	 * @param graph   the graph that that defines the rendering context.
	 * @param view    the view that should be rendered.
	 * @param sel     whether the object is selected.
	 * @param focus   whether the object has the focus.
	 * @param preview whether we are drawing a preview.
	 * @return	the component used to render the value.
	 */
	public Component getRendererComponent(JGraph graph, CellView view, boolean sel, boolean focus, boolean preview)
	{
		if (view instanceof PortView && graph != null)
		{
			this.graph = graph;
			this.view = (PortView) view;
			installAttributes(this.view);
			this.hasFocus = focus;
			this.selected = sel;
			this.preview = preview;
			return this;
		}
		return null;
	}

	public void paint(Graphics g)
	{
//			Log.logInfo(this, "In FunctionBoxDefaultPortRenderer.paint() method.");
		g.setColor(graph.getBackground());
		g.setXORMode(graph.getBackground());
		super.paint(g);
//			((JComponent)super).paint(g);
//			paintFromCustomizedRenderer(g);
		paintCustomizedPort(g);
		paintSelectionBorder(g);
	}

//	private void paintFromCustomizedRenderer(Graphics g)
//	{
//		Graphics2D g2 = (Graphics2D) g;
//		// construct the triangle
//		Polygon triangle = getTrianglePolygon(false, true, true);
//
////            Log.logInfo(this, "Triangle is '" + triangle + "'.");
//		boolean tmp = selected;
//
//		if (!preview && super.isOpaque())
//		{
//			g.setColor(super.getBackground());
//			if (gradientColor != null && !preview)
//			{
//				setOpaque(false);
//				g2.setPaint(new GradientPaint(0, 0, getBackground(), getWidth(),
//						getHeight(), gradientColor, true));
//			}
//			g.fillPolygon(triangle);
//		}
//		try
//		{
//			setBorder(null);
//			setOpaque(false);
//			selected = false;
//			super.paint(g);
//		}
//		finally
//		{
//			selected = tmp;
//		}
//
//		if (selected)// || preview)
//		{
//			g2.setStroke(GraphConstants.SELECTION_STROKE);
//			g.setColor(graph.getHighlightColor());
//			g.drawPolygon(triangle);
//		}
//		else
//		{
//			g.setColor(Color.BLACK.darker().darker());
//			g.drawPolygon(triangle);
//		}
//	}

	private void paintCustomizedPort(Graphics g)
	{
		/**
		 * Design rationale:
		 * preview 	true: gradientColor;
		 * 			false: selected	true
		 */
		Color oldColor = g.getColor();

		Polygon triangle = null;
		//triangle = getTrianglePolygon(false, true, true);
//		if (preview)
//		{
////				g.fill3DRect(0, 0, d.width, d.height, true);
//			g.setColor(gradientColor.darker().darker());
//			g.fillPolygon(triangle);
//		}
//		else
//		{
//			g.fillPolygon(triangle);
//		}
		boolean offset = (GraphConstants.getOffset(view.getAllAttributes()) != null);
		boolean mapped = isPortMapped();
//		if (selected)
//		{
//            g.setColor(defaultSelectionBorderColor);
//		}
//		else
//		{
//			g.setColor(getForeground());
//		}

		triangle = getTrianglePolygon(false, offset, preview);
		if(preview)
		{
			g.setColor(gradientColor.darker().darker());
			g.fillPolygon(triangle);
		}
		else if(offset)
		{
			g.setColor(getForeground());
			g.fillPolygon(triangle);
		}

		{
			triangle = getTrianglePolygon(false, true, true);
			if (selected)
			{
				g.setColor(defaultSelectionBorderColor);
				g.fillPolygon(triangle);
			}
			else
			{
				if (mapped)
				{
					Color prevColor = g.getColor();
					g.setColor(defaultMappedFillColor);
					g.fillPolygon(triangle);
					//draw the border
					g.setColor(prevColor);
					g.drawPolygon(triangle);
				}
				else
				{
					g.setColor(getForeground());
					g.drawPolygon(triangle);
				}
			}
		}
//		if (!offset)
//		{
////				g.fillRect(1, 1, d.width - 2, d.height - 2);
//			triangle = getTrianglePolygon(false, false, true);
//			g.fillPolygon(triangle);
//		}
//		else if (!preview)
//		{
////				g.drawRect(1, 1, d.width - 3, d.height - 3);
//			triangle = getTrianglePolygon(false, true, false);
//			g.drawPolygon(triangle);
//		}

//		Log.logInfo(this, "is selected? " + selected);
//		Log.logInfo(this, "is offset? " + offset);
//		Log.logInfo(this, "is Preview? " + preview);
//		Log.logInfo(this, "is Port mapped? " + mapped);
		//return the old settings
		g.setColor(oldColor);
	}

	/**
	 * Provided for subclassers to paint a selection border.
	 */
	protected void paintSelectionBorder(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Stroke previousStroke = g2.getStroke();
		g2.setStroke(GraphConstants.SELECTION_STROKE);
		if (hasFocus && selected)
			g.setColor(graph.getLockedHandleColor());
		else if (selected)
			g.setColor(graph.getHighlightColor());
		boolean offset = (GraphConstants.getOffset(view.getAllAttributes()) != null);
		Polygon triangle = getTrianglePolygon(true, offset, preview);

		if (selected)
		{
			g.drawPolygon(triangle);
		}
		g2.setStroke(previousStroke);
	}

	private boolean isPortMapped()
	{
		boolean result = false;
		Object portObj = this.view.getCell();
		if(portObj instanceof DefaultPort)
		{
			DefaultPort port = (DefaultPort) portObj;
			Set edges = port.getEdges();
			if(edges!=null && !edges.isEmpty())
			{
				result = true;
			}
		}
		return result;
	}

	/**
	 * Install the attributes of specified cell in this renderer instance. This
	 * means, retrieve every published key from the cells hashtable and set
	 * global variables or superclass properties accordingly.
	 *
	 * @param view the cell view to retrieve the attribute values from.
	 */
	protected void installAttributes(CellView view)
	{
		Map map = view.getAllAttributes();
//		setOpaque(GraphConstants.isOpaque(map));
//		setBorder(GraphConstants.getBorder(map));
//		bordercolor = GraphConstants.getBorderColor(map);
//		if(bordercolor==null)
//		{
//			bordercolor = defaultBorderColor;
//		}
//		borderWidth = Math.max(1, Math.round(GraphConstants.getLineWidth(map)));
//		if (getBorder() == null && bordercolor != null)
//		{
//			setBorder(BorderFactory.createLineBorder(bordercolor, borderWidth));
//		}
		Color foreground = GraphConstants.getForeground(map);
		setForeground((foreground != null) ? foreground : defaultForeground);
		Color background = GraphConstants.getBackground(map);
		setBackground((background != null) ? background : defaultBackground);
		Color gradientColor = GraphConstants.getGradientColor(map);
		setGradientColor((gradientColor==null) ? defaultGradientColor : gradientColor);
		Font font = GraphConstants.getFont(map);
		if(font!=null)
		{
			setFont(font);
		}
	}
//	protected void paintBorder(Graphics g)
//	{
//		// TODO This needs to be implemented to paint a non-rectangular
//		// border
////			g.setColor(Color.BLACK);
////			Polygon triangle = getTrianglePolygon(true);
////			g.drawPolygon(triangle);
//
//		super.paintBorder(g);
//	}

	private Polygon getTrianglePolygon(boolean forBorder, boolean offset, boolean preview)
	{
		int b = 0;
		Dimension d = getSize();
//			Log.logInfo(this, "Default size is: '" + d + "'.");
		if (forBorder)
		{//may be changed to default border width
			b = 4;//borderWidth
		}
		Polygon result = null;
		// construct the triangle
		int start = 0;
		int width = d.width - b; // allow for border
		int height = d.height - b; // allow for border
		int halfHeight = height / 2;

		if (!offset)
		{
			start = 1;
			width -= 3;
			height -= 3;
		}
		if (!preview)
		{
			start = 1;
			width -= 4;
			height -= 4;
		}

		int[] xpoints = {start, width, start};
		int[] ypoints = {start, halfHeight, height};
		result = new Polygon(xpoints, ypoints, 3);
		return result;
	}

	public Color getGradientColor()
	{
		return gradientColor;
	}

	public void setGradientColor(Color gradientColor)
	{
		this.gradientColor = gradientColor;
	}

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.14  2006/08/02 18:44:22  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.13  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/10/25 22:00:43  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/25 15:17:08  jiangsc
 * HISTORY      : Added description.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/19 22:28:06  jiangsc
 * HISTORY      : 1) Renamed FunctionalBox to FunctionBox to be consistent;
 * HISTORY      : 2) Added SwingWorker to OpenMapAction;
 * HISTORY      : 3) Save Point for Function Change.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/18 18:41:03  jiangsc
 * HISTORY      : Implemented logic to support parsing of multiple groups within function library
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/15 18:58:47  jiangsc
 * HISTORY      : 1) Reconstucted Menu bars;
 * HISTORY      : 2) Integrated FunctionPane to display property;
 * HISTORY      : 3) Enabled drag and drop functions to mapping panel.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/13 22:04:27  jiangsc
 * HISTORY      : UI Update
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/07/11 22:49:08  jiangsc
 * HISTORY      : View change
 * HISTORY      :
 */