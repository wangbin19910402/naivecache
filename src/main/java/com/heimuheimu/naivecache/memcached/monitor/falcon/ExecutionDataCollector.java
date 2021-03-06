/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 heimuheimu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.heimuheimu.naivecache.memcached.monitor.falcon;

import com.heimuheimu.naivecache.constant.FalconDataCollectorConstant;
import com.heimuheimu.naivecache.memcached.monitor.ExecutionMonitorFactory;
import com.heimuheimu.naivemonitor.falcon.support.AbstractExecutionDataCollector;
import com.heimuheimu.naivemonitor.monitor.ExecutionMonitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Memcached 客户端使用的执行信息 Falcon 监控数据采集器。该采集器采集周期为 30 秒，每次采集将会返回以下数据项：
 *
 * <ul>
 *     <li>naivecache_key_not_found/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内 Get 操作未找到 Key 的次数</li>
 *     <li>naivecache_timeout/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内 Memcached 操作发生超时的错误次数</li>
 *     <li>naivecache_error/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内 Memcached 操作发生异常的错误次数</li>
 *     <li>naivecache_tps/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内每秒平均执行次数</li>
 *     <li>naivecache_peak_tps/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内每秒最大执行次数</li>
 *     <li>naivecache_avg_exec_time/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内单次 Memcached 操作平均执行时间</li>
 *     <li>naivecache_max_exec_time/module=naivecache &nbsp;&nbsp;&nbsp;&nbsp; 30 秒内单次 Memcached 操作最大执行时间</li>
 * </ul>
 *
 * @author heimuheimu
 */
public class ExecutionDataCollector extends AbstractExecutionDataCollector {

    private static final Map<Integer, String> ERROR_METRIC_SUFFIX_MAP;

    static {
        ERROR_METRIC_SUFFIX_MAP = new HashMap<>();
        ERROR_METRIC_SUFFIX_MAP.put(ExecutionMonitorFactory.ERROR_CODE_KEY_NOT_FOUND, "_key_not_found");
        ERROR_METRIC_SUFFIX_MAP.put(ExecutionMonitorFactory.ERROR_CODE_TIMEOUT, "_timeout");
        ERROR_METRIC_SUFFIX_MAP.put(ExecutionMonitorFactory.ERROR_CODE_MEMCACHED_ERROR, "_error");
    }

    @Override
    protected List<ExecutionMonitor> getExecutionMonitorList() {
        return ExecutionMonitorFactory.getAll();
    }

    @Override
    protected String getModuleName() {
        return FalconDataCollectorConstant.MODULE_NAME;
    }

    @Override
    protected Map<Integer, String> getErrorMetricSuffixMap() {
        return ERROR_METRIC_SUFFIX_MAP;
    }

    @Override
    public int getPeriod() {
        return FalconDataCollectorConstant.REPORT_PERIOD;
    }
}
