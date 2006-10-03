/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/LinkSelectionHighlighter.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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


package gov.nih.nci.hl7.ui.jgraph;

import gov.nih.nci.hl7.ui.jgraph.graph.MappingViewCommonComponent;
import gov.nih.nci.hl7.ui.map.MappingDataManager;
import gov.nih.nci.hl7.ui.map.MappingPanel;
import gov.nih.nci.hl7.ui.map.SourceTree;
import gov.nih.nci.hl7.ui.map.TargetTree;
import org.jgraph.JGraph;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;

/**
 * This class defines a highlighter class for graph presentation.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:43 $
 */
public class LinkSelectionHighlighter extends MouseAdapter implements GraphSelectionListener, TreeSelectionListener
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: LinkSelectionHighlighter.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/jgraph/LinkSelectionHighlighter.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	private MappingPanel mappingPanel;
	private JGraph graph;
	private boolean graphInSelection = false;
	private boolean graphInClearSelectionMode = false;
	private boolean sourceTreeInSelection = false;
	private boolean targetTreeInSelection = false;

//	private boolean anythingInDragDropMode = false;

//	private static final short CLEAR_SELECTION_GRAPH = 1;
//	private static final short CLEAR_SELECTION_SOURCE_TREE = 2;
//	private static final short CLEAR_SELECTION_TARGET_TREE = 3;

	public LinkSelectionHighlighter(MappingPanel mappingPanel, JGraph graph)
	{
		this.mappingPanel = mappingPanel;
		this.graph = graph;
		initialize();
	}

	private void initialize()
	{
		if(mappingPanel!=null)
		{
//			mappingPanel.removeMouseListener(this);
//			mappingPanel.addMouseListener(this);
			JTree tree = mappingPanel.getSourceTree();
			if(tree!=null)
			{
				tree.removeMouseListener(this);
				tree.addMouseListener(this);
			}
			tree = mappingPanel.getTargetTree();
			if(tree!=null)
			{
				tree.removeMouseListener(this);
				tree.addMouseListener(this);
			}
		}
		if(graph!=null)
		{
			graph.addMouseListener(this);
		}
	}

//	public LinkSelectionHighlighter(MappingPanel mappingPanel)
//	{
//		this.mappingPanel = mappingPanel;
//	}
//
//	void setGraph(JGraph newGraph)
//	{
//		graph = newGraph;
//	}

