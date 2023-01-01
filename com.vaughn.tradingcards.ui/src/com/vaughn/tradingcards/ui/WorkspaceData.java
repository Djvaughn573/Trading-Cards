package com.vaughn.tradingcards.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;

import com.vaughn.tradingcards.ui.preference.XmlPreferenceStore;

public class WorkspaceData {
	private static final Location PERS_LOCATION = Platform.getInstallLocation();
	private static final String PERS_FOLDER = "com.vaughn.tradingcards.ui";
	private static final String PERS_FILE = "workspaceData";
	
	private boolean showDialog;
	private String initialDefault;
	private String selection;
	private String recentWorkspaces;

	public WorkspaceData (URL instanceURL) {
		readPersistedData();
        if (instanceURL != null) {
			setInitialDefault(new File(instanceURL.getFile()).toString());
		}
	}
	
	public String getSelection() {
		return selection;
	}

    /**
     * The argument workspace has been selected, update the receiver.  Does not
     * persist the new values.
     */
    public void workspaceSelected(String dir) {
        // this just stores the selection, it is not inserted and persisted
        // until the workspace is actually selected
        selection = dir;
    }
    
    private boolean readPersistedData() {
    	XmlPreferenceStore store = getPreferenceStore();
		if (!store.loadFile(store.file)) {
			writeDefaultPersistData(store);
			store.loadFile(store.file);
		}
		
		showDialog = store.getBoolean("showDialog");
		initialDefault = store.getString("initialDefault");
		selection = store.getString("selection");
		recentWorkspaces = store.getString("recentWorkspaces");
    	
    	return true;
    }
	
    public void writePersistedData() {
    	XmlPreferenceStore store = getPreferenceStore();
    	
    	store.setValue("showDialog", showDialog);
    	store.setValue("initialDefault", initialDefault);
    	store.setValue("selection", selection);
    	store.setValue("recentWorkspaces", recentWorkspaces);
    	
    	store.saveFile(store.file);
    }

    /**
     * Set this data's initialDefault parameter to a properly formatted version
     * of the argument directory string. The proper format is to the platform
     * appropriate separator character without meaningless leading or trailing
     * separator characters.
     */
    private void setInitialDefault(String dir) {
        if (dir == null || dir.length() <= 0) {
            initialDefault = null;
            return;
        }

        dir = new Path(dir).toOSString();
        while (dir.charAt(dir.length() - 1) == File.separatorChar) {
			dir = dir.substring(0, dir.length() - 1);
		}
        initialDefault = dir;
    }
    
    public String getInitialDefault() {
    	if (initialDefault == null) {
    		try {
				URL url = new URL(PERS_LOCATION.getURL(), "workspace");
				initialDefault = url.getFile();//getPreferenceStore().locationToString(PERS_LOCATION);
			} catch (MalformedURLException e) {
				return null;
			}
    	}
    	return initialDefault;
    }
    
    private void writeDefaultPersistData(XmlPreferenceStore store) {
    	String dir = store.locationToString(PERS_LOCATION);//new File(PERS_LOCATION.getURL().getPath()).toString();
    	dir = dir + File.separator + "workspace";
    	
    	store.setValue("showDialog", true);
    	store.setValue("initialDefault", dir);
    	store.setValue("selection", null);
    	store.setValue("recentWorkspaces", null);
    	
    	store.saveFile(store.file);
    }

    private XmlPreferenceStore getPreferenceStore() {
		XmlPreferenceStore store = 
				new XmlPreferenceStore(PERS_LOCATION, PERS_FOLDER, PERS_FILE);
    	return store;
    }
    
    public boolean getShowDialog() {
    	return showDialog;
    }
    
    public void setShowDialog(boolean value) {
    	showDialog = value;
    }

    /**
     * Return an array of recent workspaces sorted with the most recently used at
     * the start.
     * 
     * currently doesn't return a String[]
     */
    public String getRecentWorkspaces() {
        return recentWorkspaces;
    }

    /**
     * Sets the list of recent workspaces.
     */
    public void setRecentWorkspaces(String workspaces) {
    	if (workspaces != null || workspaces != "") {	//	"C:\" should be the least.
			recentWorkspaces = workspaces;
    	}
    }
    
}
