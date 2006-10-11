/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/actions/OpenMapAction.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui.actions;

import gov.nih.nci.hl7.ui.MainFrame;
import gov.nih.nci.hl7.ui.context.ContextManagerClient;
import gov.nih.nci.hl7.ui.context.DefaultContextOpenAction;
import gov.nih.nci.hl7.ui.map.MappingPanel;
import gov.nih.nci.hl7.ui.util.DefaultSettings;
import gov.nih.nci.hl7.ui.util.SwingWorker;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.GeneralUtilities;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * This class defines the open Map panel action.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class OpenMapAction extends DefaultContextOpenAction
{
	private static final String COMMAND_NAME = ActionConstants.OPEN_MAP_FILE_TXT;
	private static final Character COMMAND_MNEMONIC = new Character('M');
	private static final KeyStroke ACCELERATOR_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK + Event.SHIFT_MASK, false);
//	private static final ImageIcon IMAGE_ICON = new ImageIcon(DefaultSettings.getImage("fileOpen.gif"));
	private static final String TOOL_TIP_DESCRIPTION = "Open a Mapping File";

    private transient File openFile;

	/**
	 * Defines an <code>Action</code> object with a default
	 * description string and default icon.
	 */
	public OpenMapAction(MainFrame mainFrame)
	{
		this(COMMAND_NAME, mainFrame);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a default icon.
	 */
	public OpenMapAction(String name, MainFrame mainFrame)
	{
		this(name, IMAGE_ICON, mainFrame);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a the specified icon.
	 */
	public OpenMapAction(String name, Icon icon, MainFrame mainFrame)
	{
		super(name, icon, mainFrame);
	}

    protected void setAdditionalAttributes()
	{
		setMnemonic(COMMAND_MNEMONIC);
		setAcceleratorKey(ACCELERATOR_KEY_STROKE);
		setActionCommandType(DESKTOP_ACTION_TYPE);
		setShortDescription(TOOL_TIP_DESCRIPTION);
	}

	/**
	 * Return the real implementation of ContextClient class.
	 * @return the class object
	 */
	protected Class getContextClientClass()
	{
		return MappingPanel.class;
	}

	/**
	 * Return the real user input of the file to be opened.
	 *
	 * @return the file object
	 */
	protected File getFileFromUserInput()
	{
		return openFile;
	}

	/**
	 * Launch the context manager client to UI.
	 *
	 * @param panel
	 * @param file
	 */
	protected void launchPanel(final ContextManagerClient panel, final File file)
	{
		final MappingPanel mappingPanel  = (MappingPanel) panel;
		//have to add the new tab so as the panel may update its panel title in the tabbed pane.
		SwingWorker worker = new SwingWorker()
		{
			public Object construct()
			{
				try
				{
					GeneralUtilities.setCursorWaiting(mainFrame);
					mainFrame.addNewTab(mappingPanel);
					setSuccessfullyPerformed(true);
				}
				catch(Throwable t)
				{
					Log.logException(getClass(), "May ignore and proceed", t);
					setSuccessfullyPerformed(false);
				}
				finally
				{
					//back to normal, in case exception occurred.
					GeneralUtilities.setCursorDefault(mainFrame);
					return null;
				}
			}

			public void finished()
			{
				if (!isSuccessfullyPerformed())
				{//no need to proceed further
					return;
				}
				//this variable will help determine whether or not to close the created panel in the event of validation errors or exceptions.
				boolean everythingGood = true;
				ValidatorResults validatorResults = null;
				try
				{
					GeneralUtilities.setCursorWaiting(mainFrame);
					validatorResults = mappingPanel.processOpenMapFile(file);
					everythingGood = handleValidatorResults(validatorResults);
//					mainFrame.getMainContextManager().getContextFileManager().registerFileUsageListener(mappingPanel);
				}
				catch (Throwable e1)
				{
//					reportThrowableToUI(e1, mainFrame);
					//log the exception, but not report
					DefaultSettings.reportThrowableToLogAndUI(this, e1, "", mainFrame, false, true);
					Message msg = MessageResources.getMessage("GEN3", new Object[0]);
					//report the nice to have message
					DefaultSettings.reportThrowableToLogAndUI(this, null, msg.toString(), mainFrame, false, false);
					everythingGood = false;
				}
				finally
				{
					//back to normal.
					GeneralUtilities.setCursorDefault(mainFrame);
					setSuccessfullyPerformed(everythingGood);

					if (!everythingGood)
					{//do the clean up.
//						Message msg = MessageResources.getMessage("GEN3", new Object[0]);
//						JOptionPane.showMessageDialog(mainFrame, msg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						if (mappingPanel != null && mainFrame.hasComponentOfGivenClass(getContextClientClass(), false) != null)
						{
						//						mainFrame.getTabbedPane().remove(mappingPanel);
						//use close action instead of removing it from tabbed directly so as to allow main frame to clean up maps.
							gov.nih.nci.hl7.ui.map.actions.CloseAction closeAction = new gov.nih.nci.hl7.ui.map.actions.CloseAction(mappingPanel);
							closeAction.actionPerformed(actionEvent);
						}
					}
				}
			}
		};
		worker.start();
	}

	/**
	 * Invoked when an action occurs.
	 */
	protected boolean doAction(ActionEvent e)
	{
		File file = DefaultSettings.getUserInputOfFileFromGUI(mainFrame, //getUIWorkingDirectoryPath(),
				Config.MAP_FILE_DEFAULT_EXTENTION, Config.OPEN_DIALOG_TITLE_FOR_MAP_FILE, false, false);
		if (file != null)
		{
			openFile = file;
			super.doAction(e);
		}
//		else
//		{
//			Log.logInfo(this, "Open command cancelled by user.");
//		}
		return isSuccessfullyPerformed();
	}

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.24  2006/08/02 18:44:21  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.23  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.22  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/12/29 23:06:12  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/12/14 21:37:16  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/11/29 16:23:56  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/11/14 19:55:51  jiangsc
 * HISTORY      : Implementing UI enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/10/25 22:00:42  jiangsc
 * HISTORY      : Re-arranged system output strings within UI packages.
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/10/19 18:51:24  jiangsc
 * HISTORY      : Re-engineered Action calling sequence.
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/10/13 18:53:44  jiangsc
 * HISTORY      : Added validation on invalid file type to map and HSM modules.
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/10/10 20:48:56  jiangsc
 * HISTORY      : Enhanced dialog operation.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/08/29 17:48:55  jiangsc
 * HISTORY      : Fix to #95 to avoid hour glass after map loading.
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/08/18 21:04:37  jiangsc
 * HISTORY      : Save point of the synchronization effort.
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/08/18 15:30:14  jiangsc
 * HISTORY      : First implementation on Switch control.
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/08/11 22:10:27  jiangsc
 * HISTORY      : Open/Save File Dialog consolidation.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/04 18:54:03  jiangsc
 * HISTORY      : Consolidated tabPane management into MainFrame
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/03 16:56:14  jiangsc
 * HISTORY      : Further consolidation of context sensitive management.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/02 22:23:12  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/27 13:57:40  jiangsc
 * HISTORY      : Added the first round of HSMPanel.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/22 20:52:59  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 */