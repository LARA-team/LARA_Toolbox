/**
 * 
 */
package de.cesr.lara.testing.toolbox.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.testing.LTestUtils;
import de.cesr.lara.testing.LTestUtils.LTestAgent;
import de.cesr.lara.toolbox.config.xml.LPersister;
import de.cesr.lara.toolbox.config.xml.LXmlAgentConfigurator;

/**
 * @author Sascha Holzhauer
 *
 */
public class XmlLAgentConfiguratorTest {
	
	final String FILENAME_XML_CONFIGURATOR = "AgentConfiguratorA.xml";
	
	LXmlAgentConfigurator<LTestUtils.LTestAgent, LTestUtils.LTestBo> aConfigurator;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		XmlConfigUtils.initXmlTestModel(true);
		aConfigurator = LPersister.getPersister(null).readXML(
				LXmlAgentConfigurator.class, FILENAME_XML_CONFIGURATOR);
		aConfigurator.load(null);
	}

	@Test
	public void test() {
		LTestAgent agent = new LTestAgent("TestAgent1");
		agent.getLaraComp().setPreprocessor(null);
		aConfigurator.configure(agent);

		assertTrue(agent.getLaraComp().getBOMemory().contains("BO1"));
		assertTrue(agent.getLaraComp().getBOMemory().contains("BO12"));

		assertEquals(
				1.0,
				agent.getLaraComp().getPreferenceWeight(
						LModel.getModel().getPrefRegistry().get("PreferenceA")),
				0.001);

		assertEquals(
				2.0,
				agent.getLaraComp().getPreferenceWeight(
						LModel.getModel().getPrefRegistry().get("PreferenceB")),
				0.001);

		assertEquals(
				3.0,
				agent.getLaraComp().getPreferenceWeight(
						LModel.getModel().getPrefRegistry().get("PreferenceC")),
				0.001);

		assertTrue(agent.getLaraComp().getPreprocessor() != null);
	}
}