//	private void clearSelections(short clearWhichSelection)
//	{
//		switch(clearWhichSelection)
//		{
//			case CLEAR_SELECTION_GRAPH:
//				graph.clearSelection();
//				break;
//		}
//		mappingPanel.getSourceTree().clearSelection();
//		mappingPanel.getTargetTree().clearSelection();
//	}

	/**
	 * Called whenever the value of the selection changes.
	 *
	 * @param e the event that characterizes the change.
	 */
	public void valueChanged(GraphSelectionEvent e)
	{
//		Log.logInfo(this, "A new Graph Cell is selected. '" + (e == null ? e : e.getCell()) + "'");
		if (mappingPanel.isInDragDropMode())
		{//if in dragging mode, ignore.
			return;
		}
		if(graphInSelection)
		{
//			Log.logInfo(this, "In graph selection mode, just a re-entry, so ignore.");
			return;
		}
		//clear tree selections if and only if the call is NOT orignated from any tree node selection
		if(sourceTreeInSelection)
		{//clear the opposite
			mappingPanel.getTargetTree().clearSelection();
		}

		if(targetTreeInSelection)
		{//clear the opposite
			mappingPanel.getSourceTree().clearSelection();
		}

		graphInSelection = true;

		if(!sourceTreeInSelection && !targetTreeInSelection)
		{
			mappingPanel.getTargetTree().clearSelection();
			mappingPanel.getSourceTree().clearSelection();
		}
		if(!isAClearSelectionEvent(e))
		{//ignore if it is a clear selection event
			Object obj = e.getCell();
			if (!graphInClearSelectionMode && obj instanceof DefaultEdge)
			{//only handles edge, when graph is NOT in CLEAR selection mode.
				DefaultEdge edge = (DefaultEdge) obj;
				Object source = edge.getSource();
				Object target = edge.getTarget();

				if(!sourceTreeInSelection)
				{//manually highlight if and only if it is orignated from graph selection.
					Object sourceUserObject = getUserObject(source);
					highlightTreeNodeInTree(mappingPanel.getSourceTree(), sourceUserObject);
				}

				if(!targetTreeInSelection)
				{//manually highlight if and only if it is orignated from graph selection.
					Object targetUserObject = getUserObject(target);
					highlightTreeNodeInTree(mappingPanel.getTargetTree(), targetUserObject);
				}
			}
		}
		graphInSelection = false;
	}

	/**
	 * If it is clear selection event, the isAddedXXX() will return false, so the return will be true, i.e., it is a clear selection event.
	 * @param event
	 * @return true if it is a clear selection event; otherwise, return false.
	 */
	private boolean isAClearSelectionEvent(EventObject event)
	{
		boolean result = false;
		if(event instanceof GraphSelectionEvent)
		{
			result = !((GraphSelectionEvent) event).isAddedCell();
		}
		else if(event instanceof TreeSelectionEvent)
		{
			result = !((TreeSelectionEvent) event).isAddedPath();
		}
		return result;
	}

	private Object getUserObject(Object graphOrTreeNode)
	{
		if(graphOrTreeNode instanceof DefaultPort)
		{
			DefaultMutableTreeNode parentCell = (DefaultMutableTreeNode)((DefaultPort) graphOrTreeNode).getParent();
			return getUserObject(parentCell);
		}
		else if(graphOrTreeNode instanceof DefaultMutableTreeNode)
		{
			return ((DefaultMutableTreeNode)graphOrTreeNode).getUserObject();
		}
		else
		{
			return null;
		}
	}

	private void highlightTreeNodeInTree(JTree tree, Object object)
	{
		if ((!(object instanceof DefaultGraphCell) && (object instanceof DefaultMutableTreeNode)))
		{//screen out possible graph cell but just leave pure tree node to be highlighted
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) object;
			TreePath treePath = new TreePath(treeNode.getPath());
//			tree.scrollPathToVisible(treePath);
			tree.setSelectionPath(treePath);
		}
	}

	/**
	 * Called whenever the value of the selection changes.
	 *
	 * @param e the event that characterizes the change.
	 */
	public void valueChanged(TreeSelectionEvent e)
	{
		if (mappingPanel.isInDragDropMode())
		{//if in dragging mode, ignore.
			return;
		}
		Object eventSource = e.getSource();
		TreePath path = e.getPath();
		Object node = (path==null)? null : path.getLastPathComponent();
//		Log.logInfo(this, "TreeSelectionSource:'" + (eventSource ==null? "null" : eventSource.getClass().getName()) + "'");
		if(graphInSelection || sourceTreeInSelection || targetTreeInSelection)
		{//if graph is in selection, no need to execute further logic; otherwise, we may run into an indefinite loop.
//			Log.logInfo(this, "In Graph or tree selection mode, so just ignore.");
			return;
		}
		else
		{
			if(node==null)
			{
				return;
			}

			String searchMode = null;
			if(eventSource instanceof SourceTree)
			{
				searchMode = MappingViewCommonComponent.SEARCH_BY_SOURCE_NODE;
				//notify that tree is selection process.
				sourceTreeInSelection = true;
				mappingPanel.getTargetTree().clearSelection();
			}
			else if(eventSource instanceof TargetTree)
			{
				searchMode = MappingViewCommonComponent.SEARCH_BY_TARGET_NODE;
				//notify that tree is selection process.
				targetTreeInSelection = true;
				mappingPanel.getSourceTree().clearSelection();
			}

			graphInClearSelectionMode = true;
			graph.clearSelection();
			graphInClearSelectionMode = false;

			if(!isAClearSelectionEvent(e) && searchMode!=null)
			{//if not a clear selection event and searchMode is not null.
				MappingDataManager dataManager = mappingPanel.getMappingDataManager();
				List<MappingViewCommonComponent> compList = dataManager.findMappingViewCommonComponentList(node, searchMode);
				int size = compList.size();
				for(int i=0; i<size; i++)
				{
					MappingViewCommonComponent comp = compList.get(i);
					DefaultEdge linkEdge = comp.getLinkEdge();
					if(graph!=null)
					{
						graph.setSelectionCell(linkEdge);
					}
//					else
//					{//just highlight the tree nodes.
//						GraphSelectionEvent event = new GraphSelectionEvent(this, new Object[]{linkEdge}, new boolean[]{false});
//						valueChanged(event);
//					}

//					//highlight the other tree node correspondingly
//					JTree treeToBeHighlighted = null;
//					DefaultMutableTreeNode treeNodeToBeHighlighted = null;
//					if(searchMode == MappingViewCommonComponent.SEARCH_BY_TARGET_NODE)
//					{//to highlight source tree
//						treeToBeHighlighted = mappingPanel.getSourceTree();
//					}
				}
			}//end of if(searchMode!=null)
			if (eventSource instanceof SourceTree)
			{
				//notify that tree is end of selection process.
				sourceTreeInSelection = false;
			}
			else if (eventSource instanceof TargetTree)
			{
				//notify that tree is end of selection process.
				targetTreeInSelection = false;
			}
		}
	}

	/**
	 * Invoked when the mouse has been clicked on a component.
	 */
	public void mouseClicked(MouseEvent e)
	{
		//if mouse clicked, it is definitely not in drag and drop.
		boolean previousValue = mappingPanel.isInDragDropMode();
		mappingPanel.setInDragDropMode(false);
		if(previousValue)
		{//previously in drag and drop mode, so to ensure the highlight back up, generate the corresponding tree or graph selection event.
			Object source = e.getSource();
//			String name = source == null ? "null" : source.getClass().getName();
//			System.out.println("Source is ' " + name + "'," + "LinkSelectionHighlighter's mouseClicked is called");
			//following code tries to trigger the valueChanged() methods above to mimic and restore the "highlight" command
			if(source instanceof JGraph)
			{
				JGraph mGraph = (JGraph) source;
				mGraph.setSelectionCells(mGraph.getSelectionCells());
//				GraphSelectionEvent event = new GraphSelectionEvent(source, new Object[]{}, )
			}
			else if(source instanceof JTree)
			{
				JTree mTree = (JTree) source;
				//following code does not trigger valueChanged(TreeSelectionEvent) above.
				//mTree.setSelectionPaths(mTree.getSelectionPaths());
				//mTree.setSelectionRows(mTree.getSelectionRows());
				TreePath[] paths = mTree.getSelectionPaths();
				int size = paths==null? 0 : paths.length;
				if(size>0)
				{
					boolean[] areNew = new boolean[size];
					for(int i=0; i<size; i++)
					{
						areNew[i] = true;
					}
					TreePath leadingPath = mTree.getLeadSelectionPath();
					TreeSelectionEvent event = new TreeSelectionEvent(source, paths, areNew, null, leadingPath);
					valueChanged(event);
				}
			}
		}
	}

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.18  2006/08/02 18:44:22  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.17  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/12/29 23:06:17  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/12/07 19:20:50  jiangsc
 * HISTORY      : With enhanced functions.
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/11/23 20:03:59  jiangsc
 * HISTORY      : Enhancement on highlight functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/11/09 23:05:51  jiangsc
 * HISTORY      : Back to previous version.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/10/24 20:31:00  jiangsc
 * HISTORY      : Turned off auto-scroll feature to comprise mapping issue.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/10/21 15:12:07  jiangsc
 * HISTORY      : Removed some comments lines
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/10/18 17:01:03  jiangsc
 * HISTORY      : Changed one function signature in DragDrop component;
 * HISTORY      : Enhanced drag-drop status monitoring in MappingPanel;
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/10/07 22:20:58  jiangsc
 * HISTORY      : Save point
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/10/03 19:33:59  jiangsc
 * HISTORY      : Implement highlighting tree nodes upon graph selection.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/09/27 21:47:58  jiangsc
 * HISTORY      : Customized edge rendering and initially added a link highlighter class.
 * HISTORY      :
 */
