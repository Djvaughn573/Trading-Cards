package com.vaughn.magic.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class MagicPerspectiveHandler {
	@Execute
	public void execute(MApplication application, EModelService service, EPartService partService) {
		// search for a part with the following ID
		String ID = "com.vaughn.tradingcards.ui.perspective.magicperspective";
		MUIElement element = service.find(ID, application);
		MPerspective perspective = service.getPerspectiveFor(element);
		System.out.println(perspective);
		// TODO do something useful with the perspective
		partService.switchPerspective(perspective);
	}
}
