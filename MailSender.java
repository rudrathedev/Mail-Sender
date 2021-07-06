package com.kab.MailSender;

import android.app.Activity;
import android.content.Context;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import javax.mail.Authenticator;
import javax.mail.*;
import javax.mail.util.*;
import javax.mail.event.*;
import javax.mail.search.*;
import javax.mail.internet.*;
import com.sun.activation.registries.*;
import javax.activation.*;
import com.sun.mail.handlers.*;
import com.sun.mail.dsn.*;
import com.sun.mail.iap.*;
import com.sun.mail.util.*;
import com.sun.mail.pop3.*;
import com.sun.mail.smtp.*;
import com.sun.mail.imap.*;
import myjava.awt.datatransfer.*;
import org.apache.harmony.misc.*;
import org.apache.harmony.awt.*;
import org.apache.harmony.awt.internal.nls.*;
import org.apache.harmony.awt.datatransfer.*;

@DesignerComponent(
        version = 1,
        description = "",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "")

@SimpleObject(external = true)
//Permissions
@UsesPermissions(permissionNames = "android.permission.ACCESS_NETWORK_STATE, android.permission.INTERNET, android.permission.READ_EXTERNAL_STORAGE")
@UsesLibraries(libraries = "additionnal.jar, activation.jar, mail.jar")
public class MailSender extends AndroidNonvisibleComponent {

    private Context context;
    private Activity activity;

    public MailSender(ComponentContainer container){
        super(container.$form());
        this.activity = container.$context();
        this.context = container.$context();
    }

    @SimpleFunction()
    public void SendMail(String senderEmail, String senderEmailPassword, String recieverMail, String mailCc, String mailBcc, String mailSubject, String mailBody){
            BackgroundMail.newBuilder(context)
                .withUsername(senderEmail)
                .withPassword(senderEmailPassword)
                .withMailTo(recieverMail)
                .withMailCc(mailCc)
                .withMailBcc(mailBcc)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(mailSubject)
                .withBody(mailBody)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                       EmailSendSuccessful();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                       EmailSendFailed();
                    }
                })
                .send();
    }

    @SimpleEvent
    public void EmailSendSuccessful(){
        EventDispatcher.dispatchEvent(this, "EmailSendSuccessful");
    }
    @SimpleEvent
    public void EmailSendFailed(){
        EventDispatcher.dispatchEvent(this, "EmailSendFailed");
    }
    @SimpleProperty
    public Object PlainType() {
        return "BackgroundMail.TYPE_PLAIN";
    }
    @SimpleProperty
    public Object HtmlType() {
        return "BackgroundMail.TYPE_HTML";
    }
}
