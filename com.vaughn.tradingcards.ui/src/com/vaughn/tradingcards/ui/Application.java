package com.vaughn.tradingcards.ui;

import java.io.*;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.ide.ChooseWorkspaceData;
import org.eclipse.ui.internal.ide.ChooseWorkspaceDialog;
//import org.eclipse.ui.internal.ide.ChooseWorkspaceWithSettingsDialog;

//import com.vaughn.tradingcards.ui.Application;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
    private static Application application;
    private static E4Application e4Application = new E4Application();
//    private URL url;
//    private static Display display;
//    private Location instanceLoc;
//    private Shell shell;

//    public Application() {
//    }
//    
//    public Application getInstance() {
//    	return application;
//    }
    
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        Location instanceLoc = Platform.getInstanceLocation();
        boolean isWorkspaceSelected = false;

        try {
            URL url = new File(System.getProperty("user.home"), "workspace").toURI().toURL();
            ChooseWorkspaceData data = new ChooseWorkspaceData(url);
            // ChooseWorkspaceDialog(Shell parentShell, ChooseWorkspaceData launchData, boolean supressAskAgain, boolean centerOnMonitor)
            ChooseWorkspaceDialog dialog = new ChooseWorkspaceDialog(display.getActiveShell(), data, false, true);
            dialog.prompt(true);
            String selection = data.getSelection();
            if (selection != null)  {
                isWorkspaceSelected = true;
                data.writePersistedData();
                url = new File(selection).toURI().toURL();
                instanceLoc.set(url, false);
            }
            // if ChooseWorkspaceDialog.cancelPressed() happens
            if (selection == null)
            	return IApplication.EXIT_OK;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
//            if (isWorkspaceSelected)  {
//                int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor(/*display*/));
//                if (returnCode == PlatformUI.RETURN_RESTART) {
//                    return IApplication.EXIT_RESTART;
//                }
//            }

            application = this;
            e4Application.start(context);
            return IApplication.EXIT_OK;
	
        } finally {
            display.dispose();
        }
    }

	@Override
	public void stop() {
		e4Application.stop();
	}
}
