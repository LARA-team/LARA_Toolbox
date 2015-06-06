/**
 * 
 */
package de.cesr.lara.testing.toolbox.config.xml;

import de.cesr.lara.testing.LTestUtils;
import de.cesr.lara.toolbox.config.xml.LXmlModelConfigurator;
import de.cesr.lara.toolbox.param.LXmlConfigPa;
import de.cesr.parma.core.PmParameterManager;

/**
 * @author Sascha Holzhauer
 *
 */
public class XmlConfigUtils {

	final static String FOLDER = "./testdata/config";
	final static String XML_PREFERENCES = "Preferences.xml";
	final static String XML_DCONFIG = "DecisionConfigurations.xml";;

	static public void initXmlTestModel(boolean configureModel)
			throws Exception {
		LTestUtils.initBareTestModel(null);
		
		PmParameterManager pm = PmParameterManager.getInstance(null);
		pm.setParam(LXmlConfigPa.XML_BASEPATH, FOLDER);
		pm.setParam(LXmlConfigPa.XML_PATH_PREFERENCES, XML_PREFERENCES);
		pm.setParam(LXmlConfigPa.XML_PATH_DCONFIG, XML_DCONFIG);

		if (configureModel) {
			LXmlModelConfigurator.configure(null, pm);
		}
	}
}

