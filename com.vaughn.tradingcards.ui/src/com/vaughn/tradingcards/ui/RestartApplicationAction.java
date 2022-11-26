package com.vaughn.tradingcards.ui;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class RestartApplicationAction {
    @Execute
    public void execute(IWorkbench workbench) {
    	Application.getInstance().setRestartCode();
        workbench.close();
    }
}
