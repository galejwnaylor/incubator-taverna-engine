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

import net.sf.taverna.t2.workflowmodel.Dataflow;
import net.sf.taverna.t2.workflowmodel.EditException;
import net.sf.taverna.t2.workflowmodel.Merge;

/**
 * An Edit class responsible for adding a Merge to the dataflow.
 * 
 * @author Tom Oinn
 * 
 */
public class AddMergeEdit extends AbstractDataflowEdit {

	private Merge merge;

	protected AddMergeEdit(Dataflow dataflow, Merge merge) {
		super(dataflow);
		this.merge = merge;
	}

	/**
	 * Adds the Merge instance to the Dataflow
	 * 
	 * @throws EditException
	 *             if the edit has already taken place (without an intermediate
	 *             undo) or a processor with that name already exists.
	 */
	@Override
	protected void doEditAction(DataflowImpl dataflow) throws EditException {
		if (!(merge instanceof MergeImpl)) {
			throw new EditException(
					"The Merge is of the wrong implmentation, it should be of type MergeImpl");
		}
		dataflow.addMerge((MergeImpl) merge);
	}
}
