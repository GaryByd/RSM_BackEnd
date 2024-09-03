package com.rc.utils;

public interface ILock {
    boolean tryLock(Long timeOutSec);
    void unlock();
}
