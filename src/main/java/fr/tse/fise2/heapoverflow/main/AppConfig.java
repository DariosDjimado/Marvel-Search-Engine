package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.database.AppConfigRow;
import fr.tse.fise2.heapoverflow.database.AppConfigsTable;
import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.tasks.SetupMVC;
import okhttp3.Cache;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;

/**
 * App configuration
 *
 * @author Darios DJIMADO
 */
public class AppConfig {

    private static final String[] tables = {
            "users",
            "cache_urls",
            "collections",
            "elements",
            "elements_association",
            "first_appearance",
            "wikipedia_urls"};

    private static AppConfig instance;
    private static AppConfigRow APP_CONFIG_ROW;
    private final Cache cacheUrls;
    private String tmpDir;


    private AppConfig() {
        AppErrorHandler.configureLogging();
        this.cacheUrls = new Cache(new File("CacheResponse.tmp"), 10 * 1024 * 1024);
        File file = new File("CacheImage.tmp");
        this.tmpDir = file.getName() + "/";
        try {
            if (!CreateTables.tableIsCreated("app_configs")) {
                new SetupMVC();
            } else {
                if (this.checkIntegrity()) {
                    APP_CONFIG_ROW = AppConfigsTable.getConfig();
                    if (APP_CONFIG_ROW != null) {
                        EventQueue.invokeLater(() -> {
                            UI ui = new UI();
                            ui.init();
                            AppErrorHandler.configureLogging();
                            Controller controller = new Controller(ui);
                            controller.init();
                        });
                    } else {
                        this.requireNewInstallation();
                    }
                } else {
                    this.requireNewInstallation();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "fatal error", "Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void newInstance() {
        instance = new AppConfig();
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private void requireNewInstallation() {
        int option = JOptionPane.showConfirmDialog(null,
                "Missed tables, do you want to install them ?", "Missing files",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            new SetupMVC();
        } else {
            System.exit(1);
        }
    }

    private boolean checkIntegrity() {
        for (String table : tables) {
            if (!CreateTables.tableIsCreated(table)) {
                return false;
            }
        }
        return true;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public Cache getCacheUrls() {
        return cacheUrls;
    }
}
