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
package de.cesr.lara.testing.toolbox.preprocessor;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.agents.impl.LDefaultAgentComp;
import de.cesr.lara.components.container.memory.LaraBOMemory;
import de.cesr.lara.components.container.memory.impl.LDefaultLimitedCapacityBOMemory;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDecisionHeuristicComponent_MaxLineTotalRandomAtTie;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.eventbus.impl.LEventbus;
import de.cesr.lara.components.model.impl.LAbstractModel;
import de.cesr.lara.components.model.impl.LAbstractStandaloneSynchronisedModel;
import de.cesr.lara.components.model.impl.LModel;
import de.cesr.lara.components.preprocessor.LaraBOCollector;
import de.cesr.lara.components.preprocessor.event.LPpBoCollectorEvent;
import de.cesr.lara.components.preprocessor.impl.LOmitZeroContributingBOCollector;
import de.cesr.lara.components.util.LaraRandom;
import de.cesr.lara.components.util.impl.LCapacityManagers;
import de.cesr.lara.components.util.impl.LRandomService;
import de.cesr.lara.testing.TestUtils.TestAgent;
import de.cesr.lara.testing.TestUtils.TestBo;

/**
 * TODO why placed in toolbox (SH)?
 */
public class OmitZeroContributingBOScannerTest {

	TestAgent															agent;
	LaraBOMemory<TestBo> memory;

	LaraPreference														goal1;
	LaraPreference														goal2;
	LaraPreference														goal3;

	/**
	 * Does not contribute to any goal
	 */
	TestBo bo1;
	/**
	 * Contributes to goal1 by 0.0
	 */
	TestBo bo2;
	/**
	 * Contributes to goal1 by 1.0 and goal2 by 0.0
	 */
	TestBo bo3;

	LaraDecisionConfiguration													dBuilder;

	LaraBOCollector<TestAgent, TestBo> scanner;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LEventbus.resetAll();

		LModel.setNewModel(new LAbstractStandaloneSynchronisedModel() {

			@Override
			public LaraRandom getLRandom() {
				return new LRandomService((int) System.currentTimeMillis());
			}

			@Override
			public void onInternalEvent(LaraEvent event) {
			}
		});
		((LAbstractModel) LModel.getModel()).init();

		Class<? extends LaraPreference> goal1 = new LaraPreference() {
		}.getClass();
		Class<? extends LaraPreference> goal2 = new LaraPreference() {
		}.getClass();

		agent = new TestAgent("TestAgent");
		Map<Class<? extends LaraPreference>, Double> utilities = new HashMap<Class<? extends LaraPreference>, Double>();
		bo1 = new TestBo(agent, utilities);
		utilities.put(goal1, 0.0);
		bo2 = new TestBo(agent, utilities);
		utilities.put(goal1, 1.0);
		utilities.put(goal2, 0.0);
		bo3 = new TestBo(agent, utilities);

		memory = new LDefaultLimitedCapacityBOMemory<TestBo>(
				LCapacityManagers.<TestBo> makeNINO());

		agent.getLaraComp().setBOMemory(memory);

		dBuilder = new LDecisionConfiguration("TestDecision");
		LDefaultAgentComp.setDefaultDeliberativeChoiceComp(dBuilder, new LDecisionHeuristicComponent_MaxLineTotalRandomAtTie());
		List<Class<? extends LaraPreference>> goals = new ArrayList<Class<? extends LaraPreference>>();
		goals.add(goal1);
		goals.add(goal2);
		dBuilder.setPreferences(goals);

		scanner = new LOmitZeroContributingBOCollector<TestAgent, TestBo>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.cesr.lara.components.preprocessor.impl.LContributingBoCollector#collectBOs(LaraAgent, de.cesr.lara.components.container.memory.LaraBOMemory, de.cesr.lara.components.decision.LaraDecisionConfiguration)}
	 * .
	 */
	@Test
	public final void testGetBOs() {
		scanner.onInternalEvent(new LPpBoCollectorEvent(agent, dBuilder));
		assertEquals(
				"No BO in memory contributes to the decision's preferenceWeights (since there is no bo inserted)",
				0, agent.getLaraComp().getDecisionData(dBuilder).getBos()
						.size());

		memory.memorize(bo1);
		scanner.onInternalEvent(new LPpBoCollectorEvent(agent, dBuilder));
		assertEquals(
				"No BO in memory contributes to the decision's preferenceWeights (since bo1 is inserted but has utility 0.0 for goal1)",
				0, agent.getLaraComp().getDecisionData(dBuilder).getBos()
						.size());
		memory.memorize(bo2);
		memory.memorize(bo3);
		scanner.onInternalEvent(new LPpBoCollectorEvent(agent, dBuilder));
		assertEquals(
				"1 BO in memory contributes to the decision's preferenceWeights (since bo2 with utility 1.0 for goal1 and 0.0 for goal2 are inserted)",
				1, agent.getLaraComp().getDecisionData(dBuilder).getBos()
						.size());
	}
}
