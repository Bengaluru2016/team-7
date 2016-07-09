package com.sreesha.android.villgro;

import java.util.ArrayList;

/**
 * Created by Sreesha on 09-07-2016.
 */
public class ModuleMetaData {
    String moduleType = "defaultType";
    ArrayList<Integer> moduleSubNumberList;

    public ModuleMetaData(String moduleType, ArrayList<Integer> moduleSubNumberList) {
        this.moduleType = moduleType;
        this.moduleSubNumberList = moduleSubNumberList;
    }

    public String getModuleType() {
        return moduleType;
    }

    public ArrayList<Integer> getModuleSubNumberArrayList() {
        return moduleSubNumberList;
    }
}
