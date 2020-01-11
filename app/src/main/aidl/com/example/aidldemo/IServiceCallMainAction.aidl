// IServiceCallMainAction.aidl
package com.example.aidldemo;

// Declare any non-default types here with import statements

interface IServiceCallMainAction {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String doCallMainCmd(int cmd, String params);
}
