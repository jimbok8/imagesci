/**
 *       Java Image Science Toolkit
 *                  --- 
 *     Multi-Object Image Segmentation
 *
 * Center for Computer-Integrated Surgical Systems and Technology &
 * Johns Hopkins Applied Physics Laboratory &
 * The Johns Hopkins University
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.  The license is available for reading at:
 * http://www.gnu.org/copyleft/lgpl.html
 *
 * @author Blake Lucas (blake@cs.jhu.edu)
 */
package org.imagesci.demo;


import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.vecmath.Point3i;

import org.imagesci.muscle.MuscleActiveContour3D;
import org.imagesci.utility.PhantomMetasphere;

import edu.jhu.cs.cisst.vent.VisualizationApplication;
import edu.jhu.cs.cisst.vent.widgets.VisualizationMUSCLE3D;
import edu.jhu.ece.iacl.jist.io.NIFTIReaderWriter;
import edu.jhu.ece.iacl.jist.structures.image.ImageDataFloat;
import edu.jhu.ece.iacl.jist.structures.image.ImageDataInt;

// TODO: Auto-generated Javadoc
/**
 * The Class Example4b_muscle3d.
 */
public class Example4b_muscle3d extends AbstractExample {
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		(new Example4b_muscle3d()).launch(args);
	}

	/* (non-Javadoc)
	 * @see org.imagesci.demo.AbstractExample#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Parametric multi-object active contour segmentation with 3D MUSCLE.";
	}

	/* (non-Javadoc)
	 * @see org.imagesci.demo.AbstractExample#getName()
	 */
	@Override
	public String getName() {
		return "MUSCLE 3D";
	}

	/* (non-Javadoc)
	 * @see org.imagesci.demo.AbstractExample#launch(java.io.File, java.lang.String[])
	 */
	@Override
	public void launch(File workingDirectory, String[] args) {
		boolean showGUI = true;
		if (args.length > 0 && args[0].equalsIgnoreCase("-nogui")) {
			showGUI = false;
		}
		File flabel = new File(workingDirectory, "ufo_labels.nii");
		File fdistfield = new File(workingDirectory, "ufo_distfield.nii");
		ImageDataInt initLabels = new ImageDataInt(NIFTIReaderWriter
				.getInstance().read(flabel));
		ImageDataFloat initDistfield = new ImageDataFloat(NIFTIReaderWriter
				.getInstance().read(fdistfield));
		PhantomMetasphere metasphere = new PhantomMetasphere(new Point3i(128,
				128, 128));
		metasphere.setNoiseLevel(0.1);
		metasphere.setFuzziness(0.5f);
		metasphere.setInvertImage(true);
		metasphere.solve();
		ImageDataFloat refImage = metasphere.getImage();

		MuscleActiveContour3D activeContour = new MuscleActiveContour3D();
		activeContour.setPressure(refImage, 1.0f);
		activeContour.setCurvatureWeight(0.1f);
		activeContour.setTargetPressure(0.5f);
		activeContour.setMaxIterations(130);
		activeContour.setReferenceImage(refImage);
		activeContour.setInitialLabelImage(initLabels);
		activeContour.setInitialDistanceFieldImage(initDistfield);
		if (showGUI) {
			try {
				activeContour.init();
				VisualizationMUSCLE3D visual = new VisualizationMUSCLE3D(512,
						512, activeContour);
				VisualizationApplication app = new VisualizationApplication(
						visual);
				app.setPreferredSize(new Dimension(920, 650));
				app.setShowToolBar(true);
				app.addListener(visual);
				app.runAndWait();
				visual.dispose();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			activeContour.solve();
		}

	}

}
