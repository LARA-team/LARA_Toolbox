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