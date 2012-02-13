/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 20.01.2010
 */
package de.cesr.lara.toolbox.snanalysis.bos;


import java.util.Map;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;


/**
 * 
 * @author Sascha Holzhauer
 * @param <A>
 * @param <BO>
 * @date 20.01.2010
 * 
 */
public abstract class LAbstractSNBO<A extends LaraAgent<A, BO>, BO extends LAbstractSNBO<A, BO>> extends
		LaraBehaviouralOption<A, BO> {

	/**
	 * @param key
	 * @param agent
	 * @param utilities
	 */
	public LAbstractSNBO(String key, A agent, Map<Class<? extends LaraPreference>, Double> utilities) {
		super(key, agent, utilities);
	}

	/**
	 * @param dConfig
	 */
	@Override
	public Map<Class<? extends LaraPreference>, Double> getSituationalUtilities(LaraDecisionConfiguration dConfiguration) {
		Map<Class<? extends LaraPreference>, Double> utilities = getModifiableUtilities();
		for (Class<? extends LaraPreference> goal : dConfiguration.getPreferences()) {
			computeUtility(goal);
		}
		// TODO fill map!
		return utilities;
	}

	/**
	 * @param goal
	 * @return Created by Sascha Holzhauer on 25.01.2010
	 */
	public abstract boolean computeUtility(Class<? extends LaraPreference> goal);

}
