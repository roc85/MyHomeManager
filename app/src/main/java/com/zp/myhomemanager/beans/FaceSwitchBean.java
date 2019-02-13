package com.zp.myhomemanager.beans;

import com.zp.myhomemanager.ui.views.HomeFaceView;

public class FaceSwitchBean {
    private HomeFaceView.FACE_ANIM face_anim;

    public HomeFaceView.FACE_ANIM getFace_anim() {
        return face_anim;
    }

    public void setFace_anim(HomeFaceView.FACE_ANIM face_anim) {
        this.face_anim = face_anim;
    }

    public FaceSwitchBean(HomeFaceView.FACE_ANIM fa)
    {
        this.face_anim = fa;
    }
}
