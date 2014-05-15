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

import net.sf.taverna.t2.annotation.AnnotationAssertion;
import net.sf.taverna.t2.annotation.AnnotationBeanSPI;
import net.sf.taverna.t2.annotation.CurationEvent;
import net.sf.taverna.t2.annotation.CurationEventBeanSPI;
import net.sf.taverna.t2.annotation.impl.AnnotationAssertionImpl;
import net.sf.taverna.t2.workflowmodel.Edit;
import net.sf.taverna.t2.workflowmodel.EditException;

public class AddCurationEventEdit<T extends AnnotationBeanSPI, S extends CurationEventBeanSPI>
		implements Edit<AnnotationAssertion<T>> {
	private AnnotationAssertion<T> annotationAssertion;
	private CurationEvent<S> curationEvent;
	private boolean applied;

	public AddCurationEventEdit(AnnotationAssertion<T> annotationAssertion,
			CurationEvent<S> curationEvent) {
		this.annotationAssertion = annotationAssertion;
		this.curationEvent = curationEvent;
	}

	@Override
	public final AnnotationAssertion<T> doEdit() throws EditException {
		if (applied) {
			throw new EditException("Edit has already been applied");
		}
		if (!(annotationAssertion instanceof AnnotationAssertionImpl)) {
			throw new EditException(
					"Object being edited must be instance of AnnotationAssertionImpl");
		}

		try {
			synchronized (annotationAssertion) {
				((AnnotationAssertionImpl) annotationAssertion)
						.addCurationEvent(curationEvent);
				applied = true;
				return this.annotationAssertion;
			}
		} catch (Exception e) {
			applied = false;
			throw new EditException("There was a problem with the edit", e);
		}
	}

	@Override
	public final Object getSubject() {
		return annotationAssertion;
	}

	@Override
	public final boolean isApplied() {
		return applied;
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
