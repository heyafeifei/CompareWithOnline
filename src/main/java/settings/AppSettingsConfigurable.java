package settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @FileName: AppSettingsConfigurable.java
 * @Description:
 * @Author: heyafei
 * @Date: 2021-09-29 13:08:18
 */
public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "SDK: Application Settings Example";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        SetOnlineBranchLocalPath settings = SetOnlineBranchLocalPath.getInstance();
        boolean modified = !mySettingsComponent.getPathJson().equals(settings.pathJson);
        return modified;
    }

    @Override
    public void apply() {
        SetOnlineBranchLocalPath settings = SetOnlineBranchLocalPath.getInstance();

        settings.pathJson = mySettingsComponent.getPathJson();
    }

    @Override
    public void reset() {
        SetOnlineBranchLocalPath settings = SetOnlineBranchLocalPath.getInstance();

        mySettingsComponent.setPathJson(settings.pathJson);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
