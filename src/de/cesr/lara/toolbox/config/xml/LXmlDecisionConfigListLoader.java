/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import java.util.HashSet;
import java.util.Set;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import de.cesr.lara.components.decision.impl.LDecisionConfiguration;
import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.components.util.LaraDecisionConfigRegistry;

/**
 * @author Sascha Holzhauer
 *
 */
@Root(name = "decisonConfigurations")
public class LXmlDecisionConfigListLoader {

	@ElementList(entry = "dConfig", inline = true, required = true)
	Set<LDecisionConfiguration> decisionConfigs = new HashSet<LDecisionConfiguration>();

	public LXmlDecisionConfigListLoader() {
	}

	/**
	 * Loads decisions into the {@link LaraDecisionConfigRegistry} associated
	 * with the given model ID.
	 * 
	 * @param id
	 */
	public void load(Object id) {
		LaraDecisionConfigRegistry dConfigRegistry = LModel.getModel(id)
				.getDecisionConfigRegistry();
		for (LDecisionConfiguration dConfig : decisionConfigs) {
			dConfigRegistry.register(dConfig);
		}
	}
}
