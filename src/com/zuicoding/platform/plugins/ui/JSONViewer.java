package com.zuicoding.platform.plugins.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.zuicoding.platform.plugins.jsonformater.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/12/7
 * <p>
 * <p></p>
 */
public class JSONViewer extends DialogWrapper{
    private JPanel container;
    private JTextArea jsonText;
    public JSONViewer() {
        super(false);
        init();
        setTitle("JSON 格式化");
        setOKButtonText("格式化");

    }

    public JPanel getContainer() {
        return container;
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        Action[] actions = super.createActions();
        actions[0] = new DialogWrapperAction("格式化") {

            @Override
            protected void doAction(ActionEvent actionEvent) {
                String text = jsonText.getText();
                if (text == null || text.trim().isEmpty()) return;
                System.err.println(text);
                jsonText.setText(JsonUtils.formatJson(text));
            }
        };

        return actions;
    }




    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return container;
    }


}
