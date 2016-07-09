package com.sreesha.android.villgro;

/**
 * Created by Sreesha on 09-07-2016.
 */
public class ModuleMetaData {
    String moduleType = "defaultType";
    int moduleSubNumber = 0;

    public ModuleMetaData(String moduleType, int moduleSubNumber) {
        this.moduleType = moduleType;
        this.moduleSubNumber = moduleSubNumber;
    }

    public String getModuleType() {
        return moduleType;
    }

    public int getModuleSubNumber() {
        return moduleSubNumber;
    }
}
