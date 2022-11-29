package com.vaughn.tradingcards.ui.temp;

import java.io.*;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
//import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.ide.ChooseWorkspaceData;
import org.eclipse.ui.internal.ide.ChooseWorkspaceDialog;
//import org.eclipse.ui.internal.ide.ChooseWorkspaceWithSettingsDialog;

//import com.vaughn.tradingcards.ui.Application;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
    private static Application application;
    private E4Application e4Application = new E4Application();
    private URL url;
    private Display display;
    private Location instanceLoc;
//    private Shell shell;

    public Application(){
    }
    
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
        display = PlatformUI.createDisplay();
        instanceLoc = Platform.getInstanceLocation();
        boolean isWorkspaceSelected = false;

        try {
            url = new File(System.getProperty("user.home"), "workspace").toURI().toURL();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            if (isWorkspaceSelected)  {
                int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor(/*display*/));
                if (returnCode == PlatformUI.RETURN_RESTART) {
                    return IApplication.EXIT_RESTART;
                }
            }

            e4Application.start(context);
            return IApplication.EXIT_OK;

        } finally {
            display.dispose();
        }
    }

	@Override
	public void stop() {
		e4Application.stop();
//		if (!PlatformUI.isWorkbenchRunning())
//			return;
//		final IWorkbench workbench = PlatformUI.getWorkbench();
//		final Display display = workbench.getDisplay();
//		display.syncExec(new Runnable() {
//			public void run() {
//				if (!display.isDisposed())
//					workbench.close();
//			}
//		});
	}
	
//	@Override
//	public void stop() {
//		e4Application.stop();
//	}
}
