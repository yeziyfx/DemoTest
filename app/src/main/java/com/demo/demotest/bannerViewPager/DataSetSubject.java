package com.demo.demotest.bannerViewPager;


public interface DataSetSubject {
    void registerSubscriber(DataSetSubscriber subscriber);
    void removeSubscriber(DataSetSubscriber subscriber);
    void notifySubscriber();
}
