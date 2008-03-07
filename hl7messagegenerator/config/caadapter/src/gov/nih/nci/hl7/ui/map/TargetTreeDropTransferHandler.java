/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/map/TargetTreeDropTransferHandler.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.ui.map;

import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.ui.dragdrop.HL7SDKDropTargetAdapter;
import gov.nih.nci.hl7.ui.dragdrop.TransferableNode;
import gov.nih.nci.hl7.ui.jgraph.UIHelper;
import gov.nih.nci.hl7.ui.tree.DefaultSourceTreeNode;
import gov.nih.nci.hl7.ui.tree.MappableNode;
import gov.nih.nci.hl7.ui.tree.TreeDefaultDropTransferHandler;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.validation.MapLinkValidator;
import gov.nih.nci.hl7.validation.ValidatorResults;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;

/**
 * This class handles drop-related data manipulation for target tree on the mapping panel.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:44 $
 */
public class TargetTreeDropTransferHandler extends TreeDefaultDropTransferHandler
{
	private MappingDataManager mappingDataMananger;
	public TargetTreeDropTransferHandler(JTree tree, MappingDataManager mappingDataMananger)
	{
		this(tree, mappingDataMananger, DnDConstants.ACTION_MOVE);
	}

	public TargetTreeDropTransferHandler(JTree tree, MappingDataManager mappingDataMananger, int action)
	{
		super(tree, action);
		this.mappingDataMananger = mappingDataMananger;
	}

	/**
	 * set up the drag and drop listeners. This must be called
	 * after the constructor.
	 */
	protected void initDragAndDrop()
	{
		TreeCellRenderer cellRenderer = this.getTree().getCellRenderer();
		if (cellRenderer instanceof DefaultTreeCellRenderer)
		{
			DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) cellRenderer;
			this.plafSelectionColor = renderer.getBackgroundSelectionColor();
		}
		else
		{
			this.plafSelectionColor = Color.blue;
		}
		//set up drop stuff
		this.dropTargetAdapter = new HL7SDKDropTargetAdapter(this,
				acceptableDropAction,
				acceptableDropFlavors,
				preferredLocalFlavors);

		// component, ops, listener, accepting
		this.dropTarget = new DropTarget(this.getTree(),
				acceptableDropAction,
				this.dropTargetAdapter,
				true);
		this.dropTarget.setActive(true);
	}

	/**
	 * Called by the DropTargetAdapter in dragEnter, dragOver and
	 * dragActionChanged
	 */
	public void dragUnderFeedback(boolean ok, DropTargetDragEvent e)
	{
		TreeCellRenderer cellRenderer = this.getTree().getCellRenderer();
		if (cellRenderer instanceof DefaultTreeCellRenderer)
		{
			DefaultTreeCellRenderer renderer =
					(DefaultTreeCellRenderer) cellRenderer;
			if (ok)
			{
				renderer.setBackgroundSelectionColor(this.plafSelectionColor);
				this.drawFeedback = true;
			}
			else
			{
				renderer.setBackgroundSelectionColor(Color.red);
			}
		}

//comments out so that when drag over a folder it will not expand
		Point p = e.getLocation();
		TreePath path = this.getTree().getPathForLocation(p.x, p.y);
		if (path != null)
		{
			this.getTree().setSelectionPath(path);
//	        if(this.getTree().isExpanded(path) == false)
//		    this.getTree().expandPath(path);
		}
	}

	/**
	 * Called by the DropTargetAdapter in dragEnter, dragOver and
	 * dragActionChanged.
	 * Current implementation only accept DefaultSourceTreeNode as the possible transferable data.
	 * In future, if on the manipulation of Target Tree itself, please sub this class.
	 */
	public boolean isDropOk(DropTargetDragEvent e)
	{
		Point p = e.getLocation();
//		System.out.println("e.getTransferable() is of type " + e.getTransferable().getClass().getName());
		TransferableNode transferableNode = obtainTransferableNode(e);
		if(transferableNode==null)
		{
			return false;
		}
//		java.util.List transferredDataList = transferableNode.getSelectionList();
		TreePath path = this.getTree().getPathForLocation(p.x, p.y);
		if (path == null)
		{
			return false;
		}
		DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) path.getLastPathComponent();

		if(targetNode instanceof MappableNode)
		{//only allows node that is not being mapped, that is, target node could only be mapped once.
			MappableNode mappableNode = (MappableNode) targetNode;
			if(mappableNode.isMapped())
			{
				return false;
			}
		}

		boolean result = false;
		if(isDataContainsTargetClassObject(transferableNode, DefaultGraphCell.class))
		{
			result = true;
		}
		else if(isDataContainsTargetClassObject(transferableNode, DefaultSourceTreeNode.class))
		{
			DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) transferableNode.getSelectionList().get(0);
			MapLinkValidator validator = new MapLinkValidator(sourceNode.getUserObject(), targetNode.getUserObject());
			ValidatorResults validatorResult = validator.validate();
			Object targetUserObject = targetNode.getUserObject();
			if(targetUserObject instanceof MetaObject)
			{//further validate if the target object itself is mappable or not.
				validatorResult.addValidatorResults(MapLinkValidator.isMetaObjectMappable((MetaObject) targetUserObject));
			}
			result = validatorResult.isValid();
		}

