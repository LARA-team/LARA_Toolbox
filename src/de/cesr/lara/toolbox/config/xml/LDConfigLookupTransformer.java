/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import org.apache.log4j.Logger;
import org.simpleframework.xml.transform.Transform;

import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.components.util.LaraDecisionConfigRegistry;

/**
 * @author Sascha Holzhauer
 *
 */
public class LDConfigLookupTransformer implements
		Transform<LaraDecisionConfiguration> {

	/**
	 * Logger
	 */
	static private Logger logger = Logger
			.getLogger(LDConfigLookupTransformer.class);

	LaraDecisionConfigRegistry dConfigRegistry;

	public LDConfigLookupTransformer() {
		this.dConfigRegistry = LModel.getModel().getDecisionConfigRegistry();
	}

	public LDConfigLookupTransformer(Object id) {
		this.dConfigRegistry = LModel.getModel(id).getDecisionConfigRegistry();
	}

	/**
	 * @see org.simpleframework.xml.transform.Transform#read(java.lang.String)
	 */
	@Override
	public LaraDecisionConfiguration read(String value) throws Exception {

		// <- LOGGING
		if (logger.isDebugEnabled()) {
			logger.debug("Transform LaraDecisionConfiguration: " + value);
		}
		// LOGGING ->

		if (!this.dConfigRegistry.isRegistered(value)) {
			throw new IllegalStateException(
					"The requrested decision configuration (" + value
							+ ") is not yet registered!");
		} else {
			return this.dConfigRegistry.get(value);
		}
	}

	/**
	 * @see org.simpleframework.xml.transform.Transform#write(java.lang.Object)
	 */
	@Override
	public String write(LaraDecisionConfiguration value) throws Exception {
		// not needed
		return null;
	}
}
