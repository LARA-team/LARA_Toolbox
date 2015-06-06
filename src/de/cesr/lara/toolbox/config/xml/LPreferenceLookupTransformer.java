/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import org.simpleframework.xml.transform.Transform;

import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.components.util.LaraPreferenceRegistry;

/**
 * @author Sascha Holzhauer
 *
 */
public class LPreferenceLookupTransformer implements
 Transform<LaraPreference> {

	LaraPreferenceRegistry prefRegistry;

	public LPreferenceLookupTransformer() {
		this.prefRegistry = LModel.getModel().getPrefRegistry();
	}

	public LPreferenceLookupTransformer(Object id) {
		this.prefRegistry = LModel.getModel(id).getPrefRegistry();
	}

	/**
	 * @see org.simpleframework.xml.transform.Transform#read(java.lang.String)
	 */
	@Override
	public LaraPreference read(String value) throws Exception {
		if (!this.prefRegistry.isRegistered(value)) {
			throw new IllegalStateException("The requested Preference ("
					+ value + ") is not yet registered!");
		} else {
			return this.prefRegistry.get(value);
		}
	}

	/**
	 * @see org.simpleframework.xml.transform.Transform#write(java.lang.Object)
	 */
	@Override
	public String write(LaraPreference value) throws Exception {
		// not needed
		return null;
	}
}
