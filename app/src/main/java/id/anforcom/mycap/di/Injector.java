package id.anforcom.mycap.di;

import id.anforcom.mycap.data.repository.ApiRepository;

/**
 * Created by aflah on 12/10/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class Injector {

    public static ApiRepository provideApiRepository () {
        return ApiRepository.getInstance();
    }
}
