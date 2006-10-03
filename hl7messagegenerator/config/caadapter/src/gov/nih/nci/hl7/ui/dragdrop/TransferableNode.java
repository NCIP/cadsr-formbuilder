/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/dragdrop/TransferableNode.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $
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


package gov.nih.nci.hl7.ui.dragdrop;

import gov.nih.nci.hl7.util.Log;

import java.awt.datatransfer.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * This class is designated to handle data transfer and drag-n-drop, etc., for classes in caAdapter.
 * Due to the fact that possible multi-selecitons may involve in, this class is designed to handle multi-selection.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version     Since caAdapter v1.2
 * revision    $Revision: 1.1 $
 * date        $Date: 2006-10-03 17:38:43 $
 */
public class TransferableNode implements Serializable, ClipboardOwner, Transferable
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create logging mechanism
	 * to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: TransferableNode.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/dragdrop/TransferableNode.java,v 1.1 2006-10-03 17:38:43 marwahah Exp $";

	/**
	 * Following are drag/drop, cut/copy/paste related variables, including data
	 * flavor definition
	 */
	//--begin of drag/drop, cut/copy/paste related variables
	public static final DataFlavor NODE_FLAVOR;
	public static final DataFlavor LOCAL_NODE_FLAVOR;
//	public static final DataFlavor NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Node");

	static
	{
		NODE_FLAVOR = new DataFlavor(TransferableNode.class, "TransferableNode");
		LOCAL_NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + "; class=gov.nih.nci.hl7.ui.dragdrop.TransferableNode", "Local TransferableNode");
	}

	public static final DataFlavor[] transferDataFlavors = {NODE_FLAVOR, LOCAL_NODE_FLAVOR};

	private static final List flavorList = Arrays.asList(transferDataFlavors);
	private DataTransferActionType actionType;
	private List selectionList;
	private boolean isUsingClipboard;
	//--end of drag/drop, cut/copy/paste related variables

	public TransferableNode(List selectionList, DataTransferActionType type, boolean usingClipboard)
	{
		this.selectionList = selectionList;
		this.actionType = type;
		this.isUsingClipboard = usingClipboard;
	}

	/**
	 * Called by Clipboard to notify this object is no longer in clipboard.
	 * @param clipboard
	 * @param contents
	 */
	public void lostOwnership(Clipboard clipboard, Transferable contents)
	{
		Log.logInfo(this, "TransferableNode lost ownership of " + clipboard.getName());
		Log.logInfo(this, "data: " + contents);
		selectionList = null;
		this.actionType = null;
		this.isUsingClipboard = false;
//		clearDataInTransitFlag();
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		/**Implement this java.awt.datatransfer.Transferable method*/
		return (DataFlavor[]) transferDataFlavors.clone();
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		/**Implement this java.awt.datatransfer.Transferable method*/
		return flavorList.contains(flavor);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
	{
		/**Implement this java.awt.datatransfer.Transferable method*/
		if (flavor.equals(NODE_FLAVOR))
		{
//			Log.logInfo(this, getClass() + " the flavor requested is NODE_FLAVOR");
			return this;
		}
		else if (flavor.equals(LOCAL_NODE_FLAVOR))
		{
//			Log.logInfo(this, getClass() + " the flavor requested is LOCAL_NODE_FLAVOR");
			return this;
		}
		else
		{
			throw new UnsupportedFlavorException(flavor);
		}
	}

	public List getSelectionList()
	{
		return this.selectionList;
	}

	public synchronized DataTransferActionType getActionType()
	{
		return this.actionType;
	}

	public String toString()
	{
		return (this.selectionList == null) ? null : this.selectionList.toString();
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.10  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.9  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/14 21:37:18  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/11/29 16:23:55  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/08/03 19:11:00  jiangsc
 * HISTORY      : Some cosmetic update and make HSMPanel able to save the same content to different file.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/22 20:53:15  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/23 14:30:13  jiangsc
 * HISTORY      : Updated CSVPanel implementation to support basic drag and drop.
 * HISTORY      :
 */
