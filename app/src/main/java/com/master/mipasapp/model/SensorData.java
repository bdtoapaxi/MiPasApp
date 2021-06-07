package com.master.mipasapp.model;

import android.hardware.Sensor;

public class SensorData {
    private float LightSensor;
    private float TempSensor;
    private float ProxSensor;
    private float PressureSensor;
    private float HumiditySensor;

    public SensorData() {

    }

    public SensorData(float lightSensor, float tempSensor, float proxSensor, float pressureSensor, float humiditySensor) {
        this.LightSensor = lightSensor;
        this.TempSensor = tempSensor;
        this.ProxSensor = proxSensor;
        this.PressureSensor = pressureSensor;
        this.HumiditySensor = humiditySensor;
    }

    public float getLightSensor() {
        return LightSensor;
    }

    public void setLightSensor(float lightSensor) {
        LightSensor = lightSensor;
    }

    public float getTempSensor() {
        return TempSensor;
    }

    public void setTempSensor(float tempSensor) {
        TempSensor = tempSensor;
    }

    public float getProxSensor() {
        return ProxSensor;
    }

    public void setProxSensor(float proxSensor) {
        ProxSensor = proxSensor;
    }

    public float getPressureSensor() {
        return PressureSensor;
    }

    public void setPressureSensor(float pressureSensor) {
        PressureSensor = pressureSensor;
    }

    public float getHumiditySensor() {
        return HumiditySensor;
    }

    public void setHumiditySensor(float humiditySensor) {
        HumiditySensor = humiditySensor;
    }
}
