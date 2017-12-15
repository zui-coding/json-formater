package com.zuicoding.platform.plugins.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.zuicoding.platform.plugins.jsonformater.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2017/12/7
 * <p>
 * <p></p>
 */
public class JSONViewer extends DialogWrapper{
    private JPanel container;
    private JTextField queryField;
    private JEditorPane jsonArea;
    private JButton queryBtn;

    public JSONViewer() {
        super(false);
        init();
        setTitle("JSON 格式化");
        setOKButtonText("格式化");
        HTMLEditorKit kit = new HTMLEditorKit();
        jsonArea.setEditorKit(kit);
        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jsonArea.getText();
                if (JsonUtils.isBank(text)) return;
                Document doc = Jsoup.parse(text);
                Element element = doc.body().selectFirst("p");
                //已经格式化好了
                if (element == null){
                    element = doc.body();
                    element.html("<p>" + element.html() + "</p>");
                    element = element.selectFirst("p");
                }

                text = element.html();

                text = wrapStyle(text);

                element.html(text);

                setJsonArea(element.toString());
            }
        });
    }

    public JPanel getContainer() {
        return container;
    }



    private String wrapStyle(String html){
        if (html == null || html.trim().isEmpty())return html;
        String value = queryField.getText();
        if (value == null || value.trim().isEmpty()) return html;
        Document doc = Jsoup.parse(html);
        doc.select("font[name=\"keyWrapper\"]").unwrap();
        html = doc.body().html().replaceAll(value,"<font name=\"keyWrapper\" style=\"background-color:yellow;\">" + value + "</font>");

        return html;
    }

    private void setJsonArea(String html){
        jsonArea.setText(html);
    }
    @NotNull
    @Override
    protected Action[] createActions() {
        Action[] actions = super.createActions();
        actions[0] = new DialogWrapperAction("格式化") {

            @Override
            protected void doAction(ActionEvent actionEvent) {
                String text = jsonArea.getText();
                if (text == null || text.trim().isEmpty()) return;
                System.err.println(text);

                try {
                    Document doc = Jsoup.parse(text);

                    Element element = doc.body().selectFirst("p");
                    if (element == null) {
                        element = doc.body();
                        element.html("<p>" + element.html() + "</p>");
                        element = element.selectFirst("p");
                    }
                    text = element.html();
                    System.err.println(text);
                    text = text.replaceAll("(&nbsp;|<br/>|\0|<br>)","");
                    text = JsonUtils.formatJson(text);
                    text = wrapStyle(text);
                    element.html(text);
                    setJsonArea(element.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }

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
