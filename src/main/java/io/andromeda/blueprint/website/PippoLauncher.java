package io.andromeda.blueprint.website;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.core.Pippo;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Locale;

import static io.andromeda.blueprint.website.Constants.APPLICATION_DOMAIN_NAME;
import static io.andromeda.blueprint.website.Constants.APPLICATION_EMAIL;
import static io.andromeda.blueprint.website.Constants.APPLICATION_TITLE;
import static io.andromeda.blueprint.website.Constants.COPYRIGHT_STARTYEAR;
import static io.andromeda.blueprint.website.Constants.LAST_UPDATE;

/**
 * Run application from here.
 */
public class PippoLauncher {
    /** The logger instance for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PippoLauncher.class);

    private PippoLauncher() {

    }

    public static void main(String[] args) {
        Pippo pippo = new Pippo(new PippoApplication());

        String applicationName = pippo.getApplication().getApplicationName();
        String applicationVersion = pippo.getApplication().getApplicationVersion();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("{} is closing normally.", applicationName);
            LOGGER.info(
                    "---------------------------------------------------------------------------------------------------");
        }, "Shutdown-thread"));

        LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOGGER.info("Welcome to the webapp {} in version {}.", applicationName, applicationVersion);
        LOGGER.info("Application title:           {}",
                pippo.getApplication().getPippoSettings().getRequiredString(APPLICATION_TITLE));
        LOGGER.info("Application domain name:     {}",
                pippo.getApplication().getPippoSettings().getRequiredString(APPLICATION_DOMAIN_NAME));
        LOGGER.info("Application info email:      {}",
                pippo.getApplication().getPippoSettings().getRequiredString(APPLICATION_EMAIL));
        LOGGER.info("Application last update:     {}",
                pippo.getApplication().getPippoSettings().getRequiredString(LAST_UPDATE));
        LOGGER.info("Application copyright start: {}",
                pippo.getApplication().getPippoSettings().getRequiredString(COPYRIGHT_STARTYEAR));
        Locale.setDefault(Locale.ENGLISH);
        LOGGER.info("Locale: " + Locale.getDefault());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Charset: {}", Charset.defaultCharset().toString());
        }


        /* ---------------------------------------------------------------------------------------------------------- */

        /* Setting the static files directory to the external /public one ------------------------------------------- */
        String currentPath = System.getProperty("user.dir");
        currentPath = currentPath + File.separator + "public";
        pippo.getApplication().addFileResourceRoute("/", currentPath);
        LOGGER.info("Public path set to: {}", currentPath);
        /* ---------------------------------------------------------------------------------------------------------- */

        /* Starting Pippo ------------------------------------------------------------------------------------------- */
        pippo.start();
    }

}
