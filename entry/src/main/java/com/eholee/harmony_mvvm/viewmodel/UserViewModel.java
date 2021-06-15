package com.eholee.harmony_mvvm.viewmodel;

import com.eholee.harmony_arch.activedata.MutableActiveData;
import com.eholee.harmony_arch.viewmodel.ViewModel;
import com.eholee.harmony_mvvm.repository.User;

/**
 * Author : Jeffer
 * Date : 2021/6/15
 * Desc :
 */
public class UserViewModel extends ViewModel {
    private final MutableActiveData<User> userMutableActiveData = new MutableActiveData<>();

    public MutableActiveData<User> getUserLiveData(){
        return userMutableActiveData;
    }

    public void setUser(User user){
        userMutableActiveData.setData(user);
    }

}
