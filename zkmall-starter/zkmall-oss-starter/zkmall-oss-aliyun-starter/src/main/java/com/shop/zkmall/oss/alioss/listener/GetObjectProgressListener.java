/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss.alioss.listener;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
public class GetObjectProgressListener implements ProgressListener {

    private long bytesRead = 0;
    private long totalBytes = -1;
    @Getter
    private boolean succeed = false;

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                log.debug("Start to download......");
                break;
            case RESPONSE_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                log.debug("{} bytes in total will be downloaded to a local file", this.totalBytes);
                break;
            case RESPONSE_BYTE_TRANSFER_EVENT:
                this.bytesRead += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int) (this.bytesRead * 100.0 / this.totalBytes);
                    log.debug("{} bytes have been read at this time, download progress: {}%({}/{})", bytes, percent, this.bytesRead, this.totalBytes);
                } else {
                    log.debug("{} bytes have been read at this time, download ratio: unknown({}/...)", bytes, this.bytesRead);
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                log.debug("Succeed to download, {} bytes have been transferred in total", this.bytesRead);
                break;
            case TRANSFER_FAILED_EVENT:
                log.debug("Failed to download, {} bytes have been transferred", this.bytesRead);
                break;
            default:
                break;
        }
    }

}
