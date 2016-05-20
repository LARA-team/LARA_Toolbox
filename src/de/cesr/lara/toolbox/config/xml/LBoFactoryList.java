/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Indented for de-serialisation of BO lists
 * 
 * @author Sascha Holzhauer
 * 
 */
@Root(name = "LBoFactoryList")
public class LBoFactoryList {

	@ElementList(inline = true, required = false, entry = "bofactory", empty = false)
	List<LBoFactory> bofactories = new ArrayList<>();

	public List<LBoFactory> getBoFactories() {
		return this.bofactories;
	}
}
