package com.bonds0097.unbillable;

import android.content.Intent;
import android.util.Log;

import com.bonds0097.substratehelper.Logging;
import com.saurik.substrate.MS;

import org.json.JSONObject;

import java.lang.reflect.Method;

public class Main {
    static void initialize() {
        final String logTag = "unbillable";
        Log.d(logTag, "Extension loaded.");

        String classToHook = "com.king.core.billingutil.IabHelper";
        MS.hookClassLoad(classToHook, new MS.ClassLoadHook() {
            public void classLoaded(Class<?> hookedClass) {
                Logging.ClassLoaded(logTag, hookedClass);
                Logging.LogClassDeclaredMethods(logTag, hookedClass);
                Method[] methodList = hookedClass.getDeclaredMethods();
                    for (Method method : methodList) {
                        try {
                            final Method hookedMethod = method;
                            MS.hookMethod(hookedClass, hookedMethod, new MS.MethodAlteration() {
                                public Object invoked(Object _this, Object... args) throws Throwable {
                                    Logging.HookingIntoMethodWithArgs(logTag, hookedMethod.getName(), _this, args);
                                    String sku;
                                    Object inventory = null;
                                    if (hookedMethod.getName().equals("launchPurchaseFlow")) {
                                        sku = (String) args[1];
                                        return invoke(_this, args);
                                    } else  if (hookedMethod.getName().equals("handleActivityResult")){
                                        Intent intent = new Intent();
                                        intent.putExtra("RESPONSE_CODE", 0);
                                        intent.putExtra("INAPP_DATA_SIGNATURE", "mJZwm85q4jDclgYYO588XSkNmOVS6G2DgB2gJUkSI5a826BS6+VmnMUJ9Q9yByofHNAAD2Kf4Z63hsxig93J4I3JrePwWzqfqfkBO84MyQXVIYygZ2gBkvu13Evl8arY33Dmj3sy3M/yMJZJ7mXENYrl9liMTfCKNknZANzRTIsZu+/2CL7hXP7yOLD8Zp+G5X1sfrW8Vj0ZgeXVivMTjuoMkBtJZ3EI7AT0CQxA8cFacJ0lHOXANpTlH6AVwT1hM9r5w1bikyJ/XLx3QU43YYLY8tFqbxJwJ8u7oCo3tTe9Xhd0l/UmPE4KiyunJXpdwkSbhrC/xzTHbRGdt6xj4w==");
                                        JSONObject purchase = new JSONObject();
                                        purchase.put("orderId", "12999763169054705758.1316213752962757");
                                        purchase.put("packageName", "com.king.candycrushsaga");
                                        purchase.put("productId", "com.midasplayer.iap.candycrushsaga.booster.lollipop");
                                        purchase.put("purchaseTime", System.currentTimeMillis());
                                        purchase.put("purchaseState", 0);
                                        purchase.put("purchaseToken", "ikbmeocacailgjckdgkjefke.AO-J1OzrNfNUA4wotv1lJ5M1SCm2rMGKLsQSElGdNiN5v1yuobj2wy_CKdUP9TFWw_X8dG-kuHivrvNJSFEM7Tfou902bRht5h19NNMM5dUHSmu3EXw6-fGK2SX65xxVuYVmWp_OROUMWRRqQXKRMI6voCWCWjpWLx6vEhYFTWeS9Mo_nGJ9SSM");
                                        intent.putExtra("INAPP_PURCHASE_DATA", purchase.toString());
                                        return invoke(_this, args[0], -1, intent);

                                    } else if (hookedMethod.getName().equals("queryPurchases")) {
                                        inventory = args[0];
                                        return invoke(_this, args);
                                    } else if (hookedMethod.getName().equals("consume")) {
                                        if (inventory != null) {
                                            Method addPurchase = inventory.getClass().getMethod("addPurchase", args[0].getClass());
                                            addPurchase.invoke(inventory, args[0]);}
                                        return Void.TYPE;
                                    } else {
                                        return invoke(_this, args);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Log.e(logTag, "An error occurred: " + e.toString());
                        }
                    }
                }

        });

        classToHook = "com.king.core.billingutil.Security";
        MS.hookClassLoad(classToHook, new MS.ClassLoadHook() {
            public void classLoaded(Class<?> hookedClass) {
                Logging.ClassLoaded(logTag, hookedClass);
                Logging.LogClassDeclaredMethods(logTag, hookedClass);
                for (Method method : hookedClass.getDeclaredMethods()) {
                    if (method.getName().equals("verify") || method.getName().equals("verifyPurchase")) {
                        try {
                            final Method hookedMethod = method;
                            MS.hookMethod(hookedClass, hookedMethod, new MS.MethodAlteration() {
                                public Object invoked(Object _this, Object... args) throws Throwable {
                                    Logging.HookingIntoMethodWithArgs(logTag, hookedMethod.getName(), _this, args);
                                    return true;
                                }
                            });
                        } catch (Exception e) {
                            Log.e(logTag, "An error occurred: " +  e.toString());
                        }}
                }
            }
        });

//        classToHook = "com.android.vending.billing.IInAppBillingService";
//        MS.hookClassLoad(classToHook, new MS.ClassLoadHook() {
//            public void classLoaded(Class<?> hookedClass) {
//                Logging.ClassLoaded(logTag, hookedClass);
//                Logging.LogClassDeclaredMethods(logTag, hookedClass);
//                for (Method method : hookedClass.getDeclaredMethods()) {
//                    if (method.getName().equals("consumePurchase")) {
//                        try {
//                            final Method hookedMethod = method;
//                            MS.hookMethod(hookedClass, hookedMethod, new MS.MethodAlteration() {
//                                public Object invoked(Object _this, Object... args) throws Throwable {
//                                    Logging.HookingIntoMethodWithArgs(logTag, hookedMethod.getName(), _this, args);
//                                    return 0;
//                                }
//                            });
//                        } catch (Exception e) {
//                            Log.e(logTag, "An error occurred: " +  e.toString());
//                        }}
//                }
//            }
//        });

        classToHook = "com.king.core.billingutil.IabResult";
        MS.hookClassLoad(classToHook, new MS.ClassLoadHook() {
            public void classLoaded(Class<?> hookedClass) {
                Logging.ClassLoaded(logTag, hookedClass);
                Logging.LogClassDeclaredMethods(logTag, hookedClass);
                for (Method method : hookedClass.getDeclaredMethods()) {
                    if (method.getName().equals("isSuccess")) {
                        try {
                            final Method hookedMethod = method;
                            MS.hookMethod(hookedClass, hookedMethod, new MS.MethodAlteration() {
                                public Object invoked(Object _this, Object... args) throws Throwable {
                                    Logging.HookingIntoMethodWithArgs(logTag, hookedMethod.getName(), _this, args);
                                    return true;
                                }
                            });
                        } catch (Exception e) {
                            Log.e(logTag, "An error occurred: " +  e.toString());
                        }}
                }
            }
        });

//        classToHook = "com.king.store.billingutil.Inventory";
//        MS.hookClassLoad(classToHook, new MS.ClassLoadHook() {
//            public void classLoaded(Class<?> hookedClass) {
//                Logging.ClassLoaded(logTag, hookedClass);
//                Logging.LogClassDeclaredMethods(logTag, hookedClass);
//                Method addPurchase = null;
//                for (Method method : hookedClass.getDeclaredMethods()) {if (method.getName().equals("addPurchase")) {
//                    addPurchase = method;
//                }}
//                for (Method method : hookedClass.getDeclaredMethods()) {
//
//                    try {
//                        final Method addPurchaseFinal = addPurchase;
//                        final Method hookedMethod = method;
//                        MS.hookMethod(hookedClass, hookedMethod, new MS.MethodAlteration() {
//                            public Object invoked(Object _this, Object... args) throws Throwable {
//                                Logging.HookingIntoMethodWithArgs(logTag, hookedMethod.getName(), _this, args);
//
//                                if (hookedMethod.getName().equals("addSkuDetails")) {
////                                    SkuDetails skuDetails = (SkuDetails)args[0];
////                                    String sku = skuDetails.getSku();
//                                    JSONObject param2 = new JSONObject();
//                                    param2.put("productId", "com.midasplayer.iap.candycrushsaga.goldbar.10x");
//                                    Purchase purchaseData = new Purchase("Blah", param2.toString(), "Blah");
//                                    addPurchaseFinal.invoke(_this, purchaseData);
//                                    return invoke(_this, args);
//                                } else {
//                                Object result = invoke(_this, args);
//                                if (result != null) {
//
//                                Log.d(logTag, "Method Return: " + result.toString());}
//                                return result;}
//                            }
//                        });
//                    } catch (Exception e) {
//                        Log.e(logTag, "An error occurred: " +  e.toString());
//                    }
//
//                }
//            }
//        });
    }}

