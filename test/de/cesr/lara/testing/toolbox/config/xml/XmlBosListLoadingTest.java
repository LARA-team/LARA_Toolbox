/**
 * 
 */
package de.cesr.lara.testing.toolbox.config.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.testing.LTestUtils.LTestAgent;
import de.cesr.lara.toolbox.config.xml.LBoFactoryList;
import de.cesr.lara.toolbox.config.xml.LPersister;

/**
 * @author Sascha Holzhauer
 *
 */
public class XmlBosListLoadingTest {

	final String FILENAME_XML_BOS = "./bos/listA.xml";

	protected LTestAgent agent;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		XmlConfigUtils.initXmlTestModel(true);
		this.agent = new LTestAgent("Testagent");
	}

	@Test
	public void test() throws Exception {
		LBoFactoryList bofactorylist = LPersister
				.getPersister(null).readXML(LBoFactoryList.class,
				FILENAME_XML_BOS);
		assertEquals(3, bofactorylist.getBoFactories().size());
		assertEquals("BO1", bofactorylist.getBoFactories().get(0).
				assembleBo(agent, null).getKey());
		assertEquals("BO2",
				bofactorylist.getBoFactories().get(1).assembleBo(agent, null)
						.getKey());
		assertEquals("BO3",
				bofactorylist.getBoFactories().get(2).assembleBo(agent, null)
						.getKey());

		assertEquals(
				0.5,
				bofactorylist
						.getBoFactories()
						.get(0)
						.assembleBo(agent, null)
						.getModifiableUtilities()
						.get(LModel.getModel().getPrefRegistry()
								.get("PreferenceA")).doubleValue(), 0.001);
		assertEquals(
				0.3,
				bofactorylist
						.getBoFactories()
						.get(0)
						.assembleBo(agent, null)
						.getModifiableUtilities()
						.get(LModel.getModel().getPrefRegistry()
								.get("PreferenceB")).doubleValue(), 0.001);
	}
}
