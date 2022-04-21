import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestFactory;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.ui.UIUtil;
import settings.SetOnlineBranchLocalPath;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CompareWithOnline extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //获取当前在操作的工程上下文
        Project project = e.getData(PlatformDataKeys.PROJECT);
        //获取当前操作的类文件
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        //获取当前类文件的路径
        String classPath = psiFile.getVirtualFile().getPath();

        SetOnlineBranchLocalPath settings = SetOnlineBranchLocalPath.getInstance();



        Map<String, String> pathJson = settings.pathJson;
        String onLinePath = getOnlinePath(classPath, pathJson);
        if (null == onLinePath || "".equals(onLinePath)) {
            Messages.showInfoMessage("没有配置路径", "没有配置路径");
        } else {
            showDiff(classPath, onLinePath, project);
        }

    }


    private void showDiff(String selfFilePath, String onlineBranchLocalRootPath, Project project) {
        VirtualFile virtualFil1 = getVirtualFile(selfFilePath);
        VirtualFile virtualFil2 = getVirtualFile(onlineBranchLocalRootPath);
        UIUtil.invokeLaterIfNeeded(() -> {
            DiffRequest request = DiffRequestFactory.getInstance().createFromFiles(project, virtualFil1, virtualFil2);
            DiffManager.getInstance().showDiff(project, request);
        });
    }

    private VirtualFile getVirtualFile(String filePath) {
        File file2 = new File(filePath);
        if (file2.exists()) {
            VirtualFile virtualFil2 = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file2);
            return virtualFil2;
        } else {
            Messages.showInfoMessage(filePath + "文件不存在", "文件不存在");
            return null;
        }
    }

    private String getOnlinePath(String classPath, Map<String, String> pathJson) {

        String onlinePath = "";
        Set keySet = pathJson.keySet();
        Iterator<String> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            if (classPath.contains(name)) {
                String devPath = pathJson.get(name);
                onlinePath = classPath.replaceAll(name, devPath);
                break;
            }
        }
        return onlinePath;
    }


}
