/*******************************************************************************
 * Copyright (c) 2015 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.gef4.mvc.examples.logo.behaviors;

import org.eclipse.gef4.mvc.behaviors.AbstractBehavior;
import org.eclipse.gef4.mvc.examples.logo.parts.FXGeometricCurvePart;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 * A behavior that regulates the clickable area width of the
 * {@link FXGeometricCurvePart}'s visual dependent on the zoom level.
 *
 * @author anyssen
 *
 */
public class FXClickableAreaBehavior extends AbstractBehavior<Node> {

	private static final double ABSOLUTE_CLICKABLE_WIDTH = 8;
	private DoubleBinding clickableAreaBinding;

	private final ChangeListener<? super Number> scaleXListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable,
				Number oldValue, Number newValue) {
			clickableAreaBinding.invalidate();
		}
	};

	@Override
	protected void doActivate() {
		clickableAreaBinding = new DoubleBinding() {
			@Override
			protected double computeValue() {
				double localClickableWidth = ABSOLUTE_CLICKABLE_WIDTH
						/ ((FXViewer) getHost().getRoot().getViewer())
								.getCanvas().getContentTransform().getMxx();
				return Math.min(localClickableWidth, ABSOLUTE_CLICKABLE_WIDTH);
			}
		};
		getHost().getVisual().getCurveNode().clickableAreaWidthProperty()
				.bind(clickableAreaBinding);
		((FXViewer) getHost().getRoot().getViewer()).getCanvas()
				.getContentTransform().mxxProperty()
				.addListener(scaleXListener);
	}

	@Override
	protected void doDeactivate() {
		clickableAreaBinding.dispose();
		((FXViewer) getHost().getRoot().getViewer()).getCanvas()
				.getContentTransform().mxxProperty()
				.removeListener(scaleXListener);
	}

	@Override
	public FXGeometricCurvePart getHost() {
		return (FXGeometricCurvePart) super.getHost();
	}

}