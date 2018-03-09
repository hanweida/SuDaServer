package com.suda.web.enum_const;

/**
 * Created by ES-BF-IT-126 on 2018/3/9.
 */
public enum LiveSourceConst {
    KUWAN_Source(1),DIDIAOKAN_Source(2),LEQIUBA_Source(3);
    private int index;
    // 构造方法
    private LiveSourceConst(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // 普通方法
    public static String getName(LiveSourceConst cd) {
        for (LiveSourceConst c : LiveSourceConst.values()) {

        }
        return null;
    }
}
