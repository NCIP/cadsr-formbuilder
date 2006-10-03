/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/context/DefaultContextManagerClientPanel.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui.context;

import gov.nih.nci.hl7.ui.MainContextManager;
import gov.nih.nci.hl7.ui.MainFrame;
import gov.nih.nci.hl7.ui.MenuConstants;
import gov.nih.nci.hl7.ui.actions.ActionConstants;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.util.GeneralUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * This class defines the abstract implementation of some commonly used functions of a ContextManagerClient Panel.
 * Descendant classes are expected to override some of them to provide customized implementations.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
abstract public class DefaultContextManagerClientPanel extends JPanel implements ContextManagerClient
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: DefaultContextManagerClientPanel.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/context/DefaultContextManagerClientPanel.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $";

	//key: menu_name, value: a map of action items (key: menuitem name, value, action);
	protected Map menuMap;

	protected File saveFile = null;

	private MainContextManager contextManager = null;

	/**
	 * Creates a new JPanel with the specified layout manager and buffering
	 * strategy.
	 *
	 * @param layout           the LayoutManager to use
	 * @param isDoubleBuffered a boolean, true for double-buffering, which
	 *                         uses additional memory space to achieve fast, flicker-free
	 *                         updates
	 */
	protected DefaultContextManagerClientPanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
	}

	/**
	 * Create a new buffered JPanel with the specified layout manager
	 *
	 * @param layout the LayoutManager to use
	 */
	protected DefaultContextManagerClientPanel(LayoutManager layout)
	{
		super(layout);
	}

	/**
	 * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
	 * and the specified buffering strategy.
	 * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
	 * will use a double buffer.
	 *
	 * @param isDoubleBuffered a boolean, true for double-buffering, which
	 *                         uses additional memory space to achieve fast, flicker-free
	 *                         updates
	 */
	protected DefaultContextManagerClientPanel(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
	}

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 */
	protected DefaultContextManagerClientPanel()
	{
	}

	/**
	 * Return the save file.
	 * @return the save file.
	 */
	public File getSaveFile()
	{
		return saveFile;
	}

	/**
	 * Set a new save file.
	 * @param saveFile
	 * @return true if the value is changed, false otherwise.
	 */
	public boolean setSaveFile(File saveFile)
	{
		//removed the equal check so as to support explicit refresh or reload call.
//		if (!GeneralUtilities.areEqual(this.saveFile, saveFile))
//		{
		boolean sameFile = GeneralUtilities.areEqual(this.saveFile, saveFile);
		if(!sameFile)
		{//remove interest in the context file manager, first for old file
			if(getMainContextManager()!=null)
			{
				getMainContextManager().getContextFileManager().removeFileUsageListener(this);
			}
		}
		this.saveFile = saveFile;
		if(!sameFile)
		{//register interest in the context file manager for new file
			if(getMainContextManager()!=null)
			{
				getMainContextManager().getContextFileManager().registerFileUsageListener(this);
			}
		}
		updateTitle(this.saveFile.getName());
		return true;
//		}
//		return false;
	}

	/**
	 * Overridable function to update Title in the tabbed pane.
	 * @param newTitle
	 */
	protected void updateTitle(String newTitle)
	{
		JRootPane rootPane = getRootPane();
//		System.out.println("root pane type: " + rootPane.getClass().getName());
		if (rootPane != null)
		{
			Container container = rootPane.getParent();
//		System.out.println("root pane parent is " + container.getClass().getName());
			if (container instanceof MainFrame)
			{
				((MainFrame)container).setCurrentPanelTitle(newTitle);
//				JTabbedPane tabbedPane = ((MainFrame) container).getTabbedPane();
//				int seleIndex = tabbedPane.getSelectedIndex();
//				if (seleIndex != -1)
//				{
//					tabbedPane.setTitleAt(seleIndex, newTitle);
//				}
			}
		}
	}

	/**
	 * Return a list menu items under the given menu to be updated.
	 * @param menu_name
	 * @return the action map.
	 */
	public Map getMenuItems(String menu_name)
	{
		if (menuMap == null)
		{
			menuMap = Collections.synchronizedMap(new HashMap());
		}

		Map actionMap = (Map) menuMap.get(menu_name);
		if (actionMap == null)
		{//lazy initialization
			actionMap = new HashMap();
			menuMap.put(menu_name, actionMap);
		}//end of if(actionMap==null)

		//return the non-null action map and also lazily initialized menuMap.
		return actionMap;
	}

	/**
	 * return the close action inherited with this client.
	 * @return the close action inherited with this client.
	 */
	public Action getDefaultCloseAction()
	{//by doing this way, the menu and the panel will use the same close action.
		Map actionMap = getMenuItems(MenuConstants.FILE_MENU_NAME);
		Action closeAction = (Action) actionMap.get(ActionConstants.CLOSE);
		return closeAction;
	}

	/**
	 * return the save action inherited with this client.
	 * @return the save action inherited with this client.
	 */
	public Action getDefaultSaveAction()
	{
		Map actionMap = getMenuItems(MenuConstants.FILE_MENU_NAME);
		Action saveAction = (Action) actionMap.get(ActionConstants.SAVE);
		return saveAction;
	}

    protected MainContextManager getMainContextManager()
	{
		if(contextManager==null)
		{
			JRootPane rootPane = getRootPane();
//		System.out.println("root pane type: " + rootPane.getClass().getName());
			if (rootPane != null)
			{//rootpane is not null implies this panel is fully displayed;
				Container container = rootPane.getParent();
//		System.out.println("root pane parent is " + container.getClass().getName());
				if (container instanceof MainFrame)
				{
					//on the flip side, if it is null, it implies it is under certain construction.
					MainFrame mf = (MainFrame) container;
					contextManager = mf.getMainContextManager();
				}
			}
		}
		return contextManager;
	}

	/**
	 * Return the top root container (frame or dialog or window) this panel is associated with.
	 * @return the top root container (frame or dialog or window) this panel is associated with.
	 */
	public Container getRootContainer()
	{
//		Container container = null;
//		JRootPane rootPane = getRootPane();
//		//		System.out.println("root pane type: " + rootPane.getClass().getName());
//		if (rootPane != null)
//		{//rootpane is not null implies this panel is fully displayed;
//			container = rootPane.getParent();
//		}
//		return container;
		return DefaultSettings.findRootContainer(this);
	}

	/**
	 * return the open action inherited with this client.
	 * @return the open action inherited with this client.
	 */
	public abstract Action getDefaultOpenAction();

	/**
	 * Provide the default implementation of this method by simply returning an list with the registered saveFile;
	 * if saveFile is null, will return an empty list.
	 * @return a list of file objects that this context is associated with.
	 */
	public List<File> getAssociatedFileList()
	{
		List<File> resultList = new ArrayList<java.io.File>();
		if(saveFile!=null)
		{
			resultList.add(saveFile);
		}
		return resultList;
	}

    /**
	 * Return a list of Action objects that is included in this Context manager.
	 * @return a list of Action objects that is included in this Context manager.
	 */
    public java.util.List<Action> getToolbarActionList()
    {
        java.util.List<Action> actions = new ArrayList<Action>();
        actions.add(getDefaultOpenAction());
        actions.add(getDefaultSaveAction());
//        actions.add(getDefaultCloseAction());
		Map actionMap = getMenuItems(MenuConstants.FILE_MENU_NAME);
		Action action = (Action) actionMap.get(ActionConstants.VALIDATE);
		actions.add(action);
		return actions;
    }
