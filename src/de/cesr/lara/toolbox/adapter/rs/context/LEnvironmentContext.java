/**
 * 
 */
package de.cesr.lara.toolbox.adapter.rs.context;

import java.util.Collection;
import java.util.Set;

import de.cesr.lara.components.environment.LaraEnvironment;
import de.cesr.lara.components.environment.LaraEnvironmentListener;
import de.cesr.lara.components.environment.impl.LAbstractEnvironmentalProperty;
import de.cesr.lara.components.environment.impl.LEnvironment;
import repast.simphony.context.DefaultContext;

/**
 * @author Sascha Holzhauer
 *
 */
public class LEnvironmentContext<T> extends DefaultContext<T> implements LaraEnvironmentContext<T> {

	LaraEnvironment lEnvironment = null;
	
	public LEnvironmentContext() {
		this("LEnvironmentContext");
	}
	
	public LEnvironmentContext(String id) {
		super(id);
		lEnvironment = new LEnvironment();
	}
	
	@Override
	public void addEnvListener(LaraEnvironmentListener listener) {
		this.lEnvironment.addEnvListener(listener);
	}

	@Override
	public void addEnvListener(LaraEnvironmentListener listener, String name) {
		this.lEnvironment.addEnvListener(listener, name);
	}

	@Override
	public void addProperty(LAbstractEnvironmentalProperty<?> property) {
		this.addProperty(property);
	}

	@Override
	public boolean containsProperty(LAbstractEnvironmentalProperty<?> property) {
		return this.lEnvironment.containsProperty(property);
	}

	@Override
	public boolean containsProperty(String name) {
		return lEnvironment.containsProperty(name);
	}

	@Override
	public Set<LaraEnvironmentListener> getAllListeners() {
		return lEnvironment.getAllListeners();
	}

	@Override
	public Collection<LAbstractEnvironmentalProperty<?>> getEnvProperties() {
		return lEnvironment.getEnvProperties();
	}

	@Override
	public LAbstractEnvironmentalProperty<?> getPropertyByName(String name) {
		return this.lEnvironment.getPropertyByName(name);
	}

	@Override
	public <V> LAbstractEnvironmentalProperty<V> getTypedPropertyByName(
			String name) {
		return this.lEnvironment.getTypedPropertyByName(name);
	}

	@Override
	public boolean removeEnvListener(LaraEnvironmentListener listener) {
		return this.lEnvironment.removeEnvListener(listener);
	}

	@Override
	public void removeEnvListener(LaraEnvironmentListener listener, String name) {
		this.lEnvironment.removeEnvListener(listener, name);
	}

	@Override
	public boolean removeProperty(LAbstractEnvironmentalProperty<?> property) {
		return this.lEnvironment.removeProperty(property);
	}

	@Override
	public boolean removeProperty(String name) {
		return this.lEnvironment.removeProperty(name);
	}

	@Override
	public void updateProperty(LAbstractEnvironmentalProperty<?> property) {
		this.lEnvironment.updateProperty(property);
	}
}
