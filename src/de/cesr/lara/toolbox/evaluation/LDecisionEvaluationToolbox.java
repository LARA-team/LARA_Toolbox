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
package de.cesr.lara.toolbox.evaluation;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.agents.impl.LDefaultAgentComp;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDeliberativeChoiceComp_MaxLineTotalRandomAtTie;
import de.cesr.lara.components.preprocessor.LaraPreprocessor;
import de.cesr.lara.components.preprocessor.LaraPreprocessorConfigurator;
import de.cesr.lara.components.preprocessor.impl.LContributingBoCollector;
import de.cesr.lara.components.preprocessor.impl.LDefaultBOUpdater;
import de.cesr.lara.components.preprocessor.impl.LDeliberativeDecisionModeSelector;
import de.cesr.lara.components.preprocessor.impl.LPreprocessor;
import de.cesr.lara.components.preprocessor.impl.LPseudoPrefereceUpdater;
import de.cesr.lara.components.util.logging.impl.Log4jLogger;

/**
 * TODO Refactor!!!
 * 
 */
public class LDecisionEvaluationToolbox {

	private static Map<LaraDecisionConfiguration, LPreprocessor> preprocessBuilder = new HashMap<LaraDecisionConfiguration, LPreprocessor>();

	/**
	 * Logger
	 */
	static private Logger logger = Log4jLogger
			.getLogger(LDecisionEvaluationToolbox.class);

	/**
	 * Creates a PreprocessBuilder to use for post processing, for instance to
	 * evaluate the selected BO in an updated environment. No preferenceWeights
	 * are updated and all recent BOs are used from memory
	 * (LContributingBoCollector).
	 * 
	 * TODO provide further methods that allow customisation of preprocessor
	 * components
	 * 
	 * @param <A>
	 * @param <BO>
	 * @param configurator
	 * @param dConfig
	 * @return configurator
	 */
	public static <A extends LaraAgent<A, BO>, BO extends LaraBehaviouralOption<A, BO>> LaraPreprocessor<A, BO> getPostProcessPreprocessBuilder(
			LaraPreprocessorConfigurator<A, BO> configurator,
			LaraDecisionConfiguration dBuilder) {

		LaraPreprocessorConfigurator<A, BO> newConfigurator = configurator
				.clone();
		// / force preprocessor to select deliberative decision making mode
		// (assign LDeliberativeDeciderFactory):
		configurator
				.setDecisionModeSelector(new LDeliberativeDecisionModeSelector<A, BO>());
		configurator.setBOCollector(new LContributingBoCollector<A, BO>(),
				dBuilder);
		configurator.setBOAdapter(new LDefaultBOUpdater<A, BO>(), dBuilder);
		configurator.setPreferenceUpdater(new LPseudoPrefereceUpdater<A, BO>(),
				dBuilder);

		return configurator.getPreprocessor();
	}

	/**
	 * 
	 */
	public <A extends LaraAgent<A, BO>, BO extends LaraBehaviouralOption<A, BO>> void evaluateSelectedBoAgainstOpimalBo(
			LaraPreprocessorConfigurator<A, BO> configurator,
			LaraDecisionConfiguration dBuilder, A agent) {
		// exchange dConfig because of autonomous random stream for
		// LaraDeliberativeChoiceComponent:

		LaraDecisionConfiguration postProcessDBuilder = new LDecisionConfiguration(
				"NeighbourhoodDecisionBuilder");
		// make sure that the deliberativeChoiceComp as used in decisionData is
		// used

		LDefaultAgentComp.setDefaultDeliberativeChoiceComp(postProcessDBuilder,
				LDeliberativeChoiceComp_MaxLineTotalRandomAtTie
						.getInstance("Uniform post-processing"));
		postProcessDBuilder.setPreferences(dBuilder.getPreferences());

		// perform a deliberative decision:
		// / Update BOs with agents' current selected BO using a common
		// pre-process builder:
		getPostProcessPreprocessBuilder(configurator, postProcessDBuilder)
				.preprocess(postProcessDBuilder, agent);

		// / Use existing decision data:
		// / perform decision making:
		agent.getLaraComp().getDecisionData(dBuilder).getDecider().decide();

		// get selected BO:
		BO boToCompare = agent.getLaraComp().getDecisionData(dBuilder)
				.getDecider().getSelectedBo();

		// >> perform additional calculations here <<
	}

}