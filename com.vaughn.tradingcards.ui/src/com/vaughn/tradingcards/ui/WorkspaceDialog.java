package com.vaughn.tradingcards.ui;

import java.io.File;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.util.Geometry;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("unused")
public class WorkspaceDialog extends Dialog {
	private WorkspaceData launchData;
	private Text text;
	private boolean suppressAskAgain;

	public WorkspaceDialog (Shell parentShell, WorkspaceData launchData, boolean suppressAskAgain) {
		super(parentShell);
		this.launchData = launchData;
		//	If suppressAskAgain is true, the user is selecting the workspace
		//	one time or all the time. Programmers choice.
		this.suppressAskAgain = suppressAskAgain;
		
	}
	
	//	Override to make custom dialog.
	@Override
	protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(1, false);
        container.setLayout(layout);
        
        createSelectionBar(container);
        //	Button dontAskAgain is located in creatButtonBar().
        
        return container;
	}

	//	Override to add the dontAskAgain button.
	@Override
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font
		// size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton - Originally 0.
//		layout.makeColumnsEqualWidth = true; //	comment to keep the buttons from stretching
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());

		//	added for the buttonBar to hold a different type of button.
		if(!this.suppressAskAgain) {
	        Button dontAskAgain = new Button(composite, SWT.CHECK);
	        data.grabExcessHorizontalSpace = true;
	        data.horizontalAlignment = GridData.FILL;
	        dontAskAgain.setText("Don't ask again?");
	        dontAskAgain.setLayoutData(data);
	        dontAskAgain.addSelectionListener(new SelectionAdapter() {
	        	@Override
	        	public void widgetSelected(SelectionEvent event) {
	        		launchData.setShowDialog(!dontAskAgain.getSelection());
	        	}
	        });
		}
        
		// Add the buttons to the button bar.
		createButtonsForButtonBar(composite);
		return composite;
	}

	/*
	 * see org.eclipse.jface.Window.getInitialLocation()
	 */
	@Override
	protected Point getInitialLocation(Point initialSize) {
		Composite parent = getShell().getParent();

		Monitor monitor = parent.getMonitor();
		Rectangle monitorBounds = monitor.getClientArea();
		Point centerPoint = Geometry.centerPoint(monitorBounds);

		return new Point(centerPoint.x - (initialSize.x / 2), Math.max(
				monitorBounds.y, Math.min(centerPoint.y
						- (initialSize.y * 2 / 3), monitorBounds.y
						+ monitorBounds.height - initialSize.y)));
	}
	
	// Override the title of the dialog.
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Choose Workspace Dialog");
    }
	
    //	Override the size of the dialog.
    @Override
    protected Point getInitialSize() {
        return new Point(400, 130);
    }

    @Override
	protected void okPressed() {
    	workspaceSelected(getWorkspaceLocation());
    	launchData.setRecentWorkspaces(text.getText());
    	super.okPressed();
	}

	/**
	 * Set the selected workspace to the given String and close the dialog
	 *
	 * @param workspace
	 */
	private void workspaceSelected(String workspace) {
		launchData.workspaceSelected(TextProcessor.deprocess(workspace));
	}

	/**
	 * Get the workspace location from the widget.
	 * @return String
	 */
	protected String getWorkspaceLocation() {
		return text.getText();
	}

    @Override
	protected void cancelPressed() {
    	launchData.workspaceSelected(null);
        super.cancelPressed();
	}
    
    //	This was a copy of createButtonBar().
    //	Used to create a bar for the user to select a workspace.
    protected Control createSelectionBar(Composite parent) {
    	Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
		
		Label input = new Label(composite, SWT.NONE);
        input.setText("Directory:");
        
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        
        text = new Text(composite, SWT.BORDER);
        text.setFocus();
        text.setLayoutData(data);
        
        String workspace = launchData.getRecentWorkspaces();
        if (workspace == null || workspace == "") {
        	text.setText(launchData.getInitialDefault());
        }
        else {
        	text.setText(workspace);
        }
        
        Button browseButton = new Button(composite, SWT.PUSH);
        browseButton.setText("Browse...");
        browseButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent event) {
        		DirectoryDialog dialog = new DirectoryDialog(composite.getShell());
        		dialog.setFilterPath(System.getProperty("user.home"));
        		String selectedDir = dialog.open();
        		if(selectedDir != null){
        			text.setText(selectedDir);
        		}
        		// else don't setText
        	}
        });

		return composite;
    }

    /**
     * Show the dialog to the user (if needed). When this method finishes,
     * #getSelection will return the workspace that should be used (whether it
     * was just selected by the user or some previous default has been used.
     * The parameter can be used to override the users preference.  For example,
     * this is important in cases where the default selection is already in use
     * and the user is forced to choose a different one.
     *
     * @param force
     *            true if the dialog should be opened regardless of the value of
     *            the show dialog checkbox
     */
    public void prompt(boolean force) {
        if (force) {
            open();

            if (getReturnCode() == CANCEL) {
		launchData.workspaceSelected(null);
	    }

            return;
        }

        String recent = launchData.getRecentWorkspaces();

        // If the selection dialog was not used then the workspace to use is either the
        // most recent selection or the initialDefault (if there is no history).
        String workspace = null;
        if (recent != null && recent.length() > 0) {
		workspace = recent;
	}
        if (workspace == null || workspace.length() == 0) {
		workspace = launchData.getInitialDefault();
	}
        launchData.workspaceSelected(TextProcessor.deprocess(workspace));
    }

}
