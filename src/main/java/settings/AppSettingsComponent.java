package settings;

import com.intellij.ui.EditorTextField;
import com.intellij.ui.ExpandableEditorSupport;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @FileName: AppSettingsComponent.java
 * @Description:
 * @Author: heyafei
 * @Date: 2021-09-29 13:07:06
 */
public class AppSettingsComponent {
    private final JPanel myMainPanel;

    private final EditorTextField pathJson = new EditorTextField();


    public AppSettingsComponent() {
        JComponent label = new JBLabel("PathMap: ");
        new ExpandableEditorSupport(pathJson);
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(label, pathJson, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return pathJson;
    }

    public Map getPathJson() {
        return this.getStringToMap(pathJson.getText());
    }

    public void setPathJson(Map<String, String> map) {
        pathJson.setText(this.getMapToString(map));
    }

    private static Map<String, Object> getStringToMap(String str) {
        String[] str1 = str.split(",");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < str1.length; i++) {
            String[] str2 = str1[i].split("=");
            if (str2.length == 2) {
                map.put(str2[0].trim(), str2[1]);
            } else {
                map.put(str2[0].trim(), "");
            }
        }
        return map;
    }

    private static String getMapToString(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append("=").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if (i != keyArray.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
