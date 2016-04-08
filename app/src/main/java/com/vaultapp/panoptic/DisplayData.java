package com.vaultapp.panoptic;

import java.io.File;

public class DisplayData {
    String name,source;
    DisplayData(File F){
        name = F.getName();
        source = F.getParent();
    }
}
