package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ModuleDAOV2;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-jdbcdao-test.xml"})
//@ContextConfiguration(locations = {"classpath:/applicationContext-service-test-db.xml"})
public class JDBCModuleDAOV2Test {

	@Autowired
	ModuleDAOV2 moduleV2Dao;
	
	@Test
	public void testGetQuestionsInAModuleV2() {
		String moduleSeqId = "9D1F6BBF-433F-0B69-E040-BB89AD436323";
		List<QuestionTransferObject> questions = moduleV2Dao.getQuestionsInAModuleV2(moduleSeqId);
		
		assertNotNull(questions);
		assertTrue(questions.size() == 6);
		
		QuestionTransferObject aQuestion = questions.get(3);
		assertNotNull(aQuestion.getDataElement());
	}
	@Test
	public void testGetQuestionsInAModuleV2WithInvalidInput() {
		String moduleSeqId = "9D1F6BBF-433F-0B69-E040-BB89AD4";
		List<QuestionTransferObject> questions = moduleV2Dao.getQuestionsInAModuleV2(moduleSeqId);
		
		assertNotNull(questions);
		assertTrue(questions.size() == 0);
	}
	
	@Test
	public void testGetModulePublicIdVersionBySeqid() {
		String seqid = "E6829CE9-E105-44CA-E040-BB8921B67F31";
		
		ModuleTransferObject module = moduleV2Dao.getModulePublicIdVersionBySeqid(seqid);
		assertNotNull(module);
		assertTrue(module.getPublicId() == 3651164);
		assertTrue(module.getVersion() == 1.0);
		
	}
}
