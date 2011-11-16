/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 20.01.2010
 */
package de.cesr.lara.toolbox.snanalysis.bos;

import java.util.Map;

import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.toolbox.snanalysis.goals.LSNAttendCenralActors;

/**
 * 
 * @author Sascha Holzhauer
 * @param <A>
 *            the agent class
 * @date 20.01.2010
 * 
 */
public class LSNCutLeastCentralLinks<A extends LaraAgent<A, LSNDecreaseActivity<A>>>
		extends LSNDecreaseActivity<A> {

	/**
	 * @param key
	 * @param agent
	 * @param utilities
	 */
	public LSNCutLeastCentralLinks(String key, A agent,
			Map<Class<? extends LaraPreference>, Double> utilities) {
		super(key, agent, utilities);
	}

	@Override
	public boolean computeUtility(Class<? extends LaraPreference> goal) {
		if (goal == LSNAttendCenralActors.class) {
			// TODO implement utility updating
		}
		return true;
	}

	@Override
	public LSNCutLeastCentralLinks<A> getModifiedBO(A agent,
			Map<Class<? extends LaraPreference>, Double> utilities) {
		return new LSNCutLeastCentralLinks<A>(getKey(), agent, utilities);
	}

}
