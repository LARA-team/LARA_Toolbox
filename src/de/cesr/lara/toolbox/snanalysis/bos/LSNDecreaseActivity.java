/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 28.01.2010
 */
package de.cesr.lara.toolbox.snanalysis.bos;

import java.util.Map;

import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;

/**
 * 
 * @author Sascha Holzhauer
 * @param <A>
 *            the agent class
 * @date 28.01.2010
 * 
 */
public abstract class LSNDecreaseActivity<A extends LaraAgent<A, LSNDecreaseActivity<A>>>
		extends LAbstractSNBO<A, LSNDecreaseActivity<A>> {

	/**
	 * @param key
	 * @param agent
	 * @param utilities
	 */
	public LSNDecreaseActivity(String key, A agent,
			Map<Class<? extends LaraPreference>, Double> utilities) {
		super(key, agent, utilities);
	}

	/**
	 * @see de.cesr.lara.toolbox.snanalysis.bos.LAbstractSNBO#computeUtility(Class)
	 */
	@Override
	public boolean computeUtility(Class<? extends LaraPreference> goal) {
		// TODO Auto-generated method stub
		return false;
	}
}
