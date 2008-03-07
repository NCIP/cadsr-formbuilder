/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/util/GeneralUtilities.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.util;

import gov.nih.nci.hl7.validation.Message;
import org.hl7.util.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * This class defines a list general utility methods.
 * 
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:44 $
 */
public class GeneralUtilities
{
	private GeneralUtilities()
	{//never instantiable
	}

	/**
	 * Return true if one is equal to another.
	 * @param one
	 * @param another
	 * @return true if one is equal to another.
	 */
	public static final boolean areEqual(Object one, Object another)
	{
		boolean result = (one==null? another==null : one.equals(another));
		return result;
	}

	/**
	 * Return true if one is equal to another.
	 * @param one
	 * @param another
	 * @param treatNullAndBlankStringEquals if true will treat null and blank string the same; otherwise, it will not.
	 * @return true if one is equal to another.
	 */
	public static final boolean areEqual(Object one, Object another, boolean treatNullAndBlankStringEquals)
	{
		boolean result = (one==null? another==null : one.equals(another));
		if(!result && treatNullAndBlankStringEquals)
		{
			String oneStr = one==null? "" : one.toString();
			String anotherStr = another==null? "" : another.toString();
			result = isBlank(oneStr) && isBlank(anotherStr);
		}
		return result;
	}

	/**
	 * Force convert String object null to a blank string
	 * @param s
	 * @return converted String object null to a blank string
	 */
	public static final String forceNullToBlankString(String s)
	{
		return  (s == null) ? "" : s;
	}

	/**
	 * Null string or blank string is considered as blank
	 * @param s
	 * @return true if blank
	 */
	public static final boolean isBlank(String s)
	{
		return  (s == null) || (s.trim().length()==0);
	}


	/**
	 * Return the given class name with or without package name. If aClass is null, will return null.
	 * @param aClass
	 * @param withPackageName
	 * @return the given class name with or without package name. If aClass is null, will return null.
	 */
	public static final String getClassName(Class aClass, boolean withPackageName)
	{
		if(aClass==null)
		{
			return null;
		}
		String beanClassName = aClass.getName();
		if(!withPackageName)
		{//trim off package name.
			beanClassName = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
		}
		return beanClassName;
	}

	/**
	 * Sets the cursor to hourglass.  Should be called BEFORE time-intensive task has started.
	 * Disables the mouse from clicking on anything in the current window.
	 *
	 * @param cont the current Window, Panel, Dialog, or other JComponent;
	 *             just pass 'this', as long as it's a Container
	 */
	public static void setCursorWaiting(Container cont)
	{
		if (cont == null)
		{
			System.err.println("setCursorWaiting(Container cont): Container argument cannot be null. Will return and do nothing.");
			return;
		}

		Component glasspane = null;

		if (cont instanceof RootPaneContainer)
		{
			glasspane = ((RootPaneContainer) cont).getGlassPane();
		}
		else if (cont instanceof JComponent)
		{
			glasspane = ((JComponent) cont).getRootPane().getGlassPane();
		}

//		glasspane.addMouseMotionListener(new MouseMotionAdapter()
//		{
//		});
		glasspane.setVisible(true);
		Window window = SwingUtilities.windowForComponent(glasspane);
		window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * Sets the cursor to default.  Should be called AFTER time-intensive task has finished.
	 * Re-enables the mouse pointer for the current window.
	 *
	 * @param cont the current Window, Panel, Dialog, or other JComponent;
	 *             just pass 'this', as long as it's a Container
	 */
	public static void setCursorDefault(Container cont)
	{
		if(cont==null)
		{
			System.err.println("setCursorDefault(Container cont): Container argument cannot be null. Will return and do nothing.");
			return;
		}

		Component glasspane = null;

		if (cont instanceof RootPaneContainer)
		{
			glasspane = ((RootPaneContainer) cont).getGlassPane();
		}
		else if (cont instanceof JComponent)
		{
			glasspane = ((JComponent) cont).getRootPane().getGlassPane();
		}

		Window window = SwingUtilities.windowForComponent(glasspane);
		window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		glasspane.setVisible(false);
	}


	/**
	 * Return a string presentation of the object. If it is a null, will return a string "null" if returnStringValue_null is true; otherwise, return an empty string ("").
	 * @param object
	 * @param returnStringValue_null
	 * @return a string presentation of the object. If it is a null, will return a string "null" if returnStringValue_null is true; otherwise, return an empty string ("").
	 */
	public static final String getStringValue(Object object, boolean returnStringValue_null)
	{
		String result = null;
		if(object==null)
		{
			if(returnStringValue_null)
			{
				result = "null";
			}
			else
			{
				result = "";
			}
		}
		else
		{
			result = object.toString();
		}

		return result;
	}

	/**
	 * Help to convert the given throwable to an instance of message by utilizing the GEN0 messsage.
	 * @param t
	 * @return the message object.
	 */
	public static final Message convertToGeneralMessage(Throwable t)
	{
		String reportMsg;
		if(t==null)
		{
			reportMsg = "Unknown Error.";
		}
		else
		{
			reportMsg = t.getMessage();
		}
		Message msg = MessageResources.getMessage("GEN0", new Object[]{t.getMessage()});
		return msg;
	}

    public static String join(Object[] objectArray, String separator){
        if (objectArray == null) return null;
        String[] stringArray = new String[objectArray.length];

        for (int i = 0; i < objectArray.length; i++) {
            stringArray[i] = "" + objectArray[i];
        }
        return StringUtils.join(stringArray, separator);
    }

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.17  2006/08/02 18:44:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2006/01/03 19:16:53  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.15  2006/01/03 18:56:26  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/29 15:39:06  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/12/21 22:51:50  giordanm
 * HISTORY      : more function rework.
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/14 21:37:19  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/09/28 20:14:27  jiangsc
 * HISTORY      : Added a new String function.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/08/31 15:03:26  jiangsc
 * HISTORY      : Fixed some UI medium defects. Thanks to Dan's test.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/08/08 15:36:07  jiangsc
 * HISTORY      : Update isBlank() method to use:
 * HISTORY      : (s.trim().length()==0) instead of ""==s
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/08/04 22:22:29  jiangsc
 * HISTORY      : Updated license and class header information.
 * HISTORY      :
 */