package com.muzammilpeer.ffmpeglayer.enums;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muzammilpeer on 11/2/15.
 */

public enum CpuArchEnum {
    x86(1, "1b3daf0402c38ec0019ec436d71a1389514711bd", "x86"),
    ARMv7(2, "e27cf3c432b121896fc8af2d147eff88d3074dd5", "armeabi-v7a"),
    ARMv7_NEON(3, "9463c40e898c53dcac59b8ba39cfd590e2f1b1bf", "armeabi-v7a-neon"),
    NONE(4, null, null);

    private String sha1;
    private String archFullName;
    private int code;

    private static Map<Integer, CpuArchEnum> codeToStatusMapping;

    CpuArchEnum(int code, String sha1, String archName) {
        this.sha1 = sha1;
        this.archFullName = archName;
        this.code = code;
    }


    public static CpuArchEnum getStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }

    @SuppressLint("UseSparseArrays")
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, CpuArchEnum>();
        for (CpuArchEnum s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }

    public static CpuArchEnum fromString(String sha1) {
        if (!TextUtils.isEmpty(sha1)) {
            for (CpuArchEnum cpuArch : CpuArchEnum.values()) {
                if (sha1.equalsIgnoreCase(cpuArch.sha1)) {
                    return cpuArch;
                }
            }
        }
        return NONE;
    }


    public String getSha1() {
        return sha1;
    }

    public String getArchFullName() {
        return archFullName;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CpuArchEnum{" +
                "sha1='" + sha1 + '\'' +
                ", archFullName='" + archFullName + '\'' +
                ", code=" + code +
                '}';
    }
}
