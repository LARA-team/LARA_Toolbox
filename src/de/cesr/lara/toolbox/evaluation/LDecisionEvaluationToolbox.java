/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 15.12.2010
 */
package de.cesr.lara.toolbox.evaluation;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.agents.impl.LDefaultAgentComp;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDecisionHeuristicComponent_MaxLineTotalRandomAtTie;
import de.cesr.lara.components.decision.impl.LDeliberativeDeciderFactory;
import de.cesr.lara.components.decision.impl.LDecisionConfiguration;
import de.cesr.lara.components.preprocessor.LaraBOPreselector;
import de.cesr.lara.components.preprocessor.LaraDecisionModeSelector;
import de.cesr.lara.components.preprocessor.LaraPreprocessorFactory;
import de.cesr.lara.components.preprocessor.LaraPreprocessorConfiguration;
import de.cesr.lara.components.preprocessor.impl.LDefaultLBOUpdaterBuilder;
import de.cesr.lara.components.preprocessor.impl.LPseudoPrefereceUpdater;
import de.cesr.lara.components.preprocessor.impl.LContributingBOPreselector;
import de.cesr.lara.components.preprocessor.impl.LPreprocessFactory;
import de.cesr.lara.components.util.logging.impl.Log4jLogger;

/**
 * 
 */
public class LDecisionEvaluationToolbox {

	private static Map<LaraDecisionConfiguration, LPreprocessFactory>	preprocessBuilder	= new HashMap<LaraDecisionConfiguration, LPreprocessFactory>();

	/**
	 * Logger
	 */
	static private Logger										logger				= Log4jLogger
																							.getLogger(LDecisionEvaluationToolbox.class);

	/**
	 * Creates a PreprocessBuilder to use for post processing, for instance to evaluate the selected BO in an updated
	 * environment. No preferenceWeights are updated and all recent BOs are used from memory (LContributingBOPreselector).
	 * 
	 * TODO provide further methods that allow customisation of preprocessor components
	 * 
	 * @param <A>
	 * @param <BO>
	 * @param configurator
	 * @param dBuilder
	 * @return configurator
	 */
	public static <A extends LaraAgent<A, BO>, BO extends LaraBehaviouralOption<A, BO>> LaraPreprocessorFactory<A, BO> getPostProcessPreprocessBuilder(
			LaraPreprocessorConfiguration<A, BO> configurator, LaraDecisionConfiguration dBuilder) {

		LaraPreprocessorConfiguration<A, BO> newConfigurator = configurator.clone();
		// / force preprocessor to select deliberative decision making mode (assign LDeliberativeDeciderFactory):
		configurator.setDecisionModeSelector(new LaraDecisionModeSelector<A, BO>() {
			@Override
			public void selectDecisionMode(A agent, LaraDecisionConfiguration dBuilder) {
				agent.getLaraComp().getDecisionData(dBuilder).setDeciderFactory(
						LDeliberativeDeciderFactory.<A, BO> getFactory((Class<A>) agent.getClass()));
			}
		}, dBuilder);
		configurator.setBOCollector(new LContributingBOPreselector<A, BO>(), dBuilder);
		configurator.setBOAdapter(new LDefaultLBOUpdaterBuilder<A, BO>(), dBuilder);
		configurator.setPreferenceUpdater(new LPseudoPrefereceUpdater<A>(), dBuilder);

		return configurator.getPreprocessorFactory();
	}

	/**
	 * TODO make generic
	 */
	public <A extends LaraAgent<A, BO>, BO extends LaraBehaviouralOption<A, BO>> void evaluateSelectedBoAgainstOpimalBo(
			LaraPreprocessorConfiguration<A, BO> configurator, LaraDecisionConfiguration dBuilder, A agent) {
		// exchange dBuilder because of autonomous random stream for LaraDeliberativeChoiceComponent:

		LaraDecisionConfiguration postProcessDBuilder = new LDecisionConfiguration("NeighbourhoodDecisionBuilder");
		// make sure that the heuristic as used in decisionData is used
				
		LDefaultAgentComp.setDefaultDeliberativeChoiceComp(postProcessDBuilder, new LDecisionHeuristicComponent_MaxLineTotalRandomAtTie("Uniform post-processing"));
		postProcessDBuilder.setPreferences(dBuilder.getPreferences());

		// perform a deliberative decision:
		// / Update BOs with agents' current selected BO using a common pre-process builder:
		getPostProcessPreprocessBuilder(configurator, postProcessDBuilder).getPreprocessor(postProcessDBuilder)
				.preprocess(LaraBOPreselector.LAccuracy.ACCURATE, agent);

		// / Use existing decision data:
		// / perform decision making:
		agent.getLaraComp().getDecisionData(dBuilder).getDecider().decide();

		// get selected BO:
		BO boToCompare = agent.getLaraComp().getDecisionData(dBuilder).getDecider().getSelectedBo();

		// >> perform additional calculations here <<
	}
}
