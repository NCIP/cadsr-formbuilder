package gov.nih.nci.ncicb.cadsr.dao;

import gov.nih.nci.ncicb.cadsr.edci.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.edci.domain.DataElementGroup;
import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class DomainObjectFactory {
    BeanFactory beanFactory = null;
        
    public DomainObjectFactory() {
        ClassPathResource resource = new ClassPathResource("beans.xml");
        beanFactory = new XmlBeanFactory(resource);    
    }
    
    public GlobalDefinitions getGlobalDefinitions()  {
          GlobalDefinitions globalDefinitions = (GlobalDefinitions)beanFactory.getBean("globalDefinitions");
          return globalDefinitions;
    }
    
    public DataElement getDataElement()  {
          DataElement dataElement = (DataElement)beanFactory.getBean("dataElement");
          return dataElement;
    }
    
    public DataElementGroup getDataElementGroup()  {
          DataElementGroup dataElementGroup = (DataElementGroup)beanFactory.getBean("dataElementGroup");
          return dataElementGroup;
    }
    
    public DataElementConcept getDataElementConcept()  {
          DataElementConcept dataElementConcept = (DataElementConcept)beanFactory.getBean("dataElementConcept");
          return dataElementConcept;
    }
    
    public ValueDomain getValueDomain()  {
          ValueDomain valueDomain = (ValueDomain)beanFactory.getBean("valueDomain");
          return valueDomain;
    }    
    
}
