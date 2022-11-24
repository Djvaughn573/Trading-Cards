package com.vaughn.magic.ui.parts;

import javax.annotation.PostConstruct;

//import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TableViewer;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Label;

public class CardInformationPart {
	private TableViewer tableViewer;
//	private Label imageLabel;
//	private Image image;
	
	public CardInformationPart(){
		System.out.println(this.getClass().getSimpleName() + " constructed");
	}
	
	@PostConstruct
	public void createComposite(Composite parent){
		parent.setLayout(new GridLayout(1, false));

//		imageLabel = new Label(parent, SWT.NONE);
//		image = new Image(parent.getDisplay(), Platform.getInstanceLocation() + ".\\Cards\\Card600.jpg");
//		imageLabel.setImage(image);
		
		tableViewer = new TableViewer(parent);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
	}
}
