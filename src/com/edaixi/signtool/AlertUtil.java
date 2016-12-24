package com.edaixi.signtool;

import javafx.scene.control.Alert;

/**
 * Created by wei_spring on 2016/12/23.
 * <p>
 * <a href="http://code.makery.ch/blog/javafx-dialogs-official/"> from </a>
 * <p>
 * Alert工具类
 */
public class AlertUtil {

    private static Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private AlertUtil() {
    }

    public static void showAlert(String title, String header, String content) {

        alert.setTitle(title);
        alert.setHeaderText(header.isEmpty() ? null : header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void hideAlert() {
        if (alert != null) {
            alert.hide();
        }
    }
}
