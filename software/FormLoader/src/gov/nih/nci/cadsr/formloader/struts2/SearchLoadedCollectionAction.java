package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.repository.FormLoaderRepositoryImpl;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.impl.CollectionRetrievalServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

public class SearchLoadedCollectionAction extends ActionSupport implements
		SessionAware {

	private static Logger logger = Logger.getLogger(SearchLoadedCollectionAction.class.getName());
	
	private Map<Integer, String> checkboxes;
	private HttpServletRequest servletRequest;
	private List<FormCollection> collectionList = null;
	ApplicationContext applicationContext = null;

	public String execute() {
		logger.debug("We are in XMLFileLoadedAction.execute()");
		servletRequest = ServletActionContext.getRequest();
		try {

			applicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			CollectionRetrievalServiceImpl collectionRetrieval =
					(CollectionRetrievalServiceImpl)this.applicationContext.getBean("collectionRetrievalService");
			String userName = (String)servletRequest.getSession().getAttribute("username");
			collectionList = collectionRetrieval.getAllCollectionsByUser(userName);

			if (collectionList == null) {
				logger.debug("Collection list is null.");
				return ERROR;
			}

			logger.debug("User [" + userName + "] has previously loaded " + collectionList.size() + " collections.");
			servletRequest.getSession().setAttribute("collectionList", collectionList);
			return SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			addActionError(e.getMessage());
		}
		
		return ERROR;
	}

	public List<FormCollection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<FormCollection> collectionList) {
		this.collectionList = collectionList;
	}

	public Map<Integer, String> getCheckboxes() {
		return checkboxes;
	}

	public void setCheckboxes(Map<Integer, String> checkboxes) {
		this.checkboxes = checkboxes;
	}

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}

}