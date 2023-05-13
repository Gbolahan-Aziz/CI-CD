package wniemiec.app.nforum.repositories;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.Test;
import wniemiec.web.nforum.dto.TopicDTO;
import wniemiec.web.nforum.repositories.TopicRepository;


public class TopicRepositoryTest extends RepositoryTest {

	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private TopicRepository topicRepository;
	

	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public TopicRepositoryTest() {
		topicRepository = new TopicRepository();
	}
	
	
	//-------------------------------------------------------------------------
	//		Tests
	//-------------------------------------------------------------------------
	@Test
	public void testFindById() throws SQLException {
		TopicDTO topic = topicRepository.findById(1);
		
		assertEquals("topic1", topic.getTitle());
		assertEquals("content1", topic.getContent());
	}
	
	@Test
	public void testInsert() throws Exception {
		topicRepository.insert(new TopicDTO(
			null,
			"topic3",
			"content3",
			"user1"
		));
		
		IDataSet currentDatabase = jdt.getConnection().createDataSet();
		ITable currentTable = currentDatabase.getTable("topic");
		
		FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
		IDataSet expectedDatabase = loader.load("../../../../dataset-dbunit-TopicRepositoryTest-insert.xml");
		ITable expectedTable = expectedDatabase.getTable("topic");
		
		org.dbunit.Assertion.assertEquals(expectedTable, currentTable);
	}
	
	@Test
	public void testGetTopics() throws SQLException {
		List<TopicDTO> topics = topicRepository.getTopics();
		
		assertEquals(2, topics.size());
		assertEquals(1, topics.get(0).getId().intValue());
		assertEquals(2, topics.get(1).getId().intValue());
	}
}
