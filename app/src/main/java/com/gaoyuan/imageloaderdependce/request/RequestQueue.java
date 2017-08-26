package com.gaoyuan.imageloaderdependce.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gaoyuan on 2017/5/23.
 * 请求队列
 */

public class RequestQueue {

    //阻塞式队列.多线程共享，生产效率和消费效率相差太远
    //优先级队列.优先级高的队列先被消费，每一个产品都有编号，BitmapRequest实现Comparator后，队列自动实现优先级
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();
    //线程数量  转发器的数量
    private int threadCount;
    //一组转发器
    private RequestDispatcher[] mDispatcher;
    //线程安全
    private AtomicInteger i = new AtomicInteger(0);

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 添加请求
     *
     * @param bitmapRequest
     */
    public void addRequest(BitmapRequest bitmapRequest) {

        //判断请求队列中是否包含该请求
        if (!mRequestQueue.contains(bitmapRequest)) {
            //给请求添加编号
            bitmapRequest.setSeriaNo(i.decrementAndGet());
            mRequestQueue.add(bitmapRequest);
        } else {
            Log.e("gy", "请求已经存在,编号：" + bitmapRequest.getSeriaNo());
        }
    }

    /**
     * 开始请求
     */
    public void start() {
        //先停止
        stop();
        startDispatchers();
    }

    /**
     * 开启转发器
     */
    private void startDispatchers() {
        //开启了所有的转发器，也就是开启了所有的线程，线程中的while循环开始从队列中拿数据
        mDispatcher = new RequestDispatcher[threadCount];
        for (int i1 = 0; i1 < threadCount; i1++) {
            RequestDispatcher p = new RequestDispatcher(mRequestQueue);
            mDispatcher[i1] = p;
            mDispatcher[i1].start();
        }
    }

    /**
     * 暂停请求
     */
    private void stop() {

    }

}
