package settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Supports storing the application settings in a persistent way.
 * The {@link State} and {@link Storage} annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
        name = "settings.SetOnlineBranchLocalPath",
        storages = {@Storage("SdkSettingsPlugin.xml")}
)
/**
 * @FileName: settings.SetOnlineBranchLocalPath.java
 * @Description:
 * @Author: heyafei
 * @Date: 2021-09-29 13:02:06
 */
public class SetOnlineBranchLocalPath implements PersistentStateComponent<SetOnlineBranchLocalPath> {

    public Map<String,String> pathJson = new HashMap();

    public static SetOnlineBranchLocalPath getInstance() {


//       return ServiceManager.getService(SetOnlineBranchLocalPath.class);

        return ApplicationManager.getApplication().getService(SetOnlineBranchLocalPath.class);
    }

    @Nullable
    @Override
    public SetOnlineBranchLocalPath getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SetOnlineBranchLocalPath state) {
        XmlSerializerUtil.copyBean(state, this);
    }





}
