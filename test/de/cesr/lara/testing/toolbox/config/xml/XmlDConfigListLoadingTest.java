/**
 * 
 */
package de.cesr.lara.testing.toolbox.config.xml;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.components.util.LaraDecisionConfigRegistry;
import de.cesr.lara.toolbox.config.xml.LPersister;
import de.cesr.lara.toolbox.config.xml.LXmlDecisionConfigListLoader;
import de.cesr.lara.toolbox.config.xml.LXmlPreferenceLoader;
import de.cesr.lara.toolbox.config.xml.LaraContextulisedPathProvider;
import de.cesr.lara.toolbox.param.LXmlConfigPa;
import de.cesr.parma.core.PmParameterManager;

/**
 * @author Sascha Holzhauer
 *
 */
public class XmlDConfigListLoadingTest {

	final static String FOLDER = "./testdata/config";
	final static String XML_PREFERENCES = "Preferences.xml";
	final String FILENAME_XML_DCONFIG = "./DecisionConfigurations.xml";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final PmParameterManager pm = PmParameterManager.getInstance(null);
		pm.setParam(LXmlConfigPa.XML_BASEPATH, FOLDER);
		pm.setParam(LXmlConfigPa.XML_PATH_PREFERENCES, XML_PREFERENCES);

		XmlConfigUtils.initXmlTestModel(false);

		LPersister.getPersister(null).setPathProvider(
				new LaraContextulisedPathProvider() {
					@Override
					public String getPath(String filename) {
						return pm.getParam(LXmlConfigPa.XML_BASEPATH)
								+ System.getProperty("file.separator")
								+ filename;
					}
				});

		// load preferences
		LXmlPreferenceLoader prefLoader = LPersister
				.getPersister(null)
				.readXML(LXmlPreferenceLoader.class,
						(String) pm.getParam(LXmlConfigPa.XML_PATH_PREFERENCES));
		prefLoader.load(null);
	}

	@Test
	public void test() throws Exception {

		LXmlDecisionConfigListLoader dConfigLoader = LPersister.getPersister(
				null)
				.readXML(LXmlDecisionConfigListLoader.class,
						FILENAME_XML_DCONFIG);
		dConfigLoader.load(null);

		LaraDecisionConfigRegistry dConfigRegistry = LModel.getModel()
				.getDecisionConfigRegistry();
		assertTrue(dConfigRegistry.isRegistered("DecisionA"));
		assertTrue(dConfigRegistry.isRegistered("DecisionB"));
	}
}
