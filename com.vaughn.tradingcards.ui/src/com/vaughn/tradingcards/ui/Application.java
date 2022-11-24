package com.vaughn.tradingcards.ui;

//import java.net.URL;

//import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
//import org.eclipse.osgi.service.datalocation.Location;

public class Application implements IApplication {
    private static Application application;
    private Integer exit_code = IApplication.EXIT_OK;
    private E4Application e4Application;
//    private Location location = Platform.getInstanceLocation();

    public static Application getInstance() {
        return application;
    }

    public void setRestartCode() {
        exit_code = IApplication.EXIT_RESTART;
    }

    @Override
    public Object start(IApplicationContext context) throws Exception {
//    	location = Platform.getInstanceLocation();
//    	location.set(new URL("file", null, "/home/temp"), false);
        application = this;
        e4Application = new E4Application();
        e4Application.start(context);
        return exit_code;
    }

    @Override
    public void stop() {
        e4Application.stop();
    }
}
