package gov.nih.nci.ncicb.cadsr.test;

import gov.nih.nci.ncicb.cadsr.service.*;


import junit.framework.Test;
import junit.framework.TestCase;

import junit.framework.TestSuite;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class TestServices extends TestCase {
    BeanFactory beanFactory;
    
    public TestServices() {
    }
    
    public void tearDown() {
      beanFactory = null;    
    }
    
    public void setUp() {
       ClassPathResource resource = new ClassPathResource("beans.xml");
       beanFactory = new XmlBeanFactory(resource);
    }
    
    public void testGenerateMessageService() {
      try {
        GenerateMessageService generateMessageService = (GenerateMessageService)beanFactory.getBean("generateMessageService");
        generateMessageService.geteDCIHL7Message("fdjfkdjfdkj");
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public static Test suite() {
      return new TestSuite(TestServices.class);
    }

    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }    
}
