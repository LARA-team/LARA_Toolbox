package de.cesr.lara.testing.toolbox.adapater.rs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.impl.LDefaultAgentComp;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDecisionConfiguration;
import de.cesr.lara.components.decision.impl.LDeliberativeChoiceComp_MaxLineTotalRandomAtTie;
import de.cesr.lara.components.eventbus.events.LAgentDecideEvent;
import de.cesr.lara.components.eventbus.events.LAgentExecutionEvent;
import de.cesr.lara.components.eventbus.events.LAgentPerceptionEvent;
import de.cesr.lara.components.eventbus.events.LAgentPostprocessEvent;
import de.cesr.lara.components.eventbus.events.LAgentPreprocessEvent;
import de.cesr.lara.components.eventbus.events.LInternalModelInitializedEvent;
import de.cesr.lara.components.eventbus.events.LModelInstantiatedEvent;
import de.cesr.lara.components.eventbus.events.LModelStepEvent;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.eventbus.impl.LEventbus;
import de.cesr.lara.components.preprocessor.LaraPreprocessorConfigurator;
import de.cesr.lara.components.preprocessor.impl.LContributingBoCollector;
import de.cesr.lara.components.preprocessor.impl.LPreprocessorConfigurator;
import de.cesr.lara.components.util.logging.impl.Log4jLogger;
import de.cesr.lara.testing.LTestUtils.LTestAgent;
import de.cesr.lara.testing.LTestUtils.LTestBo;
import de.cesr.lara.testing.LTestUtils.LTestPreference1;
import de.cesr.lara.testing.LTestUtils.LTestPreference2;
import de.cesr.lara.toolbox.adapter.rs.LAbstractRsModel;

public class LTestRsContextBuilder extends LAbstractRsModel<LTestAgent, LTestBo, Object> {

		private Logger logger = Log4jLogger
				.getLogger(LTestRsContextBuilder.class);

		/**
		 * contains information and relevant data to process our decision one
		 */
		private LaraDecisionConfiguration decisionOneConfiguration;
		
		private Context<Object> rootContext;


		@Override
		public Context<Object> build(Context<Object> context) {
			RunEnvironment.getInstance().endAt(10.0);
			LEventbus.getInstance().subscribe(this, LInternalModelInitializedEvent.class);
		
			this.rootContext = context;
			
			LEventbus.getInstance().publish(new LModelInstantiatedEvent());
			
			return context;
		}

		@Override
		public <T extends LaraEvent> void onEvent(T event) {
			if (event instanceof LInternalModelInitializedEvent) {
				createAgents();
				initLara();
			} else if (event instanceof LModelStepEvent) {
				processStep();
			}
		}
		


		private void createAgents() {
			
			logger.info("creating agents");

			Set<Class<? extends LaraPreference>> goals = new HashSet<Class<? extends LaraPreference>>();
			goals.add(LTestPreference1.class);
			goals.add(LTestPreference2.class);

			// create two agents
			createAgent("Zero", goals);
			createAgent("One", goals);

			// init decisions
			initDecisions(goals);

			// dump info about see agents - so we can see, how they were initialized
			dumpAgentInfo();

			logger.info("agents created");
		}
		
		/**
		 * creates a new agent
		 * 
		 * @param agentId
		 * @param goals
		 */
		private void createAgent(String agentId,
				Set<Class<? extends LaraPreference>> goals) {
			LTestAgent agent = new LTestAgent(agentId);
					
			Class<? extends LaraPreference> goal1 = new LaraPreference() {
			}.getClass();
			Map<Class<? extends LaraPreference>, Double> utilities = new HashMap<Class<? extends LaraPreference>, Double>();
			LTestBo bo1 = new LTestBo(agent, utilities);
			utilities.put(goal1, 0.0);
			agent.getLaraComp().getBOMemory().memorize(bo1);
			
			rootContext.add(agent);
		}
		
		public void initDecisions(Set<Class<? extends LaraPreference>> goals) {
			// initialize decision builder (configuration for decision process)
			decisionOneConfiguration = new LDecisionConfiguration("decision one");
			decisionOneConfiguration.setPreferences(goals);
		}
		
		/**
		 * Dump some information about the agents on the logger.
		 */
		private void dumpAgentInfo() {
			// log the agents preferenceWeights for debugging reasons
			for (Object agent : rootContext) {
				logger.info("  " + ((LTestAgent)agent).getAgentId() + ": ");
				for (Entry<Class<? extends LaraPreference>, Double> entry : ((LTestAgent)agent).getLaraComp().getPreferenceWeights().entrySet()) {
					logger.info("     preference for "
							+ entry.getKey().getSimpleName() + " : "
							+ entry.getValue());
				}
			}
		}
		
		/**
		 * simulates one tick. currently our agents decide about "decision one" and
		 * log the best behavioural option
		 */
		public void processStep() {
			logger.info("MyModel.step " + getCurrentStep());
			
			// process decision one
			processDecision(decisionOneConfiguration);
		}
		
		/**
		 * processes a given decision by firing the corresponding events
		 * 
		 * @param decisionConfiguration
		 */
		public void processDecision(
				LaraDecisionConfiguration decisionConfiguration) {

			// perceive
			eventBus.publish(new LAgentPerceptionEvent(decisionConfiguration));
			// preprocess
			eventBus.publish(new LAgentPreprocessEvent(decisionConfiguration));
			// decide
			eventBus.publish(new LAgentDecideEvent(decisionConfiguration));
			// postprocess
			eventBus.publish(new LAgentPostprocessEvent(decisionConfiguration));
			// execute
			eventBus.publish(new LAgentExecutionEvent(decisionConfiguration));
		}


		private void initLara() {
			
			// Adjust preprocessor:
			LaraPreprocessorConfigurator<LTestAgent, LTestBo> configurator = LPreprocessorConfigurator
					.<LTestAgent, LTestBo> getNewPreprocessorConfigurator();

			configurator.setBOCollector(new LContributingBoCollector<LTestAgent, LTestBo>(), 
					decisionOneConfiguration);

			LDefaultAgentComp.setDefaultDeliberativeChoiceComp(decisionOneConfiguration,
					LDeliberativeChoiceComp_MaxLineTotalRandomAtTie
							.getInstance(null));

			// createAgents:

			logger.info("MyLRsContextBuilder initialised");
		}

		@Override
		protected Iterable<LTestAgent> getAgentIterable() {
			Set<LTestAgent> agents = new HashSet<LTestAgent>();
			
			for (Object o : rootContext.getObjects(LTestAgent.class)) {
				if (o instanceof LTestAgent) {
					agents.add((LTestAgent)o);
				}
			}
			return agents;
		}
	}