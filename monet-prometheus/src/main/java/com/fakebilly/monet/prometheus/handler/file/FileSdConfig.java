package com.fakebilly.monet.prometheus.handler.file;

import java.util.List;

/**
 * FileSdConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class FileSdConfig {

    private List<String> targets;

    private Labels labels;

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "FileSdConfig{" +
                "targets=" + targets +
                ", labels=" + labels +
                '}';
    }


}