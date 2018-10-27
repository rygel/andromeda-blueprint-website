package io.andromeda.blueprint.website;

import io.andromeda.fragments.Configuration;
import io.andromeda.fragments.Fragments;
import io.andromeda.fragments.Utilities;
import io.andromeda.fragments.types.RouteType;
import io.andromeda.pippo.routes.ContactRoute;
import io.andromeda.pippo.routes.ContactRouteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.core.Application;
import ro.pippo.core.PippoSettings;
import ro.pippo.core.RuntimeMode;
import ro.pippo.core.route.TrailingSlashHandler;

import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import static io.andromeda.blueprint.website.Constants.APPLICATION_DOMAIN_NAME;
import static io.andromeda.blueprint.website.Constants.APPLICATION_EMAIL;
import static io.andromeda.blueprint.website.Constants.APPLICATION_TITLE;
import static io.andromeda.blueprint.website.Constants.COPYRIGHT_STARTYEAR;
import static io.andromeda.blueprint.website.Constants.LAST_UPDATE;

/**
 * A simple Pippo application.
 *
 * @see PippoLauncher#main(String[])
 */
public class PippoApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(PippoApplication.class);
    private static final String PROTOCOL = "http://";

    @Override
    protected void onInit() {
        PippoSettings settings = this.getPippoSettings();
        String domainName = settings.getRequiredString(APPLICATION_DOMAIN_NAME);
        Map<String, Object> globalContext = getDefaultContext();

        getRouter().ignorePaths("/favicon.ico");

        // To have trailing slashes redirected to the non-slash version.
        ANY("/.*", new TrailingSlashHandler(false));

        // Send the index page
        GET("/", routeContext -> {
            routeContext.render("index", globalContext);
        });

        // send about page
        GET("/about", routeContext -> {
            routeContext.render("about", globalContext);
        });

        ContactRouteConfiguration contactConfiguration = new ContactRouteConfiguration("/contact", "contact",
                "Contact", "Andromeda Website Blueprint Contact Form",
                "info@blueprint.andromeda.io", "Andromeda Website Blueprint",
                "info@blueprint.andromeda.io");
        contactConfiguration.setHasSubject(true);
        addRouteGroup(new ContactRoute(contactConfiguration, globalContext));

        String currentPath = System.getProperty("user.dir");
        Configuration rootConfiguration  = new Configuration("root", "/",
                Paths.get(currentPath + "/data/fragments/root"), null, "static");
        rootConfiguration.setDomain(domainName);
        rootConfiguration.setProtocol(PROTOCOL);
        rootConfiguration.setRegisterOverviewRoute(false);

        Configuration blogConfiguration  = new Configuration("blog", "/blog",
                Paths.get(currentPath + "/data/fragments/blog"), "blog_overview", "blog_post");
        blogConfiguration.setDomain(domainName);
        blogConfiguration.setProtocol(PROTOCOL);
        blogConfiguration.setRegisterOverviewRoute(true);
        blogConfiguration.setRouteType(RouteType.BLOG);

        Configuration productsConfiguration  = new Configuration("products", "/products",
                Paths.get(currentPath + "/data/fragments/products"), "products_overview", "products_post");
        productsConfiguration.setDomain(domainName);
        productsConfiguration.setProtocol(PROTOCOL);
        productsConfiguration.setRegisterOverviewRoute(true);

        Configuration servicesConfiguration  = new Configuration("services", "/services",
                Paths.get(currentPath + "/data/fragments/services"), "services_overview", "services_post");
        servicesConfiguration.setDomain(domainName);
        servicesConfiguration.setProtocol(PROTOCOL);
        servicesConfiguration.setRegisterOverviewRoute(true);

        Fragments rootFragments = new Fragments(this, globalContext, rootConfiguration);
        Fragments blogFragments = new Fragments(this, globalContext, blogConfiguration);
        Fragments productsFragments = new Fragments(this, globalContext, productsConfiguration);
        Fragments servicesFragments = new Fragments(this, globalContext, servicesConfiguration);
    }

    private Map<String, Object> getDefaultContext() {
        PippoSettings settings = this.getPippoSettings();

        final Map<String, Object> context = new TreeMap<>();
        context.put("apptitle", settings.getRequiredString(APPLICATION_TITLE));
        context.put("domain_name", settings.getRequiredString(APPLICATION_DOMAIN_NAME));
        context.put("appver", this.getApplicationVersion());
        context.put("info_email", Utilities.obfuscate(settings.getRequiredString(APPLICATION_EMAIL)));
        context.put("copyrightdate", getCopyrightDate());
        context.put("last_update", settings.getRequiredString(LAST_UPDATE));
        context.put("runtime_mode", RuntimeMode.getCurrent().toString());

        return context;
    }

    private String getCopyrightDate() {
        PippoSettings settings = this.getPippoSettings();
        String copyrightStartyear = settings.getRequiredString(COPYRIGHT_STARTYEAR);

        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        if (copyrightStartyear.equals(currentYear)) {
            return copyrightStartyear;
        } else {
            return copyrightStartyear + " - " + Calendar.getInstance().get(Calendar.YEAR);
        }
    }

}
