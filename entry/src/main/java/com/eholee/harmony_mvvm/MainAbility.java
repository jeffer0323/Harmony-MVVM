package com.eholee.harmony_mvvm;

import com.eholee.harmony_arch.BaseAbility;
import com.eholee.harmony_mvvm.slice.MainAbilitySlice;
import ohos.aafwk.content.Intent;

public class MainAbility extends BaseAbility {


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());



    }
}
