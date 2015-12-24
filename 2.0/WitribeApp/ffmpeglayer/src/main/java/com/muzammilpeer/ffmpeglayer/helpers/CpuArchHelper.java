package com.muzammilpeer.ffmpeglayer.helpers;

import android.os.Build;

import com.github.hiteshsondhi88.libffmpeg.ArmArchHelper;
import com.muzammilpeer.ffmpeglayer.enums.CpuArchEnum;

/**
 * Created by muzammilpeer on 11/2/15.
 */
public class CpuArchHelper {

    public static CpuArchEnum getCpuArch() {
        // check if device is x86
        if (Build.CPU_ABI.equals(getx86CpuAbi())) {
            return CpuArchEnum.x86;
        } else {
            // check if device is armeabi
            if (Build.CPU_ABI.equals(getArmeabiv7CpuAbi())) {
                ArmArchHelper cpuNativeArchHelper = new ArmArchHelper();
                String archInfo = cpuNativeArchHelper.cpuArchFromJNI();
                // check if device is arm v7
                if (cpuNativeArchHelper.isARM_v7_CPU(archInfo)) {
                    // check if device is neon
                    if (cpuNativeArchHelper.isNeonSupported(archInfo)) {
                        return CpuArchEnum.ARMv7_NEON;
                    }
                    return CpuArchEnum.ARMv7;
                }
            }
        }
        return CpuArchEnum.NONE;
    }

    public static String getx86CpuAbi() {
        return "x86";
    }

    public static String getArmeabiv7CpuAbi() {
        return "armeabi-v7a";
    }
}
