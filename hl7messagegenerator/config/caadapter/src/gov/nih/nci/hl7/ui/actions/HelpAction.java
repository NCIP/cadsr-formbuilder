/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/actions/HelpAction.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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
import gov.nih.nci.hl7.ui.context.AbstractContextAction;
import gov.nih.nci.hl7.ui.help.HelpContentViewer;
import gov.nih.nci.hl7.ui.util.DefaultSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The class defines the about action for the whole HL7SDK application.
 *
 * @author OWNER: Ki Sung Um
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class HelpAction extends AbstractContextAction
{
	public static final String COMMAND_NAME = ActionConstants.HELP;
	public static final Character COMMAND_MNEMONIC = new Character('H');
	public static final KeyStroke ACCELERATOR_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false);
	private static final ImageIcon IMAGE_ICON = new ImageIcon(DefaultSettings.getImage("Help16.gif"));
	private static final String TOOL_TIP_DESCRIPTION = ActionConstants.HELP;
    //private static HelpContentViewer helpContentViewer = null;

    private MainFrame mainFrame = null;

	public HelpAction(MainFrame mainFrame)
	{
		this(COMMAND_NAME, mainFrame);

    }

	public HelpAction(String name, MainFrame mainFrame)
	{
		this(name, IMAGE_ICON, mainFrame);
	}

	public HelpAction(String name, Icon icon, MainFrame mainFrame)
	{
		super(name, icon);
		this.mainFrame = mainFrame;
		setMnemonic(COMMAND_MNEMONIC);
		setAcceleratorKey(ACCELERATOR_KEY_STROKE);
		setActionCommandType(DESKTOP_ACTION_TYPE);
		setShortDescription(TOOL_TIP_DESCRIPTION);

        //do not know how to set the icon location name, or just do not matter.
	}

	/**
	 * The abstract function that descendant classes must be overridden to provide customsized handling.
	 *
	 * @param e
	 * @return true if the action is finished successfully; otherwise, return false.
	 */
	protected boolean doAction(ActionEvent e) throws Exception
	{
        try
        {
             mainFrame.showHelpContentViewer();
        }
        catch(NullPointerException ne)
        {}
        
		setSuccessfullyPerformed(true);
		return isSuccessfullyPerformed();
	}

	/**
	 * Return the associated UI component.
	 *
	 * @return the associated UI component.
	 */
	protected Component getAssociatedUIComponent()
	{
		return this.mainFrame;
	}
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.15  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.14  2006/01/17 19:37:21  umkis
 * HISTORY      : HelpContentViewer is Locate in mainFrame
 * HISTORY      :
 * HISTORY      : Revision 1.13  2006/01/12 19:50:14  umkis
 * HISTORY      : by asking from UAT team, the help frame can be NOT-disappeared in spite of its lost-focusing.
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/29 23:06:12  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/14 21:37:16  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/11/29 16:23:56  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/11/17 21:02:21  umkis
 * HISTORY      : change Icon image
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/10/25 21:11:01  jiangsc
 * HISTORY      : Help Kisung correct the wrong code due to merging in doAction() function
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/10/20 15:37:18  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/10/20 13:09:11  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/10/19 18:51:24  jiangsc
 * HISTORY      : Re-engineered Action calling sequence.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/09/09 13:18:36  umkis
 * HISTORY      : Help Content Viewer
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/09/07 22:27:37  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/08/04 19:04:35  jiangsc
 * HISTORY      : Minor update
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/02 22:23:09  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/27 22:41:17  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/22 20:52:56  jiangsc
 * HISTORY      : Structure change and added License and history anchors.
 * HISTORY      :
 */
