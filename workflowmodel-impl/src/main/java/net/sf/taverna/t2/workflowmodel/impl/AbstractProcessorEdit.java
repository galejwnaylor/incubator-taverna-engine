/*******************************************************************************
 * Copyright (C) 2007 The University of Manchester   
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
package net.sf.taverna.t2.workflowmodel.impl;

import net.sf.taverna.t2.workflowmodel.Edit;
import net.sf.taverna.t2.workflowmodel.EditException;
import net.sf.taverna.t2.workflowmodel.Processor;

/**
 * Abstraction of an edit acting on a Processor instance. Handles the check to
 * see that the Processor supplied is really a ProcessorImpl.
 * 
 * @author Tom Oinn
 * 
 */
public abstract class AbstractProcessorEdit implements Edit<Processor> {

	private boolean applied = false;

	private Processor processor;

	protected AbstractProcessorEdit(Processor p) {
		if (p == null) {
			throw new RuntimeException(
					"Cannot construct a processor edit with null processor");
		}
		this.processor = p;
	}

	@Override
	public final Processor doEdit() throws EditException {
		if (applied) {
			throw new EditException("Edit has already been applied!");
		}
		if (!(processor instanceof ProcessorImpl)) {
			throw new EditException(
					"Edit cannot be applied to a Processor which isn't an instance of ProcessorImpl");
		}
		ProcessorImpl pi = (ProcessorImpl) processor;
		try {
			synchronized (pi) {
				doEditAction(pi);
				applied = true;
				return this.processor;
			}
		} catch (EditException ee) {
			applied = false;
			throw ee;
		}
	}

	/**
	 * Do the actual edit here
	 * 
	 * @param processor
	 *            The ProcessorImpl to which the edit applies
	 * @throws EditException
	 */
	protected abstract void doEditAction(ProcessorImpl processor)
			throws EditException;

	@Override
	public final Processor getSubject() {
		return processor;
	}

	@Override
	public final boolean isApplied() {
		return this.applied;
	}

	@Override
	public final void undo() {
		if (!applied) {
			throw new RuntimeException(
					"Attempt to undo edit that was never applied");
		}
		throw new UnsupportedOperationException(
				"undo not supported by this interface in Taverna 3");
	}
}
