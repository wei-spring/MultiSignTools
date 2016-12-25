package com.edaixi.signtool;

import com.edaixi.signtool.bean.ApkSignInfo;
import com.edaixi.signtool.bean.MultiSignWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static Main instance;
    public Stage singleStage;
    private Controller controller;
    private MultiSignWrapper loadMultiSignWrapper;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            singleStage = primaryStage;
            primaryStage.setTitle("e袋洗多渠道打包签名工具");
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void gotoHome() {
        try {
            replaceSceneContent("/resources/multi_sign_tool_home.fxml");
            loadMultiSignWrapper = loadPersonDataFromFile(new File("multi_sign_info.xml"));
            if (loadMultiSignWrapper != null && controller != null) {
                ApkSignInfo apkSignInfo = new ApkSignInfo();
                apkSignInfo.setApkPath(loadMultiSignWrapper.getApkPath());
                apkSignInfo.setKeystorePath(loadMultiSignWrapper.getkeystorePath());
                apkSignInfo.setAliasString(loadMultiSignWrapper.getaliasString());
                apkSignInfo.setEmpmtyFileString(loadMultiSignWrapper.getEmpmtyFileString());
                apkSignInfo.setKeystorePwd(loadMultiSignWrapper.getkeystorePwd());
                apkSignInfo.setChannelPath(loadMultiSignWrapper.getChannelPath());
                controller.setSignInfoText(apkSignInfo
                );
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoLogin() {
        try {
            replaceSceneContent("/resources/multi_sign_tool_login.fxml");
            loadMultiSignWrapper = loadPersonDataFromFile(new File("multi_sign_info.xml"));
            if (loadMultiSignWrapper != null && controller != null) {
                controller.setUserInfoText(loadMultiSignWrapper.getUserName(), loadMultiSignWrapper.getUserPwd());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent page = fxmlLoader.load();
        controller = fxmlLoader.getController();
        //Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = singleStage.getScene();
        if (scene == null) {
            scene = new Scene(page, 650, 500);
            //scene.getStylesheets().add(getClass().getResource("demo.css").toExternalForm());
            singleStage.setScene(scene);
        } else {
            singleStage.getScene().setRoot(page);
        }
        singleStage.sizeToScene();
        return page;
    }

    /**
     * 判断用户名是否合法
     *
     * @param userId
     * @param password
     * @return
     */
    public boolean userVerify(String userId, String password) {
        if (userId.contains(".com")) {
            loadMultiSignWrapper.setUserName(userId);
            loadMultiSignWrapper.setUserPwd(password);
            savePersonDataToFile(new File("multi_sign_info.xml"));
            gotoHome();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 本地序列化签名信息
     *
     * @param apkSignInfo
     */
    public void saveApkSignInfo(ApkSignInfo apkSignInfo) {
        loadMultiSignWrapper.setApkPath(apkSignInfo.getApkPath());
        loadMultiSignWrapper.setChannelPath(apkSignInfo.getChannelPath());
        loadMultiSignWrapper.setkeystorePath(apkSignInfo.getKeystorePath());
        loadMultiSignWrapper.setaliasString(apkSignInfo.getAliasString());
        loadMultiSignWrapper.setkeystorePwd(apkSignInfo.getKeystorePwd());
        loadMultiSignWrapper.setEmpmtyFileString(apkSignInfo.getEmpmtyFileString());
        savePersonDataToFile(new File("multi_sign_info.xml"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public MultiSignWrapper loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MultiSignWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            MultiSignWrapper wrapper = (MultiSignWrapper) um.unmarshal(file);
            return wrapper == null ? new MultiSignWrapper() : wrapper;
            // Save the file path to the registry.
            //setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            AlertUtil.showAlert("出错啦", "", "Could not load data from file:\n" + e.getMessage() + "\n" + file.getPath());
            return null;
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MultiSignWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            // Marshalling and saving XML to the file.

            if (loadMultiSignWrapper != null) m.marshal(loadMultiSignWrapper, file);

            // Save the file path to the registry.
            //setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            AlertUtil.showAlert("出错啦", "", "Could not load data from file:\n" + file.getPath());
        }
    }
}
