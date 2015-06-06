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
			Map<LaraPreference, Double> utilities) {
		super(key, agent, utilities);
	}

	@Override
	public boolean computeUtility(LaraPreference goal) {
		if (goal.getId().equals("LSNAttendCenralActors")) {
			// TODO implement utility updating
		}
		return true;
	}

	@Override
	public LSNCutLeastCentralLinks<A> getModifiedBO(A agent,
			Map<LaraPreference, Double> utilities) {
		return new LSNCutLeastCentralLinks<A>(getKey(), agent, utilities);
	}

}