package com.vaughn.tradingcards.ui.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Shell;

import com.vaughn.tradingcards.ui.WorkspaceData;
import com.vaughn.tradingcards.ui.WorkspaceDialog;

@SuppressWarnings("restriction")
public class LifeCycleHandler {
	@PostContextCreate
	public void postContextCreate (IApplicationContext context) {// throws URISyntaxException {
		//	Get the location and stop if set.
		Location instanceLocation = Platform.getInstanceLocation();
		
		if (instanceLocation.isSet()) {
			//	Error starting application.
			System.exit(Window.CANCEL);//return;
		}
		
		//	Get the user to select the workspace location.
		WorkspaceData data = new WorkspaceData(instanceLocation.getURL());
		WorkspaceDialog dialog = new WorkspaceDialog(new Shell(), data, false);
		dialog.prompt(data.getShowDialog());
		
		try {
			String selection = data.getSelection();
			if (selection != null) {
				data.writePersistedData();
				URL url = new File(selection).toURI().toURL();
				instanceLocation.set(url, false);
			}
			else {
				System.exit(Window.CANCEL);
			}
		} catch (IllegalStateException event) {
			//	Launch is canceled. There was an error.
//			event.printStackTrace();
			System.out.println("ERROR: IllegalStateException");
			System.exit(Window.CANCEL);
		} catch (IOException event) {
//			event.printStackTrace();
			System.out.println("ERROR: IOException");
			System.exit(Window.CANCEL);
		}
	}
}
