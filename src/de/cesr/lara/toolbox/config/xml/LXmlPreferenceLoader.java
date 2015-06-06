/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import java.util.HashSet;
import java.util.Set;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.components.util.LaraPreferenceRegistry;

/**
 * @author Sascha Holzhauer
 *
 */
@Root
public class LXmlPreferenceLoader {

	@ElementList(inline = true, entry = "preference")
	Set<String> preferences = new HashSet<String>();

	public LXmlPreferenceLoader() {
	}

	public void load(Object id) {
		LaraPreferenceRegistry prefRegistry = LModel.getModel(id)
				.getPrefRegistry();
		for (String pref : preferences) {

			if (!prefRegistry.isRegistered(pref)) {
				prefRegistry.register(pref);
			}
		}
	}
}
