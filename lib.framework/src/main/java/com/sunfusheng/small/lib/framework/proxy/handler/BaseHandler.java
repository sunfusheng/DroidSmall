package com.sunfusheng.small.lib.framework.proxy.handler;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sunfusheng.small.lib.framework.proxy.MessageArg;
import com.sunfusheng.small.lib.framework.util.ToastTip;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseHandler<Re> extends Handler implements IContext {

    protected WeakReference<Re> mReference;
    private ProgressDialog mDialog;

    public BaseHandler(Re t) {
        mReference = new WeakReference<Re>(t);
        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("正在加载...");
    }

    @Override
    public void handleMessage(Message msg) {
        Re t = checkAvailability();
        if (t != null) {
            switch (msg.arg1) {
                case MessageArg.ARG1.TOAST_MESSAGE:
                    if (msg.obj instanceof String) {
                        ToastTip.show(msg.obj + "");
                    } else if (msg.obj instanceof Integer) {
                        ToastTip.show(getContext().getString((Integer) msg.obj) + "");
                    } else {
                        ToastTip.show(msg.obj + "");
                    }
                    break;
                case MessageArg.ARG1.CALL_BACK_METHOD:
                    invokeMethod(t, msg);
                    break;
                case MessageArg.ARG1.PROGRESSDIALOG_MESSAGE:
                    switch (msg.arg2) {
                        case 1:
                            mDialog.setCanceledOnTouchOutside(false);
                            mDialog.setMessage((CharSequence) msg.obj);
                            mDialog.show();
                            break;
                        case 2:
                            mDialog.setCanceledOnTouchOutside(true);
                            mDialog.setMessage((CharSequence) msg.obj);
                            mDialog.show();
                            break;
                        case 3:
                            mDialog.dismiss();
                            break;
                    }
                default:
                    break;
            }
        }
    }

    private void invokeMethod(Re callClazz, Message msg) {
        try {
            if (!(msg.obj instanceof String)) {
                ToastTip.show("Method error:" + msg.obj);
                return;
            }
            Method method;
            try {
                method = callClazz.getClass().getMethod(msg.obj + "", Bundle.class);
            } catch (NoSuchMethodException e) {
                invokeNoArgMethod(callClazz, msg);
                return;
            }
            if (method != null) {
                method.setAccessible(true);
                method.invoke(callClazz, msg.getData());
            }
        } catch (NoSuchMethodException e) {
            ToastTip.show("NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            ToastTip.show("IllegalAccessException: " + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void invokeNoArgMethod(Re acitivty, Message msg) throws NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Method method = acitivty.getClass().getMethod(msg.obj + "");
        if (method == null) {
            throw new NoSuchMethodException(msg.obj + "");
        }
        method.setAccessible(true);
        method.invoke(acitivty);
    }

    public void onDestroy() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    protected abstract Re checkAvailability();

}
