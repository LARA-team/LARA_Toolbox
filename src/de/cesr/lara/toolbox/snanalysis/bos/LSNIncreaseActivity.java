/**
 * This file is part of
 * 
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 * 
 * Copyright (C) 2012 Center for Environmental Systems Research, Kassel, Germany
 * 
 * LARA is free software: You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * LARA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
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