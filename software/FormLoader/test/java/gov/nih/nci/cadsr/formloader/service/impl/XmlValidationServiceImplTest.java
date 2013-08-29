package gov.nih.nci.cadsr.formloader.service.impl;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceError;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;
import gov.nih.nci.cadsr.formloader.service.common.XmlValidationError;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-service-test.xml"})
public class XmlValidationServiceImplTest {
	
	private static Logger logger = Logger.getLogger(XmlValidationServiceImplTest.class.getName());
	
	@Autowired
    private XmlValidationService xmlValService;

	@Before
	public void setUp() throws Exception {
		//xmlValService = new XmlValidationServiceImpl();
		//@SuppressWarnings("resource")
		//ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
		//		"/applicationContext-service-test.xml");
		//		"C:/development/workspace-formloader3/FormLoader/applicationContext-service-test.xml");
		//ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
		//		"/applicationContext-service-test.xml");
		//		"/applicationContext-mock-repository-test.xml");

		//xmlValService = 
		//		(XmlValidationServiceImpl)applicationContext.getBean("xmlValidationService");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateXmlMalformed() {
		try {
			
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data");
			aColl.setXmlFileName("forms-malformed.xml");
			aColl = this.xmlValService.validateXml(aColl);
			fail("Exception not thrown as expected");
		} catch (FormLoaderServiceException e) {
			System.out.println(e.toString());
			assertTrue(e.getErrorCode() == FormLoaderServiceError.ERROR_MALFORMED_XML);
			//assertTrue(e.getError() instanceof XmlValidationError);
			//assertTrue(((XmlValidationError)e.getError()).getType().equals(XmlValidationError.XML_FATAL_ERROR));
		}
	}
	
	@Test
	public void testValidateXmlHappyPath() {
		//This xsd has problems and, thus, good to generate "non-conformed" errors
		//XmlValidationServiceImpl.XSD_PATH_NAME = "FormLoaderv1-testonly.xsd";
		//((XmlValidationServiceImpl)this.xmlValService).setXSD_PATH_NAME("FormLoaderv1-testonly.xsd");
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data");
			aColl.setXmlFileName("forms.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			assertTrue(forms.size() == 3);
			
			String status = StatusFormatter.getStatusInXml(aColl);
			StatusFormatter.writeStatusToXml(status, ".\\test\\data\\xmlVal-happypath.xml");
			
			//Seems new xsd allows empty question modules
			//assertTrue(forms.get(0).getErrors().size() > 0);
		
		} catch (FormLoaderServiceException e) {
			fail("Got exception: " + e.toString());
		}
	}

	@Test
	public void testValidateXmlWithEmptyPublicId() {
		//XmlValidationServiceImpl.XSD_PATH_NAME = "FormLoaderv1-testonly.xsd";
		//((XmlValidationServiceImpl)this.xmlValService).setXSD_PATH_NAME("FormLoaderv1-testonly.xsd");
		try {
			FormCollection aColl = new FormCollection();
			aColl.setXmlPathOnServer(".\\test\\data");
			aColl.setXmlFileName("forms-2.xml");
			aColl = this.xmlValService.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			assertNotNull(forms);
			FormDescriptor form = forms.get(0);
			assertTrue(form.getPublicId() == null || form.getPublicId().length() == 0);
		
		} catch (FormLoaderServiceException e) {
			fail("Got exception: " + e.toString());
		}
	}
	
	
}
