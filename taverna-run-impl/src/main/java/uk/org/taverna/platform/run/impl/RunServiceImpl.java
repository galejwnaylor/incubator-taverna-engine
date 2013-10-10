/*******************************************************************************
 * Copyright (C) 2010 The University of Manchester
 *
 *  Modifications to the initial code base are copyright of their
 *  respective authors, or their employers as appropriate.
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 ******************************************************************************/
package uk.org.taverna.platform.run.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.purl.wf4ever.robundle.Bundle;

import uk.org.taverna.platform.execution.api.ExecutionEnvironment;
import uk.org.taverna.platform.execution.api.ExecutionEnvironmentService;
import uk.org.taverna.platform.execution.api.InvalidExecutionIdException;
import uk.org.taverna.platform.execution.api.InvalidWorkflowException;
import uk.org.taverna.platform.report.State;
import uk.org.taverna.platform.report.WorkflowReport;
import uk.org.taverna.platform.run.api.InvalidRunIdException;
import uk.org.taverna.platform.run.api.RunProfile;
import uk.org.taverna.platform.run.api.RunProfileException;
import uk.org.taverna.platform.run.api.RunService;
import uk.org.taverna.platform.run.api.RunStateException;
import uk.org.taverna.scufl2.api.container.WorkflowBundle;
import uk.org.taverna.scufl2.api.core.Workflow;
import uk.org.taverna.scufl2.api.profiles.Profile;

/**
 * Implementation of the <code>RunService</code>.
 *
 * @author David Withers
 */
public class RunServiceImpl implements RunService {

	private final List<String> runs;

	private final Map<String, Run> runMap;

	private ExecutionEnvironmentService executionEnvironmentService;

	private EventAdmin eventAdmin;

	public RunServiceImpl() {
		runs = new ArrayList<String>();
		runMap = new HashMap<String, Run>();
	}

	@Override
	public Set<ExecutionEnvironment> getExecutionEnvironments() {
		return executionEnvironmentService.getExecutionEnvironments();
	}

	@Override
	public Set<ExecutionEnvironment> getExecutionEnvironments(WorkflowBundle workflowBundle) {
		return getExecutionEnvironments(workflowBundle.getMainProfile());
	}

	@Override
	public Set<ExecutionEnvironment> getExecutionEnvironments(Profile profile) {
		return executionEnvironmentService.getExecutionEnvironments(profile);
	}

	@Override
	public List<String> getRuns() {
		return runs;
	}

	@Override
	public String createRun(RunProfile runProfile) throws InvalidWorkflowException, RunProfileException {
		Run run = new Run(runProfile);
		runMap.put(run.getID(), run);
		runs.add(run.getID());
		return run.getID();
	}

	@Override
	public String open(File runFile) throws IOException {
		return null;
	}

	@Override
	public void close(String runID) throws InvalidRunIdException, InvalidExecutionIdException {

	}

	@Override
	public void save(String runID) throws InvalidRunIdException, InvalidExecutionIdException {

	}

	@Override
	public void delete(String runID) throws InvalidRunIdException, InvalidExecutionIdException {
		getRun(runID).delete();
		runMap.remove(runID);
		postEvent(RUN_DELETED, runID);
	}

	@Override
	public void start(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException {
		getRun(runID).start();
		postEvent(RUN_STARTED, runID);
	}

	@Override
	public void pause(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException {
		getRun(runID).pause();
		postEvent(RUN_PAUSED, runID);
	}

	@Override
	public void resume(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException {
		getRun(runID).resume();
		postEvent(RUN_RESUMED, runID);
	}

	@Override
	public void cancel(String runID) throws InvalidRunIdException, RunStateException, InvalidExecutionIdException {
		getRun(runID).cancel();
		postEvent(RUN_STOPPED, runID);
	}

	@Override
	public State getState(String runID) throws InvalidRunIdException {
		return getRun(runID).getState();
	}

	@Override
	public Bundle getInputs(String runID) throws InvalidRunIdException {
		return getRun(runID).getInputs();
	}

	@Override
	public Bundle getOutputs(String runID) throws InvalidRunIdException {
		return getRun(runID).getOutputs();
	}

	@Override
	public WorkflowReport getWorkflowReport(String runID) throws InvalidRunIdException {
		return getRun(runID).getWorkflowReport();
	}

	@Override
	public Workflow getWorkflow(String runID) throws InvalidRunIdException {
		return getRun(runID).getWorkflow();
	}

	@Override
	public Profile getProfile(String runID) throws InvalidRunIdException {
		return getRun(runID).getProfile();
	}

	private Run getRun(String runID) throws InvalidRunIdException {
		Run run = runMap.get(runID);
		if (run == null) {
			throw new InvalidRunIdException("Run ID " + runID + " is not valid");
		}
		return run;
	}

	private void postEvent(String topic, String runId) {
		HashMap<String, String> properties = new HashMap<>();
		properties.put("RUN_ID", runId);
		Event event = new Event(topic, properties);
		eventAdmin.postEvent(event);
	}

	public void setExecutionEnvironmentService(ExecutionEnvironmentService executionEnvironmentService) {
		this.executionEnvironmentService = executionEnvironmentService;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}