//	protected boolean enableAction(Map actionMap, String actionConstant, boolean value)
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.21  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.20  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.19  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/12/29 23:06:12  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/12/29 15:39:05  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/12/14 21:37:17  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/11/29 16:23:56  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/11/18 20:28:14  jiangsc
 * HISTORY      : Enhanced context-sensitive menu navigation and constructions.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/11/16 21:00:07  umkis
 * HISTORY      : defect# 195, getToolbarActionList() is added for tool bar menu.
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/11/14 19:55:51  jiangsc
 * HISTORY      : Implementing UI enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/10/05 20:51:58  jiangsc
 * HISTORY      : GUI Enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/10/05 20:50:27  jiangsc
 * HISTORY      : GUI Enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/10/05 20:39:51  jiangsc
 * HISTORY      : GUI Enhancement.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/18 21:04:39  jiangsc
 * HISTORY      : Save point of the synchronization effort.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/18 15:30:16  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/08/05 20:35:45  jiangsc
 * HISTORY      : 0)Implemented field sequencing on CSVPanel but needs further rework;
 * HISTORY      : 1)Removed (Yes/No) for questions;
 * HISTORY      : 2)Removed double-checking after Save-As;
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/08/04 18:54:06  jiangsc
 * HISTORY      : Consolidated tabPane management into MainFrame
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/03 19:10:59  jiangsc
 * HISTORY      : Some cosmetic update and make HSMPanel able to save the same content to different file.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/08/02 22:23:27  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/28 18:18:42  jiangsc
 * HISTORY      : Can Open HSM Panel
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/07/27 22:41:17  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 */
