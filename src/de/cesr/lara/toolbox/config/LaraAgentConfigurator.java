/**
 * 
 */
package de.cesr.lara.toolbox.config;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.agents.LaraAgent;

/**
 * Indented for use with XML configuration: The configurator is de-serialised,
 * and particular agents are passed to it to be assigned the components defined
 * in the configurator.
 * 
 * @author Sascha Holzhauer
 * 
 * @param <A>
 *            agent type
 * @param <BO>
 *            BO type
 * 
 */
public interface LaraAgentConfigurator<A extends LaraAgent<A, BO>, 
	BO extends LaraBehaviouralOption<?,? extends BO>> {

	/**
	 * @param id
	 *            object to identify persister
	 * @throws Exception
	 */
	public void load(Object id) throws Exception;

	public void configure(A agent);
}
