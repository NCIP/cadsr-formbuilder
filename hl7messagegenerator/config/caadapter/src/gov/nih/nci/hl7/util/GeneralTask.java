/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/util/GeneralTask.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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

import gov.nih.nci.hl7.ui.util.SwingWorker;

import java.io.Serializable;
import java.util.Observable;

/**
 * This class defines an interface between a task scheduler and task executor to pass along interval status information and others.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:44 $
 */
public class GeneralTask extends Observable implements Serializable
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: GeneralTask.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/util/GeneralTask.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $";

	//in format of seconds
	private long lengthOfTask = 0;
	//in format of seconds
	private long current = 0;
	private long startTime = 0;

	//canceled is only set by the consumer side of the task, while done is set by the producer side.
	private boolean done = false;
	private boolean canceled = false;

	private String statMessage;

	public boolean isDone()
	{
		return done;
	}

	public void start(SwingWorker taskExecutorThread)
	{
		current = 0;
		startTime = System.currentTimeMillis();
		done = false;
		canceled = false;
		statMessage = "";

		if(taskExecutorThread==null)
		{
			throw new IllegalArgumentException("The task Executor thread should not be null!");
		}
		if(lengthOfTask>current)
		{
//			System.out.println("Will start the task executor.");
			taskExecutorThread.start();
		}
		else
		{
			Log.logWarning(this, "Length of task is equal to current, cannot start!");
		}
	}

	public long getLengthOfTask()
	{
		return lengthOfTask;
	}

	/**
	 * It is highly recommended to set the length of task before calling start() method.
	 * @param lengthOfTask, in format of seconds
	 */
	public void setLengthOfTask(long lengthOfTask)
	{
		this.lengthOfTask = lengthOfTask;
	}

	public long getCurrent()
	{
		return current;
	}

	/**
	 * In format of seconds
	 * @param current
	 */
	public void setCurrent(long current)
	{
		this.current = current;
	}

	public void setDone(boolean done)
	{
		this.done = done;
//		if(done)
//		{
//			stop();
//		}
	}


    /**
     * Task scheduler could cancel the task
     * @return whether the task is cancelled.
     */
    public boolean isCanceled()
	{
		return canceled;
	}

	public void setCanceled(boolean canceled)
	{
		this.canceled = canceled;
		if(canceled)
		{
			statMessage = null;
		}
	}

	public void stop()
	{
		setCanceled(true);
	}

	public String getStatMessage()
	{
//		if(GeneralUtilities.isBlank(statMessage))
//		{
		statMessage = "Processing... Time has passed so far: '" + current + "' second(s).";
//		}
		return statMessage;
	}

	public void setStatMessage(String statMessage)
	{
		this.statMessage = statMessage;
	}

	public void addTaskResult(Object result)
	{
		//so far, this task object will memerize any interim task result, but just simply notify observers
		current = (System.currentTimeMillis() - startTime) / 1000;
//		System.out.println("In addTaskResult: current is '" + current + "', lengthOfTask is '" + lengthOfTask + "'.");
		if((lengthOfTask - current)<10 && !isDone() && !isCanceled())
		{//automatically extend the boundary
			lengthOfTask += 10;
		}
		setChanged();
		notifyObservers(result);
	}

}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.9  2006/08/02 18:44:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/07/27 19:28:10  jiangsc
 * HISTORY      : Wording change
 * HISTORY      :
 * HISTORY      : Revision 1.7  2006/01/03 19:16:53  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2006/01/03 18:56:26  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/14 23:09:24  jiangsc
 * HISTORY      : Updated functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/12/07 16:42:06  jiangsc
 * HISTORY      : With enhanced functions.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/12/05 22:07:46  chene
 * HISTORY      : Integrate GeneralTask, improve the statics
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/12/05 20:42:41  jiangsc
 * HISTORY      : First edition.
 * HISTORY      :
 */