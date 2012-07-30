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
public abstract class LAbstractSNBO<A extends LaraAgent<A, BO>, BO extends LAbstractSNBO<A, BO>>
		extends LaraBehaviouralOption<A, BO> {

	/**
	 * @param key
	 * @param agent
	 * @param utilities
	 */
	public LAbstractSNBO(String key, A agent,
			Map<Class<? extends LaraPreference>, Double> utilities) {
		super(key, agent, utilities);
	}

	/**
	 * @param goal
	 * @return Created by Sascha Holzhauer on 25.01.2010
	 */
	public abstract boolean computeUtility(Class<? extends LaraPreference> goal);

	/**
	 * @param dConfig
	 */
	@Override
	public Map<Class<? extends LaraPreference>, Double> getSituationalUtilities(
			LaraDecisionConfiguration dConfiguration) {
		Map<Class<? extends LaraPreference>, Double> utilities = getModifiableUtilities();
		for (Class<? extends LaraPreference> goal : dConfiguration
				.getPreferences()) {
			computeUtility(goal);
		}
		// TODO fill map!
		return utilities;
	}

}