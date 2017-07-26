package com.phonesj.news.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Phone on 2017/7/17.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface GankUrl {
}
