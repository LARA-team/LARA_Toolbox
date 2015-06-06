/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import de.cesr.lara.toolbox.param.LXmlConfigPa;
import de.cesr.parma.core.PmParameterManager;

/**
 * @author Sascha Holzhauer
 *
 */
public class LXmlModelConfigurator {

	/**
	 * @param id
	 *            object to identify model
	 * @param pm
	 * @throws Exception
	 */
	public static void configure(Object id, final PmParameterManager pm)
			throws Exception {
		LPersister.getPersister(id).setPathProvider(
				new LaraContextulisedPathProvider() {
					@Override
					public String getPath(String filename) {
						return pm.getParam(LXmlConfigPa.XML_BASEPATH)
								+ System.getProperty("file.separator")
								+ filename;
					}
				});

		LXmlPreferenceLoader prefLoader = LPersister.getPersister(id).readXML(
				LXmlPreferenceLoader.class, (String) pm.getParam(LXmlConfigPa.XML_PATH_PREFERENCES));
		prefLoader.load(id);

		LXmlDecisionConfigListLoader dConfigLoader = LPersister
				.getPersister(id)
				.readXML(LXmlDecisionConfigListLoader.class, (String) pm.getParam(LXmlConfigPa.XML_PATH_DCONFIG));
		dConfigLoader.load(id);
	}
}
