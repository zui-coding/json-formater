package com.zuicoding.platform.plugins.jsonformater;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.zuicoding.platform.plugins.ui.JSONViewer;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/12/4
 * <p>
 * <p></p>
 */
public class JSONFormater extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        DialogWrapper dialog = new JSONViewer();
        dialog.show();
    }
}
