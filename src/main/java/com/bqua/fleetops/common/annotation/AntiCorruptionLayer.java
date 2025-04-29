package com.bqua.fleetops.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * modulith 를 위한 anti corruption layer.
 * 모듈 간 통신 시 api 등의 통신으로 접근하는 것이 정석이지만
 * 같은 코드베이스에 있다는 특성 상 바로 메소드를 통해 접근한다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AntiCorruptionLayer { }