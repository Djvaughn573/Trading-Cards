package com.vaughn.tradingcards.perspectivepart.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class PokemonPerspectiveHandler {
//	@Execute
//	public void execute() {
//		System.out.println((this.getClass().getSimpleName() + " called"));
//	}

//	@Execute
//	public void execute(MApplication app, EPartService partService, EModelService modelService) {
//		MPerspective element = (MPerspective) modelService.find("Pokemon", app);
//		// now switch perspective
//		partService.switchPerspective(element);
//	}
	
	@Execute
	public void execute(MApplication application, EModelService service, EPartService partService) {
		// search for a part with the following ID
		String ID = "com.vaughn.tradingcards.perspectivepart.perspective.pokemon";
		MUIElement element = service.find(ID, application);
		MPerspective perspective = service.getPerspectiveFor(element);
		System.out.println(perspective);
		// TODO do something useful with the perspective
		partService.switchPerspective(perspective);
	}
}
