package com.eholee.harmony_mvvm.slice;

import com.eholee.harmony_arch.BaseAbilitySlice;
import com.eholee.harmony_arch.log.L;
import com.eholee.harmony_arch.viewmodel.ViewModelProvider;
import com.eholee.harmony_mvvm.MainAbility;
import com.eholee.harmony_mvvm.ResourceTable;
import com.eholee.harmony_mvvm.repository.User;
import com.eholee.harmony_mvvm.viewmodel.UserViewModel;
import ohos.aafwk.abilityjet.activedata.DataObserver;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

import java.io.IOException;

public class MainAbilitySlice extends BaseAbilitySlice {
    private UserViewModel userViewModel;
    private static final String TAG = MainAbility.class.getSimpleName();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

    }

    @Override
    public void onActive() {
        super.onActive();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);

        userViewModel.getUserLiveData().addObserver(getLifecycle(), new DataObserver<User>() {
            @Override
            public void onChanged(User user) {
                L.error(TAG , "用户信息：%{public}s" , user.toString());
                text.setText(user.name+user.age);
            }
        } , false);

        text.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                User user = new User();
                user.name ="xiaohong";
                user.age = 16;
                userViewModel.setUser(user);
            }
        });

    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
