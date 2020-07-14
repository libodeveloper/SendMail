package com.example.sendmail;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.sendmail.uitl.CrashHandler;
import com.example.sendmail.uitl.FileUtil;
import com.example.sendmail.uitl.PermissionHelper;
import com.example.sendmail.uitl.mail.Mail;
import com.example.sendmail.uitl.mail.MailSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionHelper.getInstance().requestPermission(new PermissionHelper.RequestResultListener() {
            @Override
            public void onResult(boolean granted) {
                if (granted) {

                } else {
                    Toast.makeText(MainActivity.this, "此功能需要开启SD卡读写授权!", Toast.LENGTH_SHORT).show();
                }
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        final Mail mail = createMail(new String[]{"libodeveloper@163.com"},new String[]{"qqq1987@126.com"}, null);
        final MailSender sender = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> fileList = new ArrayList<>();
                String logDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"JzgLog.zip";
                File file = new File(logDir);
                fileList.add(file);

                Log.e("mail","发送邮件了");

                sender.sendFileMail(mail, fileList);
            }
        }).start();

    }

    // Private Methods


    //要使用邮箱功能只需要更改以下四个值就行了。
    //先去发送邮箱里开通POP3/SMTP服务 获取邮箱的授权码（像这种第三方登录就用授权码而不是密码了）
    //然后查找到smtp服务的 host 服务地址 以及它对应的 端口号
    //最后libs包里的jar包直接用到自己项目里
//    private static final String HOST = "smtp.126.com"; //126的host SMTP服务器: smtp.126.com
    private static final String HOST = "smtp.qq.com"; //qq邮箱的host SMTP服务器地址
    private static final String PORT = "25"; //服务器对应的端口号
    private static final String FROM_ADD = "649119786@qq.com"; //发送者邮箱
    private static final String FROM_PSW = "faiqvynpluzzbfhd"; //发送者邮箱对应开通的授权码

    private static Mail createMail(String[] toAdds, String[] ccAdds, String bccAdds[]) {
        final Mail mail = new Mail();
        mail.setDebug(true);
        mail.setMailServerHost(HOST);
        mail.setMailServerPort(PORT);
        mail.setValidate(true);
        String[] split = FROM_ADD.split("@");

        mail.setUserName(split[0]); // 你的邮箱地址
        mail.setPassword(FROM_PSW);// 您的邮箱密码
        mail.setFromAddress(FROM_ADD); // 发送的邮箱
        mail.setToAddress(toAdds); // 发到哪个邮件去
        mail.setCcAddress(ccAdds);// 抄送邮件
        mail.setBccAddress(bccAdds);// 秘密抄送邮件
        mail.setSubject("崩溃日志"); // 邮件主题
        mail.setContent("崩溃日志，详见附件"); // 邮件文本
        return mail;
    }

    // Private Methods  __END__


}
