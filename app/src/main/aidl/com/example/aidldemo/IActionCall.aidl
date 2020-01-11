// IActionCall.aidl
package com.example.aidldemo;

import com.example.aidldemo.IServiceCallMainAction;
// Declare any non-default types here with import statements

interface IActionCall {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String doCommond(int cmd, String params);

    void registerListener(IServiceCallMainAction listener);

    void unRegisterListener(IServiceCallMainAction listener);
}
