package com.wei_spring.signtool;

import com.wei_spring.signtool.bean.ApkSignInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {

    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressBar progressBarS;
    @FXML
    private ListView listLog;
    @FXML
    private ListView listLogS;
    @FXML
    private TextField apkFileDir;
    @FXML
    private TextField apkFileDirS;
    @FXML
    private TextField channelFileDir;
    @FXML
    private TextField keystoreDir;
    @FXML
    private TextField keystoreDirS;
    @FXML
    private TextField aliasName;
    @FXML
    private TextField aliasNameS;
    @FXML
    private PasswordField keystorePWD;
    @FXML
    private PasswordField keystorePWDS;
    @FXML
    private ChoiceBox channelChoiceBox;

    public static final ObservableList showListLog =
            FXCollections.observableArrayList();

    public static final ObservableList showListLogS =
            FXCollections.observableArrayList();

    final FileChooser fileChooser = new FileChooser();

    /**
     * 设置默认的多渠道签名信息
     */
    public void setSignInfoText(ApkSignInfo apkSignInfo) {
        if (apkSignInfo != null && apkFileDir != null) {
            apkFileDir.setText(apkSignInfo.getApkPath());
            channelFileDir.setText(apkSignInfo.getChannelPath());
            keystoreDir.setText(apkSignInfo.getKeystorePath());
            aliasName.setText(apkSignInfo.getAliasString());
            keystorePWD.setText(apkSignInfo.getKeystorePwd());
        }
    }

    /**
     * 设置默认的二次签名信息
     */
    public void setSecondSignInfoText(ApkSignInfo apkSignInfo) {
        if (apkSignInfo != null && apkFileDir != null) {
            apkFileDir.setText(apkSignInfo.getApkPath());
            keystoreDir.setText(apkSignInfo.getKeystorePath());
            aliasName.setText(apkSignInfo.getAliasString());
            keystorePWD.setText(apkSignInfo.getKeystorePwd());
        }
    }

    @FXML
    protected void handleFaqButtonAction(ActionEvent event) {
        AlertUtil.showAlert("提示", "关于签名使用的apksigner.jar说明", "apksigner.jar使用的是\n" +
                "Android/sdk/build-tools/29.0.0\n下面的，如果和签名APK对应版本不匹配，可以clone项目，替换apksigner.jar，" +
                "再执行签名，当然，不替换也能签名成功");
    }

    @FXML
    protected void selectApkFilePath(ActionEvent event) {
        fileChooser.getExtensionFilters().removeAll();
        configureFileChooser(fileChooser, "选择Apk目录", "apkPath", "*.apk");
        File file = fileChooser.showOpenDialog(Main.getInstance().singleStage);
        if (file != null && file.getPath().endsWith(".apk")) {
            apkFileDir.setText(file.getPath());
        } else {
            AlertUtil.showAlert("温馨提示", "", "选择出错啦,请手动输入Apk的目录.");
        }
    }

    @FXML
    protected void selectApkFilePathS(ActionEvent event) {
        fileChooser.getExtensionFilters().removeAll();
        configureFileChooser(fileChooser, "选择Apk目录", "apkPath", "*.apk");
        File file = fileChooser.showOpenDialog(Main.getInstance().singleStage);
        if (file != null && file.getPath().endsWith(".apk")) {
            apkFileDirS.setText(file.getPath());
        } else {
            AlertUtil.showAlert("温馨提示", "", "选择出错啦,请手动输入Apk的目录.");
        }
    }

    @FXML
    protected void selectChannelFilePath(ActionEvent event) {
        fileChooser.getExtensionFilters().retainAll();
        configureFileChooser(fileChooser, "选择channel目录", "channelPath", "*.txt");
        File file = fileChooser.showOpenDialog(Main.getInstance().singleStage);
        if (file != null && file.getPath().endsWith(".txt")) {
            channelFileDir.setText(file.getPath());
        } else {
            AlertUtil.showAlert("温馨提示", "", "选择出错啦,请手动输入channel的目录.");
        }
    }

    @FXML
    protected void selectKeyStoreFilePath(ActionEvent event) {
        fileChooser.getExtensionFilters().removeAll();
        configureFileChooser(fileChooser, "选择Keystore目录", "keystorePath", "*");
        File file = fileChooser.showOpenDialog(Main.getInstance().singleStage);
        if (file != null) {
            keystoreDir.setText(file.getPath());
        } else {
            AlertUtil.showAlert("温馨提示", "", "选择出错啦,请手动输入keystore的目录.");
        }
    }

    @FXML
    protected void selectKeyStoreFilePathS(ActionEvent event) {
        fileChooser.getExtensionFilters().removeAll();
        configureFileChooser(fileChooser, "选择Keystore目录", "keystorePath", "*");
        File file = fileChooser.showOpenDialog(Main.getInstance().singleStage);
        if (file != null) {
            keystoreDirS.setText(file.getPath());
        } else {
            AlertUtil.showAlert("温馨提示", "", "选择出错啦,请手动输入keystore的目录.");
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser, String title
            , String descStr, String filterStr) {
        fileChooser.setTitle(title);
//        fileChooser.setInitialDirectory(
//                new File(System.getProperty("user.home"))
//        );
//        if (filterStr != null && !filterStr.isEmpty()) {
//            fileChooser.getExtensionFilters().add(
//                    new FileChooser.ExtensionFilter(descStr, filterStr)
//            );
//        }
    }

    @FXML
    protected void handleSignButtonAction(ActionEvent event) {
        String apkFileDirString = apkFileDir.getText();
        String channelFileDirString = channelFileDir.getText();
        String keystoreDirString = keystoreDir.getText();
        String aliasNameString = aliasName.getText();
        String keystorePWDString = keystorePWD.getText();
        if (apkFileDirString == null || apkFileDirString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "APK安装包路径不能为空.");
            return;
        } else if (channelFileDirString == null || channelFileDirString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "渠道文件路径不能为空.");
            return;
        } else if (keystoreDirString == null || keystoreDirString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "keyStore文件路径不能为空.");
            return;
        } else if (aliasNameString == null || aliasNameString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "Alias不能为空.");
            return;
        } else if (keystorePWDString == null || keystorePWDString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "keyStore密码不能为空.");
            return;
        }
        ApkSignInfo apkSignInfo = new ApkSignInfo();
        try {
            apkSignInfo.setApkPath(apkFileDirString);
            apkSignInfo.setChannelPath(channelFileDirString);
            apkSignInfo.setKeystorePath(keystoreDirString);
            apkSignInfo.setAliasString(aliasNameString);
            apkSignInfo.setKeystorePwd(keystorePWDString);
            Main.getInstance().saveApkSignInfo(apkSignInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (showListLog.size() > 1) showListLog.removeAll();
        showListLog.add("开始执行打包签名......开始执行打包签名\n");
        setTips(showListLog);
        channelApkSign(apkSignInfo);
    }

    @FXML
    protected void handleSecondSignButtonAction(ActionEvent event) {
        String apkFileDirString = apkFileDirS.getText();
        String keystoreDirString = keystoreDirS.getText();
        String aliasNameString = aliasNameS.getText();
        String keystorePWDString = keystorePWDS.getText();

        if (apkFileDirString == null || apkFileDirString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "APK安装包路径不能为空.");
            return;
        } else if (keystoreDirString == null || keystoreDirString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "keyStore文件路径不能为空.");
            return;
        } else if (aliasNameString == null || aliasNameString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "Alias不能为空.");
            return;
        } else if (keystorePWDString == null || keystorePWDString.length() < 2) {
            AlertUtil.showAlert("⚠️", "", "keyStore密码不能为空.");
            return;
        }
        ApkSignInfo apkSignInfo = new ApkSignInfo();
        try {
            apkSignInfo.setApkPath(apkFileDirString);
            apkSignInfo.setKeystorePath(keystoreDirString);
            apkSignInfo.setAliasString(aliasNameString);
            apkSignInfo.setKeystorePwd(keystorePWDString);
            Main.getInstance().saveApkSignInfo(apkSignInfo);
            System.err.println("Sign apkSignInfo:" + apkSignInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (showListLogS.size() > 1) showListLog.removeAll();
        showListLogS.add("开始💪\n");
        setTipsS(showListLogS);
        apkSign(apkSignInfo);
    }

    /**
     * 重新签名
     */
    public void apkSign(ApkSignInfo apkSignInfo) {
        try {
            String[] lists = (System.getProperty("os.name").contains("Windows")) ? apkSignInfo.getApkPath().split(File.separator + File.separator) : apkSignInfo.getApkPath().split(File.separator);
            String apkName = lists[lists.length - 1];
            showListLogS.add("执行二次签名: " + apkName.replace(".apk", "") + ".apk\n");
            signApk(apkSignInfo.getKeystorePath(), apkSignInfo.getAliasString(), apkSignInfo.getKeystorePwd(), apkSignInfo.getApkPath());
            showListLogS.add("执行结束: " + apkName.replace(".apk", "") + ".apk 签名完毕.\n");
            showListLogS.add("结束💪\n");
            setTipsS(showListLogS);
            progressBarS.setProgress(100);
            // color the bar green when the work is complete.
            progressBarS.progressProperty().addListener(observable -> {
                if (progressBarS.getProgress() >= 1 - 0.0000005) {
                    progressBarS.setStyle("-fx-accent: forestgreen;");

                }
            });
            //展示打包存储位置
            String path = new File(apkSignInfo.getApkPath()).getParent();
            showOpenDialog(path);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("错误提示", "", "执行出错啦,请检查输入是否正确,确认输入无误后再重试.");
            return;
        }
    }

    /**
     * ¬
     * 设置小提示
     */
    public void setTips(ObservableList showListLog) {
        if (showListLog != null && showListLog.size() > 1) {
            listLog.setItems(showListLog);
        }
    }

    /**
     * 设置小提示
     */
    public void setTipsS(ObservableList showListLog) {
        if (showListLog != null && showListLog.size() > 1) {
            listLogS.setItems(showListLog);
        }
    }

    /**
     * 生成渠道包,并且重新签名
     */
    public void channelApkSign(ApkSignInfo apkSignInfo) {
        try {
            File apkFile = new File(apkSignInfo.getApkPath());
            String[] lists = (System.getProperty("os.name").contains("Windows")) ? apkSignInfo.getApkPath().split(File.separator + File.separator) : apkSignInfo.getApkPath().split(File.separator);
            String apkName = lists[lists.length - 1];
            List<String> channelList = readFileByLines(apkSignInfo);
            String outputPath = makeOutputFile(apkSignInfo.getApkPath());
            if (outputPath != null && channelList != null) {
                Logger.getLogger(Controller.class.getName()).log(Level.INFO, "channelList:" + channelList.size(), "");
                final Task<Void> task = new Task<Void>() {
                    final int N_ITERATIONS = channelList.size();

                    @Override
                    protected Void call() throws Exception {
                        for (int i = 0; i < N_ITERATIONS; i++) {
                            String channerStr = channelList.get(i);
                            Logger.getLogger(Controller.class.getName()).log(Level.INFO, "channerStr:" + channerStr, "");
                            File channelApkFile = new File(outputPath + File.separator + apkName.replace(".apk", "") + "_" + channerStr + ".apk");
                            try {
                                Files.copy(apkFile.toPath(), channelApkFile.toPath());
                                addChannelFile(channelApkFile.getPath(), channerStr, apkSignInfo.getEmptyFileString());
                            } catch (IOException e) {
                            }
                            //执行重新签名逻辑
                            signApk(apkSignInfo.getKeystorePath(), apkSignInfo.getAliasString(), apkSignInfo.getKeystorePwd(), channelApkFile.getAbsolutePath());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    //javaFX operations should go here
                                    showListLog.add("渠道包: " + apkName.replace(".apk", "") + "_" + channerStr + ".apk 打包签名完毕.\n");
                                }
                            });
                            setTips(showListLog);
                            updateProgress(i + 1, N_ITERATIONS);
                            if (i == N_ITERATIONS - 1) {
                                showListLog.add("结束执行打包签名......结束执行打包签名\n");
                                SimpleDateFormat df = new SimpleDateFormat("当前时间: yyyy年MM月dd日 HH:mm:ss");
                                showListLog.add(df.format(new Date()));
                                setTips(showListLog);
                            }
                        }
                        return null;
                    }
                };
                progressBar.progressProperty().bind(
                        task.progressProperty()
                );
                // color the bar green when the work is complete.
                progressBar.progressProperty().addListener(observable -> {
                    if (progressBar.getProgress() >= 1 - 0.0000005) {
                        progressBar.setStyle("-fx-accent: forestgreen;");
                        //展示打包存储位置
                        showOpenDialog(outputPath);
                    }
                });
                final Thread thread = new Thread(task, "task-thread");
                thread.setDaemon(true);
                thread.start();
            } else {
                Logger.getLogger(Controller.class.getName()).log(Level.INFO, "outputPath == null", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("错误提示", "", "执行出错啦,请检查输入是否正确,确认输入无误后再联系开发解决.");
            return;
        }
    }


    /**
     * 弹出打包完成对话框
     */
    public void showOpenDialog(String apkPath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("打包签名完毕");
        alert.setHeaderText(null);
        alert.setContentText("点击下面按钮,打开生成渠道包存放文件夹,或者点击关闭.");

        ButtonType buttonTypeOne = new ButtonType("打开打包存放文件夹");
        ButtonType buttonTypeCancel = new ButtonType("关闭", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            String shpath;
            if (System.getProperty("os.name").contains("Windows")) {
                shpath = "start " + apkPath;
            } else {
                shpath = "open " + apkPath;
            }
            try {
                Process ps = Runtime.getRuntime().exec(shpath);
                ps.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alert.hide();
        }
    }

    /**
     * 加入渠道信息
     *
     * @param channelList
     */
    public void packageApk(List<String> channelList) {

    }

    /**
     * 签名apk
     *
     * @param keyStorePath 密钥路径
     * @param aliasStr
     * @param keyPWD
     * @param apkPatch     apk路径
     */
    public void signApk(String keyStorePath, String aliasStr, String keyPWD, String apkPatch) {
        try {
            System.out.println("sign come in....");
            //String shpath="./apksigner sign --ks edaixisign --ks-key-alias edaixi --ks-pass pass:edaixi.com --v1-signing-enabled true --v2-signing-enabled true app-debug.apk";
            String shpath;
            if (System.getProperty("os.name").contains("Windows")) {
                shpath = "apksigner_windows.bat sign --ks " + keyStorePath + " --ks-key-alias " + aliasStr +
                        " --ks-pass pass:" + keyPWD + " --v1-signing-enabled true --v2-signing-enabled true " + apkPatch;
            } else {
                shpath = "./apksigner sign --ks " + keyStorePath + " --ks-key-alias " + aliasStr +
                        " --ks-pass pass:" + keyPWD + " --v1-signing-enabled true --v2-signing-enabled true " + apkPatch;

            }
            Process ps = Runtime.getRuntime().exec(shpath);
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println("result" + result);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("错误提示", "", "\nerror:" + "出错啦,请截图联系开发:\n" + e.toString());
        }
    }


    /**
     * apk添加渠道标识
     *
     * @param zipFilePath
     * @param channel
     */
    public static void addChannelFile(String zipFilePath,
                                      String channel, String preChannel) {
        try {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            Path path = Paths.get(zipFilePath);
            URI uri = URI.create("jar:" + path.toUri());
            try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
                Path nf = fs.getPath("META-INF/" + preChannel + channel);
                try (Writer writer = Files.newBufferedWriter(nf, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                    writer.write("add_channel" + channel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据输入apk位置,生成多渠道签名输出的目录
     */
    public String makeOutputFile(String apkFileDir) {
        File file = new File(apkFileDir);
        String parentPath = file.getAbsoluteFile().getParent();
        File rootFile = new File(parentPath + File.separator + "多渠道打包签名文件");
        removeDirectory(rootFile);
        if (!rootFile.exists()) {
            if (rootFile.mkdirs()) {
                System.out.println("多渠道打包签名文件创建成功.");
                System.out.println(parentPath + File.separator + "多渠道打包签名文件");
                return parentPath + File.separator + "多渠道打包签名文件";
            } else {
                System.out.println("多渠道打包签名文件创建失败.");
                return null;
            }
        } else {
            rootFile.delete();
        }
        return parentPath + File.separator + "多渠道打包签名文件";
    }

    public static void removeDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    removeDirectory(aFile);
                }
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }

    /**
     * 读取渠道文件内容,按行展示
     */
    private List<String> readFileByLines(ApkSignInfo apkSignInfo) {
        List<String> channelList = new ArrayList<>();
        if (channelChoiceBox.getValue().equals("单个渠道")) {
            channelList.add(channelFileDir.getText());
            return channelList;
        }
        String fileName = apkSignInfo.getChannelPath();
        if (fileName.contains("/") || fileName.contains(File.separator) || fileName.contains("\\")) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(fileName));
                String tempString = null;
                int line = 1;
                //一次读一行，读入null时文件结束
                while ((tempString = reader.readLine()) != null) {
                    //把当前行号显示出来
                    //System.out.println("渠道文件 " + line + ": " + tempString);
                    line++;
                    channelList.add(tempString.toString());
                }
                reader.close();
                return channelList;
            } catch (IOException e) {
                e.printStackTrace();
                return channelList;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
                return channelList;
            }
        } else if (fileName.contains(",") || fileName.contains(",")) {
            String[] listStr = fileName.split(",");
            for (String listItem : listStr) {
                channelList.add(listItem);
            }
            return channelList;
        } else {
            channelList.add(fileName);
            return channelList;
        }
    }
}
