package org.eclipse.gef4.fx.jface;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import javafx.scene.paint.Paint;

/**
 * A label provider for a {@link Paint} value.
 *
 * @author anyssen
 *
 */
public class FXPaintLabelProvider extends LabelProvider {

	private Image image;

	@Override
	public void dispose() {
		disposeImage();
		super.dispose();
	}

	private void disposeImage() {
		if (image != null) {
			image.dispose();
			image = null;
		}
	}

	@Override
	public Image getImage(Object element) {
		disposeImage();
		ImageData imageData = FXPaintUtils.getPaintImageData(32, 12, (Paint) element);
		image = new Image(Display.getCurrent(), imageData);
		return image;
	}

	@Override
	public String getText(Object element) {
		return FXPaintUtils.getPaintDisplayText((Paint) element);
	}

}