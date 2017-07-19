package com.phonesj.news.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Phone on 2017/7/17.
 * <p>修改Fragment依赖对象的单例</p>
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
