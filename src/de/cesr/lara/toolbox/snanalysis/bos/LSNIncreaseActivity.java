/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 25.01.2010
 */
package de.cesr.lara.toolbox.snanalysis.bos;

import java.util.Map;

import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.toolbox.snanalysis.goals.LSNFreeCapacity;

/**
 * 
 * @author Sascha Holzhauer
 * @param <A>
 * @date 25.01.2010
 * 
 */
public class LSNIncreaseActivity<A extends LaraAgent<A, LSNIncreaseActivity<A>>>
		extends LAbstractSNBO<A, LSNIncreaseActivity<A>> {

	/**
	 * @param key
	 * @param agent
	 * @param utilities
	 */
	public LSNIncreaseActivity(String key, A agent,
			Map<Class<? extends LaraPreference>, Double> utilities) {
		super(key, agent, utilities);
	}

	/**
	 * @see de.cesr.lara.toolbox.snanalysis.bos.LAbstractSNBO#computeUtility(Class)
	 */
	@Override
	public boolean computeUtility(Class<? extends LaraPreference> goal) {
		if (goal == LSNFreeCapacity.class) {

		}
		return false;
	}

	@Override
	public LSNIncreaseActivity<A> getModifiedBO(A agent,
			Map<Class<? extends LaraPreference>, Double> utilities) {
		return new LSNIncreaseActivity<A>(getKey(), agent, utilities);
	}

}