//		int size = transferredDataList.size();
//		for(int i=0; i<size; i++)
//		{
//			Object transferData = transferredDataList.get(i);
//			/**
//			 * if node is not null and the transfer data is of type DefaultSourceTreeNode
//			 */
//			if (node != null && (transferData instanceof DefaultSourceTreeNode))
//			{
//	//            Debug.println("Will Return true!");
//				result = true;
//			}
//			else
//			{
//	//            Debug.println("Will Return false!");
//				result = false;
//				break;
//			}
//		}
		return result;
	}

	/**
	 * Called by the DropTargetAdapter in dragExit and drop
	 */
	public void undoDragUnderFeedback()
	{
		this.getTree().clearSelection();
		TreeCellRenderer cellRenderer = this.getTree().getCellRenderer();
		if (cellRenderer instanceof DefaultTreeCellRenderer)
		{
			DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) cellRenderer;
			renderer.setBackgroundSelectionColor(this.plafSelectionColor);
		}
		this.drawFeedback = false;
	}

	/**
	 * Called by the DropTargetAdapter in drop
	 * return true if add action succeeded
	 * otherwise return false
	 */
	public boolean setDropData(Object transferredData, DropTargetDropEvent e, DataFlavor chosen)
	{
		boolean isSuccess = false;
		Point p = e.getLocation();
		TreePath path = this.getTree().getPathForLocation(p.x, p.y);
		if (path == null)
		{
//			System.out.println(this.getClass() + " path is null. cannot find the exact path. Going to find closest path.");
			path = this.getTree().getClosestPathForLocation(p.x, p.y);
			if (path == null)
			{
//				System.out.println(this.getClass() + " path is null. Even cannot find the closest path. setDropData() will reject drop.");
				return false;
			}
		}
		DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) path.getLastPathComponent();
		try
		{
			TransferableNode dragSourceObjectSelection = (TransferableNode) transferredData;
			java.util.List dragSourceObjectList = dragSourceObjectSelection.getSelectionList();
			if (dragSourceObjectList == null || dragSourceObjectList.size() < 1)
			{
//				DesktopController.showMessage("No Selected Object is found in this Drop action.");
				return false;
			}

			if(isDataContainsTargetClassObject(dragSourceObjectSelection, DefaultGraphCell.class))
			{
				return processCellsDrop(dragSourceObjectSelection, (MappableNode) targetNode);
			}

			int size = dragSourceObjectList.size();
			for (int i = 0; i < size; i++)
			{
				DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode) dragSourceObjectList.get(i);
//				if (targetNode.getChildCount() > 0)
//				{// the user tries to drop on a parent.
//					JOptionPane.showMessageDialog(getTree().getRootPane().getParent(),
//							"You can only drop on leaf nodes.",
//							"Mapping Error",
//							JOptionPane.ERROR_MESSAGE);
//				}
				if (targetNode instanceof MappableNode && ((MappableNode) targetNode).isMapped())
				{//the target has a map already.
					JOptionPane.showMessageDialog(getTree().getRootPane().getParent(),
							"The target you selected already has a map.",
							"Mapping Error",
							JOptionPane.ERROR_MESSAGE);
				}
//				else if (sourceNode instanceof MappableNode && ((MappableNode) sourceNode).isMapped())
//				{//the target has a map already.
//					JOptionPane.showMessageDialog(getTree().getRootPane().getParent(),
//							"The source you selected already has a map.",
//							"Mapping Error",
//							JOptionPane.ERROR_MESSAGE);
//				}
				else
				{// we have a valid map, so go to map it!
					if(sourceNode instanceof MappableNode && targetNode instanceof MappableNode)
					{
						isSuccess = mappingDataMananger.createMapping((MappableNode)sourceNode, (MappableNode)targetNode);
					}
					else
					{
						JOptionPane.showMessageDialog(getTree().getRootPane().getParent(),
								"The target or source you selected is not right data type.",
								"Mapping Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				if (isSuccess)
				{
//					System.out.println("Dropped " + sourceNode.getUserObject().toString() +
//							" on " + targetNode.getUserObject().toString());
				}
			}//end of for
		}
//		catch (ValidationException ve)
//		{
//			System.out.println(this.getClass(), ve.getMessage());
//			System.out.println(ve.getClass().getName());
//			ErrorPane.showDialog(ve);
//			isSuccess = false;
//		}
		catch (Exception exp)
		{
//			System.out.println(this.getClass() + " " + exp.getMessage());
//			System.out.println(exp.getClass().getName());
//			ErrorPane.showDialog(exp);
			Log.logException(this, exp);
			isSuccess = false;
		}
		finally
		{
			return isSuccess;
		}
	}

	private boolean processCellsDrop(TransferableNode dragSourceObjectSelection, MappableNode targetNode)
	{
		boolean isSuccess = false;
		//collect the list of output ports of the function to ask for user selection
		ArrayList functionOutputPortList = new ArrayList();
		java.util.List dragSourceObjectList = dragSourceObjectSelection.getSelectionList();
		int size = dragSourceObjectList.size();
		for(int i=0; i<size; i++)
		{
			Object obj = dragSourceObjectList.get(i);
			if((obj instanceof DefaultPort) && UIHelper.isPortTypeMatch((DefaultPort) obj, false)
					&& !UIHelper.isPortMapped((DefaultPort) obj))
			{//the list only contains non-mapped port
				functionOutputPortList.add(obj);
			}
		}

		if(functionOutputPortList.size()==1)
		{//no need to ask users to select.
			this.mappingDataMananger.createMapping((MappableNode) functionOutputPortList.get(0), targetNode);
		}
		else if(functionOutputPortList.size()>1)
		{
			Object choice = JOptionPane.showInputDialog(getParentComponent(),
                "Select one output paramater of the function to be mapped.",
                "Select Function Output Parameter",
                JOptionPane.QUESTION_MESSAGE, null,
                functionOutputPortList.toArray(),
                functionOutputPortList.get(0));
			if(choice!=null)
			{
				this.mappingDataMananger.createMapping((MappableNode) choice, targetNode);
			}
			else
			{
				JOptionPane.showMessageDialog(getParentComponent(), "User cancelled this mapping action.");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(getParentComponent(), "The specified function does not have any available output parameter to be mapped to.");
		}
		return isSuccess;
	}

	private Component getParentComponent()
	{
		JRootPane rootPane = getTree().getRootPane();
		Component parentComponent = null;
		if (rootPane != null)
		{
			parentComponent = rootPane.getParent();
		}
		return parentComponent;
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.28  2006/08/02 18:44:23  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.27  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.26  2006/01/03 18:56:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.25  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.24  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.23  2005/12/23 16:37:59  jiangsc
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/12/14 21:37:17  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/11/29 16:23:54  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/11/23 19:48:52  jiangsc
 * HISTORY      : Enhancement on mapping validations.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/11/03 22:39:35  jiangsc
 * HISTORY      : Enhance only target mappings.
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/11/02 20:23:56  jiangsc
 * HISTORY      : Enhanced to select only not-mapped port
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/10/18 13:35:26  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/10/05 17:23:47  giordanm
 * HISTORY      : CSV validation work.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/09/16 23:18:56  chene
 * HISTORY      : Database prototype GUI support, but can not be loaded
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/09/08 19:37:03  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/08/26 14:53:24  chene
 * HISTORY      : Add isValidated method into ValidatorResults
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/08/25 22:40:11  jiangsc
 * HISTORY      : Enhanced mapping validation.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/25 14:07:38  jiangsc
 * HISTORY      : Minor fix to display OptionPane nicer
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/24 22:28:42  jiangsc
 * HISTORY      : Enhanced JGraph implementation;
 * HISTORY      : Save point of CSV and HSM navigation update;
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/19 20:25:20  jiangsc
 * HISTORY      : Loose the restriction on mappable.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/04 18:06:26  jiangsc
 * HISTORY      : Updated class description in comments
 * HISTORY      :
 */